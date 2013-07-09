/**
 * 
 */
package de.ws.wikibeanclient.json.wikidata;

/**
 * @author Michael
 *
 */
public class WdSiteLink {

	private String site;
	private String title;
	
	/**
	 * @return the site
	 */
	public String getSite() {
		return site;
	}
	/**
	 * @param site the site to set
	 */
	public void setSite(String site) {
		this.site = site;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "sitelink: site: "+site+", title: "+title;
	}
}
