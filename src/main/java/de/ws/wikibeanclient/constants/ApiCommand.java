/**
 * 
 */
package de.ws.wikibeanclient.constants;

/**
 * @author Michael
 *
 */
public class ApiCommand {

	public static final String LOGIN = "login";
	public static final String LOGOUT = "logout";
	public static final String QUERY = "query";
	
	//Wikidata
	public static final String WBCREATECLAIM = "wbcreateclaim";
	public static final String WBGETENTITIES = "wbgetentities";
	public static final String WBSETCLAIM = "wbsetclaim";
	public static final String WBSETDESCRIPTION = "wbsetdescription";
	public static final String WBSETLABEL = "wbsetlabel";
	public static final String WBSETREFERENCE = "wbsetreference";
	public static final String WBSETSITELINK = "wbsetsitelink";
	public static final String WBSETALIASES = "wbsetaliases";
	
	public static final String WBREMOVEREFERENCES = "wbremovereferences";	
	public static final String WBREMOVESITELINKS = "wbremovesitelinks";
	public static final String WBREMOVECLAIMS = "wbremoveclaims";
	
	public static final String WBSETQUALIFIER = "wbsetqualifier";
	public static final String WBREMOVEQUALIFIER = "wbremovequalifier";
	
	public static final String WBSETCLAIMVALUE = "wbsetclaimvalue";
	
}
