/**
 * 
 */
package de.ws.wikibeanclient.json;

import com.google.gson.annotations.SerializedName;

/**
 * @author Michael
 *
 */
public class Revision {

	private String contentformat;
	private String contentmodel;
	@SerializedName("*")
	private String content;
	/**
	 * @return the contentformat
	 */
	public String getContentformat() {
		return contentformat;
	}
	/**
	 * @param contentformat the contentformat to set
	 */
	public void setContentformat(String contentformat) {
		this.contentformat = contentformat;
	}
	/**
	 * @return the contentmodel
	 */
	public String getContentmodel() {
		return contentmodel;
	}
	/**
	 * @param contentmodel the contentmodel to set
	 */
	public void setContentmodel(String contentmodel) {
		this.contentmodel = contentmodel;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
