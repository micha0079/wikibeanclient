/**
 * 
 */
package de.ws.wikibeanclient.json.wikidata;

import com.google.gson.annotations.SerializedName;

/**
 * @author Michael
 *
 */
public class WdValue {
	
	@SerializedName("entity-type")
	private String entitytype;
	@SerializedName("numeric-id")
	private Integer numericId;
	
	//time
	private String time;
	private Integer timezone;
	private Integer before;
	private Integer after;
	private Integer precision;
	private String calendarmodel;
	
	//coordinates
	private Double latitude;
	private Double longitude;
	private Double altitude;
	private String globe;
	
	/**
	 * @return the entitytype
	 */
	public String getEntitytype() {
		return entitytype;
	}
	/**
	 * @param entitytype the entitytype to set
	 */
	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}
	
	/**
	 * @return the numericId
	 */
	public Integer getNumericId() {
		return numericId;
	}
	/**
	 * @param numericId the numericId to set
	 */
	public void setNumericId(Integer numericId) {
		this.numericId = numericId;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the timezone
	 */
	public Integer getTimezone() {
		return timezone;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param timezone the timezone to set
	 */
	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the before
	 */
	public Integer getBefore() {
		return before;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param before the before to set
	 */
	public void setBefore(Integer before) {
		this.before = before;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the after
	 */
	public Integer getAfter() {
		return after;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param after the after to set
	 */
	public void setAfter(Integer after) {
		this.after = after;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the calendarmodel
	 */
	public String getCalendarmodel() {
		return calendarmodel;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param calendarmodel the calendarmodel to set
	 */
	public void setCalendarmodel(String calendarmodel) {
		this.calendarmodel = calendarmodel;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the altitude
	 */
	public Double getAltitude() {
		return altitude;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param altitude the altitude to set
	 */
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the globe
	 */
	public String getGlobe() {
		return globe;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param globe the globe to set
	 */
	public void setGlobe(String globe) {
		this.globe = globe;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param precision the precision to set
	 */
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the precision
	 */
	public Integer getPrecision() {
		return precision;
	}

	
}
