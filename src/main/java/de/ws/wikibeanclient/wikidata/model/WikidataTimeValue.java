/**
 * @author Michael
 * @date 03.07.2013
 */
package de.ws.wikibeanclient.wikidata.model;

import de.ws.wikibeanclient.constants.WdDatavalueType;

/**
 * @author Michael
 * @date 03.07.2013
 */
public class WikidataTimeValue extends WikidataValue {

	public static Integer PRECISION_DAY = 11;
	public static Integer PRECISION_MONTH = 10;
	public static Integer pRECISION_YEAR = 9;
	
	private Integer year;
	private Integer month;
	private Integer day;
	private Integer precision;
	
	/**
	 * @author Michael
	 * @date 03.07.2013
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * @author Michael
	 * @date 03.07.2013
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}
	/**
	 * @author Michael
	 * @date 03.07.2013
	 * @param month the month to set
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}
	/**
	 * @author Michael
	 * @date 03.07.2013
	 * @return the day
	 */
	public Integer getDay() {
		return day;
	}
	/**
	 * @author Michael
	 * @date 03.07.2013
	 * @param day the day to set
	 */
	public void setDay(Integer day) {
		this.day = day;
	}
	/**
	 * @author Michael
	 * @date 03.07.2013
	 * @return the precision
	 */
	public Integer getPrecision() {
		return precision;
	}
	/**
	 * @author Michael
	 * @date 03.07.2013
	 * @param precision the precision to set
	 */
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		
		return obj!=null && obj instanceof WikidataTimeValue && 
				this.getDay()!=null && ((WikidataTimeValue)obj).getDay()!=null && this.getDay().equals(((WikidataTimeValue)obj).getDay()) &&
				this.getMonth()!=null && ((WikidataTimeValue)obj).getMonth()!=null && this.getMonth().equals(((WikidataTimeValue)obj).getMonth()) &&
				this.getPrecision()!=null && ((WikidataTimeValue)obj).getPrecision()!=null && this.getPrecision().equals(((WikidataTimeValue)obj).getPrecision()) &&
				this.getPrecision()!=null && ((WikidataTimeValue)obj).getPrecision()!=null && this.getPrecision().equals(((WikidataTimeValue)obj).getPrecision());
		
	}
	/**
	 * @author Michael
	 * @date 03.07.2013
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	
	/**
	 * 
	 */
	@Override
	public WdDatavalueType getDatavalueType() {
		return WdDatavalueType.time;
	}
}
