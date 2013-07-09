/**
 * 
 */
package de.ws.wikibeanclient.json.wikidata;

import java.util.List;


/**
 * @author Michael
 *
 */
public class WdQualifier {

	private List<WdSnak> snaks;

	/**
	 * @return the snaks
	 */
	public List<WdSnak> getSnaks() {
		return snaks;
	}

	/**
	 * @param snaks the snaks to set
	 */
	public void setSnaks(List<WdSnak> snaks) {
		this.snaks = snaks;
	}
}
