/**
 * @author Michael
 * @date 28.06.2013
 */
package de.ws.wikibeanclient.util;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Michael
 * @date 28.06.2013
 */
public class TextUtilTest extends TestCase {


	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testGetSingleStringFromList() {
		
		String[] testList = new String[4];
		testList[0] = "String 1";
		testList[1] = "String 2";
		testList[2] = "String 3";
		testList[3] = "String 4";
		
		String result = TextUtil.getSingleStringFromList(null, "|");
		Assert.assertNull(result);
		
		result = TextUtil.getSingleStringFromList(testList, null);
		Assert.assertNull(result);
		
		result = TextUtil.getSingleStringFromList(testList, "|");
		Assert.assertNotNull(result);
		Assert.assertEquals("String 1|String 2|String 3|String 4", result);
	}

}
