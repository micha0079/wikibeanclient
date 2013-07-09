package de.ws.wikibeanclient.constants;

/**
 * 
 * @author Michael
 *
 */
public enum WdDatavalueType {
	
	item("wikibase-entityid"),
	string("string"),
	time("time");
	
	private String name;
	
	private WdDatavalueType(String name) {
		this.name = name;
	}
	/**
	 * @author Michael
	 * @date 03.07.2013
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
