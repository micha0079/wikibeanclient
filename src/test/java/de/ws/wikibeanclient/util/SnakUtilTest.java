package de.ws.wikibeanclient.util;

import de.ws.wikibeanclient.json.wikidata.WdSnak;
import de.ws.wikibeanclient.json.wikidata.WdValue;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * 
 * @author Michael
 * @date 28.06.2013
 */
public class SnakUtilTest extends TestCase {

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testCreateTimeString() {
		Integer year = 2001;
		Integer month = 2;
		Integer day = 30;
		
		String result = SnakUtil.createTimeString(year, month, day);
		Assert.assertNotNull(result);
		Assert.assertEquals("+00000002001-02-30T00:00:00Z", result);
		
		year = 2001;
		month = 02;
		day = 30;
		result = SnakUtil.createTimeString(year, month, day);
		Assert.assertNotNull(result);
		Assert.assertEquals("+00000002001-02-30T00:00:00Z", result);

		year = 2001;
		month = 2;
		day = 3;
		result = SnakUtil.createTimeString(year, month, day);
		Assert.assertNotNull(result);
		Assert.assertEquals("+00000002001-02-03T00:00:00Z", result);
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testCreateTimeSnak() {
		Integer year = 2001;
		Integer month = 2;
		Integer day = 30;
		
		WdSnak result = SnakUtil.createTimeSnak("p1", year, month, day, SnakUtil.PRECISION_DAY);
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getProperty());
		Assert.assertEquals("p1", result.getProperty());
		Assert.assertNotNull(result.getDatavalue());
		Assert.assertEquals("time", result.getDatavalue().getType());
		Assert.assertNotNull(result.getDatavalue().getValue());
		Assert.assertEquals(WdValue.class, result.getDatavalue().getValue().getClass());
		Assert.assertEquals("+00000002001-02-30T00:00:00Z", ((WdValue)result.getDatavalue().getValue()).getTime());
		Assert.assertEquals((Integer)0, ((WdValue)result.getDatavalue().getValue()).getTimezone());
		Assert.assertEquals((Integer)0, ((WdValue)result.getDatavalue().getValue()).getBefore());
		Assert.assertEquals((Integer)0, ((WdValue)result.getDatavalue().getValue()).getAfter());
		Assert.assertEquals((Integer)11, ((WdValue)result.getDatavalue().getValue()).getPrecision());
		Assert.assertEquals("http://www.wikidata.org/entity/Q1985727", ((WdValue)result.getDatavalue().getValue()).getCalendarmodel());
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testCreateStringSnak() {
		
		WdSnak result = SnakUtil.createStringSnak("p1", "value");
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getProperty());
		Assert.assertEquals("p1", result.getProperty());
		Assert.assertEquals("value", result.getSnaktype());
		Assert.assertNotNull(result.getDatavalue());
		Assert.assertEquals("value", result.getDatavalue().getValue());
		Assert.assertEquals("string", result.getDatavalue().getType());
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testCreateItemSnak() {
		
		WdSnak result = SnakUtil.createItemSnak("p1", 1234);
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getProperty());
		Assert.assertEquals("p1", result.getProperty());
		Assert.assertEquals("value", result.getSnaktype());
		Assert.assertNotNull(result.getDatavalue());
		Assert.assertNotNull(result.getDatavalue().getValue());
		Assert.assertEquals("wikibase-entityid", result.getDatavalue().getType());
		Assert.assertEquals(WdValue.class, result.getDatavalue().getValue().getClass());
		Assert.assertEquals("item", ((WdValue)result.getDatavalue().getValue()).getEntitytype());
		Assert.assertEquals((Integer)1234, ((WdValue)result.getDatavalue().getValue()).getNumericId());
	}
}
