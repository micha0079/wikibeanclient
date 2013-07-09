/**
 * @author Michael
 * @date 03.07.2013
 */
package de.ws.wikibeanclient.wikidata;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import de.ws.wikibeanclient.connection.WikiDataConnection;
import de.ws.wikibeanclient.constants.WdDatavalueType;
import de.ws.wikibeanclient.constants.WdSnakType;
import de.ws.wikibeanclient.json.PageInfo;
import de.ws.wikibeanclient.json.wikidata.WdClaim;
import de.ws.wikibeanclient.json.wikidata.WdEntity;
import de.ws.wikibeanclient.json.wikidata.WdReference;
import de.ws.wikibeanclient.json.wikidata.WdSnak;
import de.ws.wikibeanclient.json.wikidata.WdValue;
import de.ws.wikibeanclient.util.SnakUtil;
import de.ws.wikibeanclient.util.TextUtil;
import de.ws.wikibeanclient.wikidata.model.WikidataStringValue;
import de.ws.wikibeanclient.wikidata.model.WikidataItemValue;
import de.ws.wikibeanclient.wikidata.model.WikidataTimeValue;
import de.ws.wikibeanclient.wikidata.model.WikidataValue;

/**
 * @author Michael
 * @date 03.07.2013
 */
public class WikidataUtil {

	private static Logger log = LogManager.getLogger(WikidataUtil.class);
	
