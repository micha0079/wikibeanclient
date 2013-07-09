/**
 * 
 */
package de.ws.wikibeanclient.json.wikidata;

/**
 * @author Michael
 *
 */
public class WdSnak {

	private String snaktype;
	private String property;
	private WdDataValue datavalue;
	
	/**
	 * @return the snaktype
	 */
	public String getSnaktype() {
		return snaktype;
	}
	/**
	 * @param snaktype the snaktype to set
	 */
	public void setSnaktype(String snaktype) {
		this.snaktype = snaktype;
	}
	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}
	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}
	/**
	 * @return the datavalue
	 */
	public WdDataValue getDatavalue() {
		return datavalue;
	}
	/**
	 * @param datavalue the datavalue to set
	 */
	public void setDatavalue(WdDataValue datavalue) {
		this.datavalue = datavalue;
	}
}
