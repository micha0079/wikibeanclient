package de.ws.wikibeanclient.util;

import de.ws.wikibeanclient.json.wikidata.WdValue;
import de.ws.wikibeanclient.wikidata.WikidataUtil;
import de.ws.wikibeanclient.wikidata.model.WikidataTimeValue;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * 
 * @author Michael
 * @date 04.07.2013
 */
public class WikidataUtilTest extends TestCase {

	/**
	 * 
	 * @author Michael
	 * @date 04.07.2013
	 */
	public void testConvertTimeValueToWikidataTime() {
		WdValue value = new WdValue();
		value.setTime("+00000001941-06-25T00:00:00Z");
		
		WikidataTimeValue time = WikidataUtil.convertTimeValueToWikidataTime(value);
		Assert.assertNotNull(time);
		Assert.assertEquals((Integer)1941, time.getYear());
		Assert.assertEquals((Integer)6, time.getMonth());
		Assert.assertEquals((Integer)25, time.getDay());
	}
}
