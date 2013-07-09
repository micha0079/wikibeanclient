/**
 * 
 */
package de.ws.wikibeanclient.json;

import java.util.List;

/**
 * @author Michael
 *
 */
public class Page {

	private String pageid;
	private String ns;
	private String title;
	private List<Revision> revisions;
	
	private String lastrevid;
	private String touched;
	private String edittoken;
	
	/**
	 * @return the pageid
	 */
	public String getPageid() {
		return pageid;
	}
	/**
	 * @param pageid the pageid to set
	 */
	public void setPageid(String pageid) {
		this.pageid = pageid;
	}
	/**
	 * @return the ns
	 */
	public String getNs() {
		return ns;
	}
	/**
	 * @param ns the ns to set
	 */
	public void setNs(String ns) {
		this.ns = ns;
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
	 * @return the revisions
	 */
	public List<Revision> getRevisions() {
		return revisions;
	}
	/**
	 * @param revisions the revisions to set
	 */
	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}
	/**
	 * @return the lastrevid
	 */
	public String getLastrevid() {
		return lastrevid;
	}
	/**
	 * @param lastrevid the lastrevid to set
	 */
	public void setLastrevid(String lastrevid) {
		this.lastrevid = lastrevid;
	}
	/**
	 * @return the touched
	 */
	public String getTouched() {
		return touched;
	}
	/**
	 * @param touched the touched to set
	 */
	public void setTouched(String touched) {
		this.touched = touched;
	}
	/**
	 * @return the edittoken
	 */
	public String getEdittoken() {
		return edittoken;
	}
	/**
	 * @param edittoken the edittoken to set
	 */
	public void setEdittoken(String edittoken) {
		this.edittoken = edittoken;
	}
}
