/**
 * 
 */
package de.ws.wikibeanclient.connection;

import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ws.wikibeanclient.constants.ApiCommand;
import de.ws.wikibeanclient.json.Api;
import de.ws.wikibeanclient.json.CategoryMembers;
import de.ws.wikibeanclient.json.Page;
import de.ws.wikibeanclient.util.JsonUtil;
import de.ws.wikibeanclient.util.TextUtil;

/**
 * 
 * @author Michael
 * @date 30.06.2013
 */
public class MediaWikiConnection {

	private static final int DEFAULT_MAX_LAG = 5;
	private static final int DEFAULT_MAX_RETRIES = 5;
	private static final int DEFAULT_MIN_WAIT_BETWEEN_QUERIES = 500;
	
	private String apiUrl;
	private int maxLag;
	private int maxRetries;
	private int minWaitTimeBetweenQueries;
	private String userAgentHeader;

	private static Logger log = LogManager.getLogger(MediaWikiConnection.class);

	private long lastQueryTimestamp = -1;
	
	private static final String DEFAULT_USER_AGENT_HEADER = "bot"; 

	/**
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param apiUrl
	 */
	public MediaWikiConnection(String apiUrl) {
		super();
		this.apiUrl = apiUrl;
		maxLag = DEFAULT_MAX_LAG;
		maxRetries = DEFAULT_MAX_RETRIES;
		minWaitTimeBetweenQueries = DEFAULT_MIN_WAIT_BETWEEN_QUERIES;
		userAgentHeader = DEFAULT_USER_AGENT_HEADER;
	}

	/**
	 * logs in with a given username and password
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param username the username
	 * @param password the password
	 * @return true if the operation was successful, false otherwise.
	 */
	public boolean login(String username, String password) {

		boolean result = false;
		
		if(TextUtil.isSet(username) && TextUtil.isSet(password)) {
			log.info("logging in as "+username);

			CookieManager cookieManager = new CookieManager();
			cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			CookieHandler.setDefault(cookieManager);

			Api api = sendRequest(ApiCommand.LOGIN, false, "lgname", username, "lgpassword",
					password);

			String sessionId = null;
			String token = null;

			if (api != null) {
				if (api.getError() != null) {
					log.error("Error: " + api.getError().getCode() + ", "
							+ api.getError().getInfo());
				} else if (api.getLogin() != null) {
					token = api.getLogin().getToken();
					sessionId = api.getLogin().getSessionid();
				}
			}

			if (TextUtil.isSet(sessionId) && TextUtil.isSet(token)) {
				api = sendRequest(ApiCommand.LOGIN, false, "lgname", username, "lgpassword",
						password, "lgtoken", token);

				if (api.getError() != null) {
					log.error("Error: " + api.getError().getCode() + ", "
							+ api.getError().getInfo());
				} else if (api.getLogin()!=null && api.getLogin().getResult() != null
						&& api.getLogin().getResult().equals("Success")) {
					log.info("logged in with sessionId " + sessionId);
					result = true;
				} else {
					log.error("login failed for unknown reason.");
				}
			} else {
				log.error("couldn't receive sessionId and token from mediawiki software, unknown error.");
			}
		} else {
			log.warn("required parameter is null!");
		}
		
				
		return result;
	}

	/**
	 * logs out.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @return true if the operation was successful, false otherwise.
	 */
	public boolean logout() {
		
		boolean success = false;
		
		log.debug("logging out...");
		Api api = sendRequest(ApiCommand.LOGOUT, false);

		if (api == null) {
			log.debug("logged out.");
			success = true;
		} else if (api != null && api.getError() != null) {
			log.error("Error: " + api.getError().getCode() + ", "
					+ api.getError().getInfo());
		}
		
		return success;
	}

	/**
	 * gets all articles in a specific category and its sub-categories.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param categoryName the name of the category
	 * @param result the result list for the categories
	 * @param categoryPrefix the String prefix for categories in this wiki (e.g., in en-wiki it's "Category:")
	 */
	public void getArticlesInCategoryAndSubCategory(String categoryName,
			List<Page> result, String categoryPrefix) {
		String pageId = getPageIdForTitle(categoryName);
		if (TextUtil.isSet(pageId)) {
			getArticlesInCategoryAndSubCategory(pageId, result,
					new HashMap<String, Boolean>(), categoryPrefix);
		} else {
			log.warn("page id not found for categoryName: " + categoryName);
		}
	}

