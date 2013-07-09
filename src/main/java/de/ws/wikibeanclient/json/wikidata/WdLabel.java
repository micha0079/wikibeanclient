/**
 * 
 */
package de.ws.wikibeanclient.json.wikidata;

/**
 * @author Michael
 *
 */
public class WdLabel {

	private String language;
	private String value;
	
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language
	 * @param value
	 */
	public WdLabel(String language, String value) {
		super();
		this.language = language;
		this.value = value;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "label: language: "+language+", value: "+value;
	}
}
