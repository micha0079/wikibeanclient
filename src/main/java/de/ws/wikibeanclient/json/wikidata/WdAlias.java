package de.ws.wikibeanclient.json.wikidata;

/**
 * 
 * @author Michael
 *
 */
public class WdAlias {

	private String language;
	private String value;
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
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
		return "sitelink: language:"+language+", value: "+value;
	}
}