	/**
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param pageId
	 * @param result
	 * @param visited
	 * @param categoryPrefix
	 */
	private void getArticlesInCategoryAndSubCategory(String pageId,
			List<Page> result, Map<String, Boolean> visited, String categoryPrefix) {
		Api api = sendRequest(ApiCommand.QUERY, false, "list", "categorymembers", "cmpageid",
				pageId);

		if (api != null) {

			visited.put(pageId, true);

			String queryContinueString = null;

			do {
				queryContinueString = (api.getQuerycontinue()!=null && api.getQuerycontinue().getCategorymembers()!=null) ? 
						api.getQuerycontinue().getCategorymembers().getCmcontinue() : null; 

				for(CategoryMembers categoryMember : api.getQuery().getCategorymembers()) {
					if (categoryMember.getTitle().startsWith(categoryPrefix)) {
						if (visited.get(categoryMember.getPageid()) == null) {
							getArticlesInCategoryAndSubCategory(categoryMember.getPageid(),
									result, visited, categoryPrefix);
						}
					} else {
						result.add(categoryMember);
					}
				}
				
				if (queryContinueString != null) {
					api = sendRequest(ApiCommand.QUERY, false, "list", "categorymembers",
							"cmpageid", pageId, "cmcontinue",
							queryContinueString);
				}
			} while (queryContinueString != null);

		}
	}

	/**
	 * returns the content of an article
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param title the title of the article
	 * @return the article content
	 */
	public String getArticleContent(String title) {
		 Api api = sendRequest(ApiCommand.QUERY, false, "titles", title, "prop",
				 "revisions", "rvprop", "content");
		 
		 return (api!=null && api.getQuery()!=null && api.getQuery().getPages()!=null && api.getQuery().getPages().getPages()!=null && 
				 api.getQuery().getPages().getPages().size()==1 &&
				 api.getQuery().getPages().getPages().get(0).getRevisions()!=null && api.getQuery().getPages().getPages().get(0).getRevisions().size()==1) ?
					 api.getQuery().getPages().getPages().get(0).getRevisions().get(0).getContent() : null;
		 
	}

	/**
	 * returns the pageId for an article.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param title the title of the article
	 * @return
	 */
	public String getPageIdForTitle(String title) {
		Api api = sendRequest(ApiCommand.QUERY, false, "titles", title);
		if (api != null) {
			if (api.getError() != null) {
				log.error("Error: " + api.getError().getCode() + ", "
						+ api.getError().getInfo());
			} else {
				if (api.getQuery() != null && api.getQuery().getPages() != null) {
					List<Page> pages = api.getQuery().getPages().getPages();
					if (pages != null && pages.size() == 1) {
						return pages.get(0).getPageid();
					}
				}

			}
		}
		return null;
	}

