/**
 * @author Michael
 * @date 28.06.2013
 */
package de.ws.wikibeanclient.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ws.wikibeanclient.constants.WdDatavalueType;
import de.ws.wikibeanclient.constants.WdSnakType;
import de.ws.wikibeanclient.json.wikidata.WdDataValue;
import de.ws.wikibeanclient.json.wikidata.WdSnak;
import de.ws.wikibeanclient.json.wikidata.WdValue;

/**
 * @author Michael
 * @date 28.06.2013
 */
public class SnakUtil {

	private static Logger log = LogManager.getLogger(SnakUtil.class);
	
	private static final String GREGORIAN_CALENDAR_MODEL = "http://www.wikidata.org/entity/Q1985727";
	public static final Integer PRECISION_DAY = 11;
	public static final Integer PRECISION_MONTH = 10;
	public static final Integer PRECISION_YEAR = 9;
	
	/**
	 * creates a time snak.
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param propertyid
	 * @param year
	 * @param month
	 * @param day
	 * @param timezone
	 * @param precision
	 * @return
	 */
	public static WdSnak createTimeSnak(String propertyid, Integer year, Integer month, Integer day, Integer precision) {
		if(TextUtil.isSet(propertyid) && year !=null  && precision!=null) {
			WdSnak snak = new WdSnak();
			WdValue value = new WdValue();
			value.setTime(createTimeString(year, month, day));
			value.setTimezone(0);
			value.setBefore(0);
			value.setAfter(0);
			value.setPrecision(precision);
			value.setCalendarmodel(GREGORIAN_CALENDAR_MODEL);
			
			WdDataValue dataValue = new WdDataValue();
			dataValue.setType(WdDatavalueType.time.getName());
			dataValue.setValue(value);
			
			snak.setDatavalue(dataValue);
			snak.setProperty(propertyid);
			snak.setSnaktype(WdSnakType.VALUE);
			
			return snak;
			
		} else {
			return null;
		}
	}
	
	/**
	 * helper method.
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	protected static String createTimeString(Integer year, Integer month, Integer day) {
		
		String yearString = "";
		String monthString = "";
		String dayString = "";
		
		if(year!=null) {
			
			yearString = year+"";
			
			while (yearString.length()<11) {
				yearString = "0" + yearString;
			}
		} else {
			log.error("year must be set!");
			return null;
		}
		
		if(month!=null) {
			monthString = month+"";
			if(monthString.length()==1) {
				monthString = "0" + monthString;
			}
		} else {
			monthString = "01";
		}
		
		if(day!=null) {
			dayString = day+"";
			if(dayString.length()==1) {
				dayString = "0" + day;
			}
		} else {
			dayString = "01";
		}
		
		return "+"+yearString+"-"+monthString+"-"+dayString+"T00:00:00Z";
	}
	
	/**
	 * creates a string snak.
	 * 
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param propertyId
	 * @param value
	 * @return
	 */
	public static WdSnak createStringSnak(String propertyId, String value) {
		if(TextUtil.isSet(propertyId)) {
			WdDataValue dataValue = new WdDataValue();
			dataValue.setType(WdDatavalueType.string.getName());
			dataValue.setValue(value);
			
			WdSnak snak = new WdSnak();
			snak.setDatavalue(dataValue);
			snak.setProperty(propertyId);
			snak.setSnaktype(WdSnakType.VALUE);
			
			return snak;
		} else {
			return null;
		}
	}
	
	/**
	 * creates an item snak.
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 * @param propertyid
	 * @param itemid
	 */
	public static WdSnak createItemSnak(String propertyId, Integer itemid) {
		if(TextUtil.isSet(propertyId) && itemid!=null) {
			WdDataValue dataValue = new WdDataValue();
			dataValue.setType(WdDatavalueType.item.getName());
			
			WdValue wdvalue = new WdValue();
			wdvalue.setEntitytype("item");
			wdvalue.setNumericId(itemid);
			dataValue.setValue(wdvalue);
			
			
			WdSnak snak = new WdSnak();
			snak.setDatavalue(dataValue);
			snak.setProperty(propertyId);
			snak.setSnaktype(WdSnakType.VALUE);
			
			return snak;
		} else {
			return null;
		}
	}
}
