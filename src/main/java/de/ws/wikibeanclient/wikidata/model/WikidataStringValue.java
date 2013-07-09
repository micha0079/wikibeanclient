/**
 * @author Michael
 * @date 04.07.2013
 */
package de.ws.wikibeanclient.wikidata.model;

import de.ws.wikibeanclient.constants.WdDatavalueType;

/**
 * @author Michael
 * @date 04.07.2013
 */
public class WikidataStringValue extends WikidataValue {

	private String value;
	
	/**
	 * 
	 * @author Michael
	 * @date 04.07.2013
	 * @param value
	 */
	public WikidataStringValue(String value) {
		this.value = value;
	}

	/**
	 * @author Michael
	 * @date 04.07.2013
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @author Michael
	 * @date 04.07.2013
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 
	 */
	@Override
	public WdDatavalueType getDatavalueType() {
		return WdDatavalueType.string;
	}
}