	/**
	 * sends a request to the mediawiki api.
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param action the command
	 * @param isEdit true if the command edits the wiki, false otherwise.
	 * @param params list of parameters (size must be a multiple of 2)
	 * @return the Api object returned by the mediawiki api
	 */
	protected Api sendRequest(String action, boolean isEdit, String... params) {

		Api api = null;

		try {

			int retry = 0;
			HttpURLConnection con = null;
			
			boolean worked = false;

			while (retry <= maxRetries && !worked) {
				
				while (lastQueryTimestamp+minWaitTimeBetweenQueries > System.currentTimeMillis()) { }
				
				retry++;
				
				URL url = new URL(apiUrl
						+ getQueryParameterString(action, isEdit, params));

				log.trace("Request: " + url.toString()+", try #"+retry);

				con = (HttpURLConnection) url.openConnection();
				con.setUseCaches(false);
				con.setRequestMethod("POST");
				con.setDoOutput(true);
				con.setRequestProperty("User-Agent", userAgentHeader);
				con.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF8");
				con.connect();
				
				lastQueryTimestamp = System.currentTimeMillis();
				
				if (con != null
						&& con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					
					String response = TextUtil.getStringFromInputStream(con.getInputStream());					
					
					log.trace("Response: " + response);
					
					api = JsonUtil.createFromJson(response);
					
					long lagSecs = getLagSeconds(api);
					if(lagSecs>0) {
						log.info("database lag, waiting "+lagSecs+" seconds...");
						while (lastQueryTimestamp +lagSecs*1000 > System.currentTimeMillis()) { }
					} else {
						worked = true;
					}

				} else {
					log.error("Error on request: Bad http response code: "
							+ con.getResponseCode());
				}
			}
			

		} catch (Exception e) {
			log.error("Error on request: " + e.getMessage(), e);
		}

		return api;
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param api
	 * @return
	 */
	private static long getLagSeconds(Api api) {
		if(api!=null && api.getError()!=null && api.getError().getCode()!=null && api.getError().getCode().equals("maxlag")) {
			if(api.getError().getInfo()!=null) {
				String infoString = api.getError().getInfo();
				if (infoString.startsWith("Waiting for") && infoString.contains(":")) {
					String[] infoParts = infoString.split(":");
					if(infoParts.length==2) {
						infoParts[1] =  infoParts[1].trim();
						String[] splittedInfoParts = infoParts[1].split(" ");
						if(splittedInfoParts.length>0) {
							String secondsString = splittedInfoParts[0];
							try {
								return new Long(secondsString);
							} catch (NumberFormatException nfe) {
								log.error("unable to parse lag seconds String: "+infoString);
							}
						}
					}
				}
				
			}
		}
		return -1;
		
	}

	/**
	 * 
	 * @author Michael
	 * @date 30.06.2013
	 * @param action
	 * @param isEdit
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getQueryParameterString(String action, boolean isEdit,
			String... params) throws UnsupportedEncodingException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("?action=").append(action);
		stringBuilder.append("&format=").append("json");
		stringBuilder.append("&maxlag=").append(maxLag + "");

		if (isEdit) {
//			stringBuilder.append("&assert=").append("bot");
			stringBuilder.append("&bot=").append("1");
			
		}

		if (params != null && params.length > 0) {
			if (params.length % 2 == 0) {
				for (int i = 0; i < params.length - 1; i += 2) {
					String urlEncodedValue = "";
					if(params[i+1]!=null) {
						urlEncodedValue = URLEncoder.encode(params[i + 1],
								"UTF-8");
					} else {
						log.warn("value is null for param: "+params[i]+"!?");
					}
					 
					stringBuilder.append("&").append(params[i]).append("=")
							.append(urlEncodedValue);
				}
			} else {
				log.error("Incorrect number of params passed to query: "
						+ params.length);
			}
		}

		return stringBuilder.toString();
	}

	/**
	 * returns a token for an edit operation. This is required for any edit operation
	 * 
	 * @param title The title of the page to edit.
	 * @return the edit token
	 */
	protected String getEditToken(String title) {
		log.debug("[title: "+title+"]...");
		Api api = sendRequest("query", false, "prop", "info", "intoken",
				 "edit", "titles", title);
		
		String editToken = (api!=null && api.getQuery()!=null && api.getQuery().getPages()!=null &&
				api.getQuery().getPages().getPages()!=null && api.getQuery().getPages().getPages().size()==1) ?
						api.getQuery().getPages().getPages().get(0).getEdittoken() : null;
		
		if(TextUtil.isSet(editToken)) {
			log.debug("received editToken: "+editToken);	
		} else {
			editToken = null;
			log.warn("couldn't receive editToken!");
		}
		
		return editToken;
	}

	/**
	 * @return the apiUrl
	 */
	public String getApiUrl() {
		return apiUrl;
	}

	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the maxLag
	 */
	public int getMaxLag() {
		return maxLag;
	}

	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param maxLag the maxLag to set
	 */
	public void setMaxLag(int maxLag) {
		this.maxLag = maxLag;
	}

	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the maxRetries
	 */
	public int getMaxRetries() {
		return maxRetries;
	}

	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param maxRetries the maxRetries to set
	 */
	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the minWaitTimeBetweenQueries
	 */
	public int getMinWaitTimeBetweenQueries() {
		return minWaitTimeBetweenQueries;
	}

	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param minWaitTimeBetweenQueries the minWaitTimeBetweenQueries to set
	 */
	public void setMinWaitTimeBetweenQueries(int minWaitTimeBetweenQueries) {
		this.minWaitTimeBetweenQueries = minWaitTimeBetweenQueries;
	}

	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @return the lastQueryTimestamp
	 */
	public long getLastQueryTimestamp() {
		return lastQueryTimestamp;
	}

	/**
	 * @author Michael
	 * @date 28.06.2013
	 * @param lastQueryTimestamp the lastQueryTimestamp to set
	 */
	public void setLastQueryTimestamp(long lastQueryTimestamp) {
		this.lastQueryTimestamp = lastQueryTimestamp;
	}

	/**
	 * @author Michael
	 * @date 29.06.2013
	 * @return the userAgentHeader
	 */
	public String getUserAgentHeader() {
		return userAgentHeader;
	}

	/**
	 * @author Michael
	 * @date 29.06.2013
	 * @param userAgentHeader the userAgentHeader to set
	 */
	public void setUserAgentHeader(String userAgentHeader) {
		this.userAgentHeader = userAgentHeader;
	}
}
