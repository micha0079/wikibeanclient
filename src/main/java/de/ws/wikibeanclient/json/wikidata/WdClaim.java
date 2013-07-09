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
public class WdClaim {

	private String id;
	private WdSnak mainSnak;
	private String type;
	private String rank;
	private List<WdReference> references;
	private LinkedHashMap<String, List<WdSnak>> qualifiers;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the mainSnak
	 */
	public WdSnak getMainSnak() {
		return mainSnak;
	}
	/**
	 * @param mainSnak the mainSnak to set
	 */
	public void setMainSnak(WdSnak mainSnak) {
		this.mainSnak = mainSnak;
	}
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
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}
	/**
	 * @param rank the rank to set
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}
	/**
	 * @return the references
	 */
	public List<WdReference> getReferences() {
		return references;
	}
	/**
	 * @param references the references to set
	 */
	public void setReferences(List<WdReference> references) {
		this.references = references;
	}
	/**
	 * @return the qualifiers
	 */
	public LinkedHashMap<String, List<WdSnak>> getQualifiers() {
		return qualifiers;
	}
	/**
	 * @param qualifiers the qualifiers to set
	 */
	public void setQualifiers(LinkedHashMap<String, List<WdSnak>> qualifiers) {
		this.qualifiers = qualifiers;
	}
	
	
}
