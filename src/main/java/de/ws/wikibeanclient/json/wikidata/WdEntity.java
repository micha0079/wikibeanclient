/**
 * 
 */
package de.ws.wikibeanclient.json.wikidata;

import java.util.LinkedHashMap;
import java.util.List;

import de.ws.wikibeanclient.json.Page;


/**
 * @author Michael
 *
 */
public class WdEntity extends Page {

	private String id;
	private String type;
	private String modified;
	private LinkedHashMap<String, List<WdAlias>> aliases;
	private LinkedHashMap<String, WdLabel> labels;
	private LinkedHashMap<String, WdDescription> descriptions;
	private LinkedHashMap<String, List<WdClaim>> claims;
	private LinkedHashMap<String, WdSiteLink> sitelinks;
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
	 * @return the aliases
	 */
	public LinkedHashMap<String, List<WdAlias>> getAliases() {
		return aliases;
	}
	/**
	 * @param aliases the aliases to set
	 */
	public void setAliases(LinkedHashMap<String, List<WdAlias>> aliases) {
		this.aliases = aliases;
	}
	/**
	 * @return the labels
	 */
	public LinkedHashMap<String, WdLabel> getLabels() {
		return labels;
	}
	/**
	 * @param labels the labels to set
	 */
	public void setLabels(LinkedHashMap<String, WdLabel> labels) {
		this.labels = labels;
	}
	/**
	 * @return the descriptions
	 */
	public LinkedHashMap<String, WdDescription> getDescriptions() {
		return descriptions;
	}
	/**
	 * @param descriptions the descriptions to set
	 */
	public void setDescriptions(LinkedHashMap<String, WdDescription> descriptions) {
		this.descriptions = descriptions;
	}
	/**
	 * @return the claims
	 */
	public LinkedHashMap<String, List<WdClaim>> getClaims() {
		return claims;
	}
	/**
	 * @param claims the claims to set
	 */
	public void setClaims(LinkedHashMap<String, List<WdClaim>> claims) {
		this.claims = claims;
	}
	/**
	 * @return the sitelinks
	 */
	public LinkedHashMap<String, WdSiteLink> getSitelinks() {
		return sitelinks;
	}
	/**
	 * @param sitelinks the sitelinks to set
	 */
	public void setSitelinks(LinkedHashMap<String, WdSiteLink> sitelinks) {
		this.sitelinks = sitelinks;
	}
	/**
	 * @return the modified
	 */
	public String getModified() {
		return modified;
	}
	/**
	 * @param modified the modified to set
	 */
	public void setModified(String modified) {
		this.modified = modified;
	}
	}
