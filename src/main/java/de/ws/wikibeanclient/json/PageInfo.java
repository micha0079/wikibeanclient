/**
 * @author Michael
 * @date 28.06.2013
 */
package de.ws.wikibeanclient.json;

import de.ws.wikibeanclient.json.wikidata.WdClaim;

/**
 * @author Michael
 * @date 28.06.2013
 */
public class PageInfo {

	private String lastrevid;
	private WdClaim claim;
	
	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the lastrevid
	 */
	public String getLastrevid() {
		return lastrevid;
	}

	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param lastrevid the lastrevid to set
	 */
	public void setLastrevid(String lastrevid) {
		this.lastrevid = lastrevid;
	}

	/**
	 * @author Michael
	 * @date 08.07.2013
	 * @return the claim
	 */
	public WdClaim getClaim() {
		return claim;
	}

	/**
	 * @author Michael
	 * @date 08.07.2013
	 * @param claim the claim to set
	 */
	public void setClaim(WdClaim claim) {
		this.claim = claim;
	}
}
