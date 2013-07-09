package de.ws.wikibeanclient.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import de.ws.wikibeanclient.constants.ApiCommand;
import de.ws.wikibeanclient.json.Api;
import de.ws.wikibeanclient.json.PageInfo;
import de.ws.wikibeanclient.json.wikidata.WdClaim;
import de.ws.wikibeanclient.json.wikidata.WdEntity;
import de.ws.wikibeanclient.json.wikidata.WdSnak;
import de.ws.wikibeanclient.util.TextUtil;

/**
 * 
 * @author Michael
 * @date 28.06.2013
 */
public class WikiDataConnection extends MediaWikiConnection {

	private static Logger log = LogManager.getLogger(WikiDataConnection.class);
	
	public static final String INFO_PROPERTY = "info";
	public static final String SITELINKS_PROPERTY = "sitelinks";
	public static final String ALIASES_PROPERTY = "aliases";
	public static final String LABELS_PROPERTY = "labels";
	public static final String DESCRIPTIONS_PROPERTY = "descriptions";
	public static final String CLAIMS_PROPERTY = "claims";
	
	
	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param apiUrl the complete url of the Mediawiki api
	 */
	public WikiDataConnection(String apiUrl) {
		super(apiUrl);
	}

	/**
	 * 
	 * returns a wikidata item for the wikilink 
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param wiki the wiki name (e.g., enwiki, frwiki, dewiki).
	 * @param title the page name in the wiki.
	 * @return the corresponding wikidata item, if existing.
	 */
	public WdEntity getItemByWikiLink(String wiki, String title) {
		return getItem(wiki, title, null, (String[])null);
	}
	
	/**
	 * returns the item for either the wiki/title pair, or the id ("Q1234"), along with the listed properties
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param wiki the wiki name (e.g., enwiki, frwiki, dewiki).
	 * @param title the page name in the wiki.
	 * @param id id the wikidata id (e.g. "Q1000").
	 * @param properties the properties to load (e.g. "info", "sitelinks", "labels",...). If null then all properties will be fetched.
	 * @return
	 */
	public WdEntity getItem(String wiki, String title, String id, String... properties) {
		
		String props = TextUtil.getSingleStringFromList(properties, "|");
		
		log.debug("getting item [sourcewiki: "+wiki+", title: "+title+", id: "+id+", props: "+props+"]");
		
		if(TextUtil.isSet(wiki) && TextUtil.isSet(title) || TextUtil.isSet(id)) {
			Api api = null;
			if(TextUtil.isSet(wiki) && TextUtil.isSet(title)) {
				if(props!=null) {
					 api = sendRequest(ApiCommand.WBGETENTITIES, false, "sites", wiki,
							"titles", title, "props", props);
				} else {
					api = sendRequest(ApiCommand.WBGETENTITIES, false, "sites", wiki,
							"titles", title);					
				}
			} else if(TextUtil.isSet(id)) {
				if(props!=null) {
					api = sendRequest(ApiCommand.WBGETENTITIES, false, "ids", id, "props", props);
				} else {
					api = sendRequest(ApiCommand.WBGETENTITIES, false, "ids", id);					
				}
			}
			
			if(api!=null) {
				if(api.getError()!=null) {
					log.error("error #"+api.getError().getCode()+": "+api.getError().getInfo());
					return null;
				} else {
					return (api.getEntities()!=null && api.getEntities().getItems()!=null && api.getEntities().getItems().size()==1) ?
							 api.getEntities().getItems().get(0) : null;		
				}
			} else {
				return null;
			}
			
			
			
		} else {
			log.warn("required parameter is null or empty: [id: "+id+"]");
			return null;
		}
	}
	
	/**
	 * returns the item for a wikidata id.
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param id the wikidata id (e.g. "Q1000").
	 * @return the item for this id, if existing.
	 */
	public WdEntity getItemById(String id) {
		return getItem(null, null, id, (String[])null);
	}
	
