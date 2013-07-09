package de.ws.wikibeanclient.json.wikidata;

/*
 * 
 */
public class WdDescription {

	private String language;
	private String value;
	
	/**
	 * 
	 */
	public WdDescription() {
		super();
	}
	/**
	 * @param language
	 * @param value
	 */
	public WdDescription(String language, String value) {
		super();
		this.language = language;
		this.value = value;
	}
	
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
		return "description: language: "+language+", value: "+value;
	}
}
