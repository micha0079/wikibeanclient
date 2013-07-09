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
public class WikidataItemValue extends WikidataValue {

	private String itemTitle;
	
	/**
	 * 
	 * @author Michael
	 * @date 04.07.2013
	 * @param itemTitle
	 */
	public WikidataItemValue(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	/**
	 * @author Michael
	 * @date 04.07.2013
	 * @return the itemTitle
	 */
	public String getItemTitle() {
		return itemTitle;
	}

	/**
	 * @author Michael
	 * @date 04.07.2013
	 * @param itemTitle the itemTitle to set
	 */
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	
	/**
	 * 
	 */
	@Override
	public WdDatavalueType getDatavalueType() {
		return WdDatavalueType.item;
	}
}