	/**
	 * creates or overwrites the description of an item in a certain language.
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param item the item to create the description for. It must be an existing item with a title and a revision.
	 * @param wiki the wiki's name that corresponds to the language (e.g., enwiki, frwiki, dewiki).
	 * @param description the description to set.
	 * @return the new revision id from the item, null if something went wrong
	 */
	public String setDescription(WdEntity item, String wiki, String description) {
		
		String revision = null;

		log.debug("setting description [item: "+(item!=null ? item.getTitle() : null)+", revision: "+(item!=null ? item.getLastrevid() : null)+
				", wiki: "+wiki+", description: "+description+"]...");
		
		if(item!=null && TextUtil.isSet(item.getTitle()) && TextUtil.isSet(item.getLastrevid()) && TextUtil.isSet(wiki) && TextUtil.isSet(description)) {
			
			String editToken = getEditToken(item.getTitle());
			
			if(TextUtil.isSet(editToken)) {
				Api api = sendRequest(ApiCommand.WBSETDESCRIPTION, true,
						"id", item.getId(), "baserevid", item.getLastrevid(), "language", wiki, "value", 
						description, "token", editToken);
				
				revision = processResultFromEditOperation(api);
				
			} else {
				log.error("couldn't receive edit token, edit failed.");
			}
		} else {
			log.warn("required parameter is null or empty!");
		}
		
		return revision;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param api
	 * @return
	 */
	private static String processResultFromEditOperation(Api api) {
		String result = null;
		if(api!=null) {
			if(api.getError()!=null) {
				log.error("error #"+api.getError().getCode()+": "+api.getError().getInfo());
			} else if(api!=null && api.getPageinfo()!=null) {
				result = api.getPageinfo().getLastrevid();
			} else if(api!=null && api.getEntity()!=null) {
				result = api.getEntity().getLastrevid();
			}
		}
		return result;
	}
	
	/**
	 * creates or overwrites the label of an item for a language.
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param item the item to create the label for. It must be an existing item with a title and a revision.
	 * @param wiki the wiki's name that corresponds to the language (e.g., enwiki, frwiki, dewiki).
	 * @param label the actual label.
	 * @return the new revision id from the item, null if something went wrong
	 */
	public String setLabel(WdEntity item, String wiki, String label) {
		
		String revision = null;

		log.debug("setting label [item: "+(item!=null ? item.getTitle() : null)+", revision: "+(item!=null ? item.getLastrevid() : null)+", wiki: "+wiki+", label: "+label+"]...");
		
		if(item!=null && TextUtil.isSet(item.getTitle()) && TextUtil.isSet(item.getLastrevid()) && TextUtil.isSet(wiki) && TextUtil.isSet(label)) {
			
			String editToken = getEditToken(item.getTitle());
			
			if(TextUtil.isSet(editToken)) {
				Api api = sendRequest(ApiCommand.WBSETLABEL, true, 
						"id", item.getId(), "baserevid", item.getLastrevid(), "language", wiki, "value", 
						label, "token", editToken);
				
				revision = processResultFromEditOperation(api);
				
			} else {
				log.error("couldn't receive edit token, edit failed.");
			}
		}  else {
			log.warn("required parameter is null or empty!");
		}
		
		return revision;
	}
	
	/**
	 * creates or overwrites a sitelink (link to a wikipedia page) for a wikidata item.
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param item the item to create the label for. It must be an existing item with a title and a revision.
	 * @param wiki the wiki's name that corresponds to the language (e.g., enwiki, frwiki, dewiki).
	 * @param title title of the page to associate
	 * @return the new revision id from the item, null if something went wrong
	 */
	public String setSitelink(WdEntity item, String wiki, String title) {
		
		String revision = null;
		
		log.debug("setting sitelink [item: "+(item!=null ? item.getTitle() : null)+", revision: "+(item!=null ? item.getLastrevid() : null)+", wiki: "+wiki+", title: "+title+"]...");
		
		if(item!=null && TextUtil.isSet(item.getTitle()) && TextUtil.isSet(item.getLastrevid()) && TextUtil.isSet(wiki) && TextUtil.isSet(title)) {
			
			String editToken = getEditToken(item.getTitle());
			if(TextUtil.isSet(editToken)) {
				Api api = sendRequest(ApiCommand.WBSETSITELINK, true, 
						"id", item.getId(), "baserevid", item.getLastrevid(), "linksite", wiki, "linktitle", 
						title, "token", editToken);
				
				revision = processResultFromEditOperation(api);
				
			} else {
				log.error("couldn't receive edit token, edit failed.");
			}
		} else {
			log.warn("required parameter is null or empty!");
		}
		
		return revision;
	}
	
	/**
	 * creates an alias for a Wikidata item.
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param item the item to create the alias for. It must be an existing item with a title and a revision.
	 * @param wiki the wiki's name that corresponds to the language (e.g., enwiki, frwiki, dewiki).
	 * @param alias the alias to set
	 * @return the new revision id from the item, null if something went wrong
	 */
	public String setAlias(WdEntity item, String wiki, String alias) {
		
		String revision = null;
		
		if(item!=null && TextUtil.isSet(item.getTitle()) && TextUtil.isSet(item.getLastrevid()) && TextUtil.isSet(wiki) && TextUtil.isSet(alias)) {

			log.debug("setting alias [item: "+(item!=null ? item.getTitle() : null)+", revision: "+(item!=null ? item.getLastrevid() : null)+
					", wiki: "+wiki+", alias: "+alias+"]...");
			
			String editToken = getEditToken(item.getTitle());
			
			if(TextUtil.isSet(editToken)) {
				Api api = sendRequest(ApiCommand.WBSETALIASES, true, 
						"id", item.getId(), "baserevid", item.getLastrevid(), "add", alias, "language", 
						wiki, "token", editToken);
				
				revision = processResultFromEditOperation(api);
				
			} else {
				log.error("couldn't receive edit token, edit failed.");
			}
		} else {
			log.warn("required parameter is null or empty!");
		}
		
		return revision;
	}
	
	/**
	 * Creates a new claim for a given item.
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param item the item to create the alias for. It must be an existing item with an id and a revision.
	 * @param claim the claim to create
	 * @return the new revision id from the item, null if something went wrong
	 */
	public PageInfo createClaim(WdEntity item, WdClaim claim) {
		
		PageInfo pageInfo = null;
		
		log.debug("creating claim [item id: "+(item!=null ? item.getId() : null)+", revision: "+(item!=null ? item.getLastrevid() : null)+", claim: "+claim+", claim.mainsnak: "+
						(claim!=null && claim.getMainSnak()!=null ? claim.getMainSnak() : null)+", claim.mainsnak.property: "+
						(claim!=null && claim.getMainSnak()!=null && claim.getMainSnak().getProperty()!=null ? claim.getMainSnak().getProperty() : null)+
					     ", value: "+(claim!=null && claim.getMainSnak()!=null && claim.getMainSnak().getDatavalue()!=null ? claim.getMainSnak().getProperty() : null)+"]...");
		
		if(item!=null && TextUtil.isSet(item.getLastrevid()) && TextUtil.isSet(item.getId()) && claim!=null && claim.getMainSnak()!=null && 
				TextUtil.isSet(claim.getMainSnak().getSnaktype()) && 
				TextUtil.isSet(claim.getMainSnak().getProperty()) && claim.getMainSnak().getDatavalue()!=null && claim.getMainSnak().getDatavalue().getValue()!=null) {
			
			String editToken = getEditToken(item.getTitle());
			
			if(TextUtil.isSet(editToken)) {
				
				Gson gson = new Gson();
				String value = gson.toJson(claim.getMainSnak().getDatavalue().getValue());
				
				log.debug("created json from value: "+value);
				
				Api api = sendRequest(ApiCommand.WBCREATECLAIM, true, 
						"entity", item.getId(), "baserevid", item.getLastrevid(), "snaktype", claim.getMainSnak().getSnaktype(), "property", 
						claim.getMainSnak().getProperty(), "value", value 
								, "token", editToken);
				
				processResultFromEditOperation(api);
				
				if(api!=null) {
					pageInfo = api.getPageinfo();
				}
				
			} else {
				log.error("couldn't receive edit token, edit failed.");
			}
		} else {
			log.warn("required parameter is null or empty!");
		}
		
		return pageInfo;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param item the item that contains the claim to add the reference to. It must be an existing item with a title and a revision.
	 * @param claimId the GIUD of the claim to add the reference to. Must be an existing claim
	 * @param referenceSnak the reference snak
	 * @return the new revision id from the item, null if something went wrong
	 */
	public PageInfo setReference(WdEntity item, String claimId, WdSnak referenceSnak) {

		PageInfo pageInfo = null;
		
		log.debug("setting reference [item: "+(item!=null ? item.getTitle() : null)+", revision: "+(item!=null ? item.getLastrevid() : null)+"]");

		if(item!=null && TextUtil.isSet(item.getTitle()) && TextUtil.isSet(item.getLastrevid()) && TextUtil.isSet(claimId) && referenceSnak!=null) {
			String editToken = getEditToken(item.getTitle());
			
			if(TextUtil.isSet(editToken)) {
				
				HashMap<String, List<WdSnak>> snakMap = new HashMap<String, List<WdSnak>>();
				ArrayList<WdSnak> snakList = new ArrayList<WdSnak>();
				snakList.add(referenceSnak);
				snakMap.put(referenceSnak.getProperty(), snakList);
				
				String jsonSnak = new Gson().toJson(snakMap);
				log.debug("created json for the reference: "+jsonSnak);
				
				Api api = sendRequest(ApiCommand.WBSETREFERENCE, true, 
						"statement", claimId, "baserevid", item.getLastrevid(), "token", editToken, "snaks", jsonSnak);
				
				pageInfo = api!=null ? api.getPageinfo() : null;
				
			} else {
				log.error("couldn't receive edit token, edit failed.");
			}
		} else {
			log.warn("required parameter is null or empty!");
		}
		
		return pageInfo;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param item the item that contains the claim to remove the reference from. It must be an existing item with a title and a revision.
	 * @param claimId the GUID of the claim that contains the reference
	 * @param referenceHash the hash value of the reference
	 * @return the new revision id from the item, null if something went wrong
	 */
	public String removeReference(WdEntity item, String claimId, String referenceHash) {
		
		String revision = null;
		
		log.debug("removing reference [item: "+(item!=null ? item.getTitle() : null)+", revision: "+(item!=null ? item.getLastrevid() : null)+", claimid: "+claimId+
				", referencehash: "+referenceHash+"]...");
		
		if(item!=null && TextUtil.isSet(item.getTitle()) && TextUtil.isSet(item.getLastrevid()) && TextUtil.isSet(claimId) && TextUtil.isSet(referenceHash)) {
			
			String editToken = getEditToken(item.getTitle());
			
			if(TextUtil.isSet(editToken)) {
				Api api = sendRequest(ApiCommand.WBREMOVEREFERENCES, true, 
						"statement", claimId, "baserevid", item.getLastrevid(), "token", editToken, "references", referenceHash);
				
				revision = processResultFromEditOperation(api);
				
			} else {
				log.error("couldn't receive edit token, edit failed.");
			}
		} else {
			log.warn("required parameter is null or empty!");
		}
		
		return revision;
	}
	
	/**
	 * creates a qualifier for a given claim.
	 * 
	 * @param item the item that contains the claim to remove the reference from. It must be an existing item with a title and a revision.
	 * @param claimId the claim GUID to add the qualifier to.
	 * @param qualifierPropertyId the property id of the qualifier.
	 * @param value the value of the qualifier.
	 * @param snakType the snaktype of the qualifier.
	 * @param existingSnakHash the has of the property snak to add the qualifier to. Only required when changing an existing qualifier.
	 * @return the new revision id from the item, null if something went wrong
	 */
	public PageInfo setQualifier(WdEntity item, String claimId, String qualifierPropertyId, String value, String snakType, String existingSnakHash) {
		
		PageInfo pageInfo = null;
		
		log.debug("setting qualifier [item: "+(item!=null ? item.getTitle() : null)+", revision: "+(item!=null ? item.getLastrevid() : null)+
				", claimid: "+claimId+", qualifierpropertyid: "+qualifierPropertyId+", value: "+value+", snaktype: "+snakType+", existingsnakhash: "+existingSnakHash+"]...");
		
		if(item!=null && TextUtil.isSet(item.getTitle()) && TextUtil.isSet(item.getLastrevid()) &&
				TextUtil.isSet(claimId) && TextUtil.isSet(qualifierPropertyId) && TextUtil.isSet(value) && TextUtil.isSet(snakType)) {
			
			String editToken = getEditToken(item.getTitle());
			
			Api api = null;
			if(TextUtil.isSet(existingSnakHash)) {
				api = sendRequest(ApiCommand.WBSETQUALIFIER, true, 
						"claim", claimId, "baserevid", item.getLastrevid(), "token", editToken, "property", qualifierPropertyId, "value", value, "snaktype",
						snakType, "snakhash", existingSnakHash);
			} else {
				api = sendRequest(ApiCommand.WBSETQUALIFIER, true, 
						"claim", claimId, "baserevid", item.getLastrevid(), "token", editToken, "property", qualifierPropertyId, "value", value, "snaktype",
						snakType);
			}
			
			processResultFromEditOperation(api);

			pageInfo = api!=null ? api.getPageinfo() : null;
			
		} else {
			log.warn("required parameter is null or empty!");
		}
		return pageInfo;
	}
	
	/**
	 * removes an existing qualifier.
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param item the item that contains the claim to remove the reference from. It must be an existing item with a title and a revision.
	 * @param claimId the claim GUID to remove the qualifier from.
	 * @param snakHashToRemove the snak hash of the qualifier to remove.
	 * @return the new revision id from the item, null if something went wrong
	 */
	public String removeQualifier(WdEntity item, String claimId, String snakHashToRemove) {
		
		String revision = null;
		
		log.debug("removing qualifier [item: "+(item!=null ? item.getTitle() : null)+", revision: "+(item!=null ? item.getLastrevid() : null)+
				", claimid: "+claimId+", snakHashToRemove: "+snakHashToRemove+"]...");
		
		if(item!=null  && TextUtil.isSet(item.getTitle()) && TextUtil.isSet(item.getLastrevid()) && TextUtil.isSet(claimId) && TextUtil.isSet(snakHashToRemove)) {
			
			String editToken = getEditToken(item.getTitle());
			
			Api api = sendRequest(ApiCommand.WBREMOVEQUALIFIER, true, 
					"claim", claimId, "baserevid", item.getLastrevid(), "token", editToken, "claim", claimId, "qualifiers", snakHashToRemove);
			
			revision = processResultFromEditOperation(api);
			
		} else {
			log.warn("required parameter is null or empty!");
		}
		return revision;
	}
	
	/**
	 * 
	 * Removes a claim
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param item the item that contains the claim. It must be an existing item with a title and a revision.
	 * @param claimId the claim GUID
	 * @return the new revision id from the item, null if something went wrong
	 */
	public String removeClaim(WdEntity item, String claimId) {
		
		String revision = null;
		
		log.debug("removing claim [item: "+(item!=null ? item.getTitle() : null)+", revision: "+(item!=null ? item.getLastrevid() : null)+
				", claimid: "+claimId+"]...");
		
		if(item!=null  && TextUtil.isSet(item.getTitle()) && TextUtil.isSet(item.getLastrevid()) && TextUtil.isSet(claimId)) {
			
			String editToken = getEditToken(item.getTitle());
			
			Api api = sendRequest(ApiCommand.WBREMOVECLAIMS, true, 
					"claim", claimId, "baserevid", item.getLastrevid(), "token", editToken, "claim", claimId);
			
			revision = processResultFromEditOperation(api);
			
		} else {
			log.warn("required parameter is null or empty!");
		}
		return revision;
	}
}