	/**
	 * 
	 * @author Michael
	 * @date 03.07.2013
	 * @param item
	 * @param propertyId
	 * @return
	 */
	public static List<WdClaim> getClaims(WdEntity item, String propertyId) {
		if(item!=null) {
			if (isValidPropertyId(propertyId)) {
				propertyId = propertyId.toLowerCase();
				return (item!=null && item.getClaims()!=null ? item.getClaims().get(propertyId) : null);
			} 
		} 
		return null;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 03.07.2013
	 * @param claim
	 * @return
	 */
	public static String getStringValueFromClaim(WdClaim claim) {
		String result = null;
		
		if(claim!=null && claim.getType()!=null)
			if(claim.getType().equals(WdDatavalueType.string.getName())) {
				result = claim.getMainSnak()!=null && claim.getMainSnak().getDatavalue()!=null ? (String)claim.getMainSnak().getDatavalue().getValue() : null;				
			} else {
				log.warn("error getting string value from claim: claim doesn't have string datatype, but has "+claim.getType());
		}
		return result;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 03.07.2013
	 * @param claim
	 * @return
	 */
	public static String getItemValueFromClaim(WdClaim claim) {
		Integer result = null;
		
		if(claim!=null && claim.getType()!=null)
			if(claim.getType().equals(WdDatavalueType.item.getName())) {
				result = claim.getMainSnak()!=null && claim.getMainSnak().getDatavalue()!=null 
						&& claim.getMainSnak().getDatavalue().getValue() instanceof WdValue ?
						((WdValue)claim.getMainSnak().getDatavalue().getValue()).getNumericId() : null;
			} else {
				log.warn("error getting item value from claim: claim doesn't have string datatype, but has "+claim.getType());
		}
		if(result!=null) {
			return convertToItemTitle(result);
		} else {
			return null;			
		}
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 03.07.2013
	 * @param claim
	 * @return
	 */
	public static WikidataTimeValue getTimeValueFromClaim(WdClaim claim) {
		WikidataTimeValue result = null;
		if(claim!=null && claim.getType()!=null) {
			if(claim.getType().equals(WdDatavalueType.time.getName())) {
				WdValue value = claim.getMainSnak()!=null && claim.getMainSnak().getDatavalue()!=null 
						&& claim.getMainSnak().getDatavalue().getValue() instanceof WdValue ?
						((WdValue)claim.getMainSnak().getDatavalue().getValue()) : null;
				if(value!=null) {
					result = convertTimeValueToWikidataTime(value);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 03.07.2013
	 * @param wdConnection
	 * @param item
	 * @param propertyId
	 * @param value
	 * @return
	 */
	public static PageInfo createClaim(WikiDataConnection wdConnection, WdEntity item, String propertyId, WikidataValue value) {
		if(wdConnection!=null && item!=null && isValidPropertyId(propertyId) && value!=null) {
			WdClaim claim = new WdClaim();
			WdSnak snak = null;
			if(value instanceof WikidataStringValue) {
				snak = SnakUtil.createStringSnak(propertyId, ((WikidataStringValue)value).getValue());
			} else if(value instanceof WikidataItemValue) {
				snak = SnakUtil.createItemSnak(propertyId, convertToItemNumber(((WikidataItemValue)value).getItemTitle()));
			} else if (value instanceof WikidataTimeValue) {
				snak = SnakUtil.createTimeSnak(propertyId, ((WikidataTimeValue)value).getYear(), ((WikidataTimeValue)value).getMonth(), 
						((WikidataTimeValue)value).getDay(), ((WikidataTimeValue)value).getPrecision());
			}
			claim.setMainSnak(snak);
			return wdConnection.createClaim(item, claim);
		} else {
			log.info("createClaim called with empty or invalid parameter");
		}
		return null;
	}
	

	
	/**
	 * 
	 * @author Michael
	 * @date 01.07.2013
	 * @param wdEntity
	 * @param propertyId
	 * @param value
	 * @return
	 */
	public static List<WdClaim> getClaimsWithSpecificValue(WdEntity wdEntity, String propertyId, WikidataValue value) {
		
		List<WdClaim> returnClaims = new ArrayList<WdClaim>();
		
		
		Integer itemNumber = null;
		if(value instanceof WikidataItemValue) {
			itemNumber =  convertToItemNumber(((WikidataItemValue)value).getItemTitle());
		}
		
		if(value!=null && wdEntity!=null && wdEntity.getClaims()!=null && wdEntity.getClaims().get(propertyId)!=null && 
				wdEntity.getClaims().get(propertyId).size()>0) {
			List<WdClaim> claims = wdEntity.getClaims().get(propertyId);

			if(claims.size()>=1) {
				for(WdClaim claim : claims) {
					if(claim.getMainSnak()!=null && claim.getMainSnak().getDatavalue()!=null
							&& claim.getMainSnak().getDatavalue().getValue()!=null &&
							claim.getMainSnak().getDatavalue()!=null && claim.getMainSnak().getDatavalue().getType().
							equals(value.getDatavalueType().getName())) {
						
						if(value.getDatavalueType().equals(WdDatavalueType.string)) {
							if(claim.getMainSnak().getDatavalue().getValue().equals(((WikidataStringValue)value).getValue())) {
								returnClaims.add(claim);
							}
						} else if(value.getDatavalueType().equals(WdDatavalueType.item) && itemNumber!=null) {
							if(claim.getMainSnak().getDatavalue().getValue()!=null &&
									claim.getMainSnak().getDatavalue().getValue() instanceof WdValue &&
									((WdValue)claim.getMainSnak().getDatavalue().getValue()).getNumericId()!=null &&
											((WdValue)claim.getMainSnak().getDatavalue().getValue()).getNumericId().equals(((WikidataItemValue)value).getItemTitle())) {
								returnClaims.add(claim);
							}
						} else if(value.getDatavalueType().equals(WdDatavalueType.time)) {
							if(claim.getMainSnak().getDatavalue().getValue()!=null &&
									claim.getMainSnak().getDatavalue().getValue() instanceof WdValue) {
								WikidataTimeValue time = convertTimeValueToWikidataTime((WdValue)claim.getMainSnak().getDatavalue().getValue());
								if(time!=null && time.equals(((WikidataTimeValue)value))) {
									returnClaims.add(claim);
								}
							}
						}
					}
				}
			}
		}
		
		return returnClaims;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 04.07.2013
	 * @param wdConnection
	 * @param item
	 * @param claim
	 * @param referenePropertyId
	 * @param referenceItemId
	 * @return
	 */
	public static PageInfo createReference(WikiDataConnection wdConnection, WdEntity item, WdClaim claim, String referenePropertyId, String referenceItemId) {
		PageInfo pageInfo = null;
		Integer referenceNumber = convertToItemNumber(referenceItemId);
		if(wdConnection!=null && item!=null && isValidPropertyId(referenePropertyId) && referenceNumber!=null) {
			WdSnak refSnak = SnakUtil.createItemSnak(referenePropertyId, referenceNumber);
			pageInfo = wdConnection.setReference(item, claim.getId(), refSnak);
		}
		
		return pageInfo;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 04.07.2013
	 * @param wikiDataConnection
	 * @param wdEntity
	 * @param qualifierPropertyId
	 * @param valueString
	 * @return
	 */
	public static PageInfo createQualifier(WikiDataConnection wdConnection, WdEntity item, WdClaim claim, String qualifierPropertyId, WikidataValue value) {
		PageInfo pageInfo = null;
		
		if(wdConnection!=null && item!=null && isValidPropertyId(qualifierPropertyId) && value!=null) {
			String valueString = null;
			if(value instanceof WikidataStringValue) {
				WdSnak snak = SnakUtil.createStringSnak(qualifierPropertyId, ((WikidataStringValue)value).getValue());
				Gson gson = new Gson();
				valueString = gson.toJson(snak.getDatavalue().getValue());
			} else if(value instanceof WikidataItemValue) {
				WdSnak snak = SnakUtil.createItemSnak(qualifierPropertyId, convertToItemNumber(((WikidataItemValue)value).getItemTitle()));
				Gson gson = new Gson();
				valueString = gson.toJson(snak.getDatavalue().getValue());
			} else if(value instanceof WikidataTimeValue) {
				WdSnak snak = SnakUtil.createTimeSnak(qualifierPropertyId, ((WikidataTimeValue)value).getYear(), ((WikidataTimeValue)value).getMonth(), 
						((WikidataTimeValue)value).getDay(), ((WikidataTimeValue)value).getPrecision());
				Gson gson = new Gson();
				valueString = gson.toJson(snak.getDatavalue().getValue());
			}
			
			pageInfo = wdConnection.setQualifier(item, claim.getId(), qualifierPropertyId, valueString, WdSnakType.VALUE, null);
		}
		
		return pageInfo;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 04.07.2013
	 * @param wdConnection
	 * @param item
	 * @param claim
	 * @return
	 */
	public static String removeClaim(WikiDataConnection wdConnection, WdEntity item, WdClaim claim) {
		
		String newRevId = null;
		
		if(wdConnection!=null && item!=null && claim!=null) {
			newRevId = wdConnection.removeClaim(item, claim.getId());
		}
		
		return newRevId;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 04.07.2013
	 * @param wdConnection
	 * @param item
	 * @param claim
	 * @return
	 */
	public static String removeReference(WikiDataConnection wdConnection, WdEntity item, WdClaim claim, WdReference reference) {
		
		String newRevId = null;
		
		if(wdConnection!=null && item!=null && claim!=null && reference!=null) {
			newRevId = wdConnection.removeReference(item, claim.getId(), reference.getHash());
		}
		
		return newRevId;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 03.07.2013
	 * @param value
	 * @return
	 */
	public static WikidataTimeValue convertTimeValueToWikidataTime(WdValue value) {
		WikidataTimeValue result = null;
		if(value!=null) {
			if(TextUtil.isSet(value.getTime())) {
				Pattern pat = Pattern.compile("0*([1-9]\\d*)-(\\d{2})-(\\d{2})T00:00:00Z");
				Matcher matcher = pat.matcher(value.getTime());
				if(matcher.find()) {
					if(matcher.groupCount()==3) {
						result = new WikidataTimeValue();
						try {
							result.setYear(new Integer(matcher.group(1)));
							result.setMonth(new Integer(matcher.group(2)));
							result.setDay(new Integer(matcher.group(3)));
						} catch (NumberFormatException nfe) {
							log.error("error creating wikidatatime from time-string "+value.getTime()+": "+nfe.getMessage(), nfe);
							result = null;
						}
						result.setPrecision(value.getPrecision());
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 03.07.2013
	 * @param itemTitle
	 * @return
	 */
	public static String convertToItemTitle(Integer itemNumber) {
		if(itemNumber!=null) {
			return "q"+itemNumber;	
		}
		return null;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 03.07.2013
	 * @param itemNumber
	 * @return
	 */
	public static Integer convertToItemNumber(String itemTitle) {
		if(TextUtil.isSet(itemTitle) && itemTitle.toLowerCase().startsWith("q") && itemTitle.length()>1) {
			try {
				return new Integer(itemTitle.substring(1));
			} catch (NumberFormatException nfe) {
				log.warn("coulnd't convert item title to number, wrong format: expected: q + number, but is "+itemTitle);
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * @author Michael
	 * @date 03.07.2013
	 * @param propertyId
	 * @return
	 */
	private static boolean isValidPropertyId(String propertyId) {
		if(TextUtil.isSet(propertyId)) {
			propertyId = propertyId.toLowerCase();
			if(propertyId.startsWith("p")) {
				return true;
			}
		} 
		log.error("propertyId has wrong format; expected: 'p +  number' (e.g., 'p100'), but is: "+propertyId);
		return false;
	} 
}
