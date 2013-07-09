/**
 * 
 */
package de.ws.wikibeanclient.json.wikidata;


/**
 * @author Michael
 *
 */
public class WdDataValue {

	private String type;
	private Object value;
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
