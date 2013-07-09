/**
 * 
 */
package de.ws.wikibeanclient.json;

import java.util.List;

/**
 * @author Michael
 *
 */
public class Query {

	private Pages pages;
	private List<CategoryMembers> categorymembers;

	/**
	 * @return the pages
	 */
	public Pages getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(Pages pages) {
		this.pages = pages;
	}

	/**
	 * @return the categorymembers
	 */
	public List<CategoryMembers> getCategorymembers() {
		return categorymembers;
	}

	/**
	 * @param categorymembers the categorymembers to set
	 */
	public void setCategorymembers(List<CategoryMembers> categorymembers) {
		this.categorymembers = categorymembers;
	}
}
