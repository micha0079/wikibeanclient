/**
 * 
 */
package de.ws.wikibeanclient.json.wikidata;

import java.util.LinkedHashMap;
import java.util.List;



/**
 * @author Michael
 *
 */
public class WdReference {
	
	private String hash;
	private LinkedHashMap<String, List<WdSnak>> snaks;
	
	
	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}
	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	/**
	 * @return the snaks
	 */
	public LinkedHashMap<String, List<WdSnak>> getSnaks() {
		return snaks;
	}
	/**
	 * @param snaks the snaks to set
	 */
	public void setSnaks(LinkedHashMap<String, List<WdSnak>> snaks) {
		this.snaks = snaks;
	}
	
	
}
