/**
 * 
 */
package de.ws.wikibeanclient.json;

/**
 * @author Michael
 *
 */
public class Login {

	private String result;
	private String token;
	private String cookieprefix;
	private String sessionid;
	
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the cookieprefix
	 */
	public String getCookieprefix() {
		return cookieprefix;
	}
	/**
	 * @param cookieprefix the cookieprefix to set
	 */
	public void setCookieprefix(String cookieprefix) {
		this.cookieprefix = cookieprefix;
	}
	/**
	 * @return the sessionid
	 */
	public String getSessionid() {
		return sessionid;
	}
	/**
	 * @param sessionid the sessionid to set
	 */
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

}
