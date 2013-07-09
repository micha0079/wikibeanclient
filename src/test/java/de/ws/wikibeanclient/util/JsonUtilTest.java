package de.ws.wikibeanclient.util;

import java.util.LinkedHashMap;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import de.ws.wikibeanclient.json.Api;
import de.ws.wikibeanclient.json.wikidata.WdAlias;
import de.ws.wikibeanclient.json.wikidata.WdClaim;
import de.ws.wikibeanclient.json.wikidata.WdDescription;
import de.ws.wikibeanclient.json.wikidata.WdEntity;
import de.ws.wikibeanclient.json.wikidata.WdLabel;
import de.ws.wikibeanclient.json.wikidata.WdValue;
import de.ws.wikibeanclient.util.JsonUtil;
import de.ws.wikibeanclient.util.TextUtil;

/**
 * 
 * @author Michael
 *
 */
public class JsonUtilTest extends TestCase {
	
	private static final String TESTFILE1_PATH = "src/test/resources/federer.json";
	private static final String TESTFILE2_PATH = "src/test/resources/wbgetentities.json";

	/**
	 * 
	 */
	public void testCreateFromJson1() {

		String response = TextUtil.getStringFromFile(TESTFILE2_PATH);
		Api api = JsonUtil.createFromJson(response);
		
		Assert.assertNotNull(api);
		Assert.assertNotNull(api.getEntities());
		Assert.assertNotNull(api.getEntities().getItems());
		Assert.assertEquals(1, api.getEntities().getItems().size());
		WdEntity entity = api.getEntities().getItems().get(0);
		Assert.assertEquals("4246474", entity.getPageid());
		Assert.assertEquals("0", entity.getNs());
		Assert.assertEquals("Q4115189", entity.getTitle());
		Assert.assertEquals("36805009", entity.getLastrevid());
		Assert.assertEquals("2013-05-07T00:59:30Z", entity.getModified());
		Assert.assertEquals("q4115189", entity.getId());
		Assert.assertEquals("item", entity.getType());
		
		LinkedHashMap<String, List<WdAlias>> aliasMap = entity.getAliases();
		Assert.assertNotNull(aliasMap);
		Assert.assertNotNull(aliasMap.get("en"));
		Assert.assertEquals(3, aliasMap.get("en").size());
		Assert.assertEquals("en", aliasMap.get("en").get(0).getLanguage());
		Assert.assertEquals("Live Test Page", aliasMap.get("en").get(0).getValue());
		Assert.assertNotNull(aliasMap.get("ja"));
		Assert.assertEquals(4, aliasMap.get("ja").size());
		Assert.assertEquals("ja", aliasMap.get("ja").get(0).getLanguage());
		Assert.assertEquals("Sandbox", aliasMap.get("ja").get(0).getValue());
		
		LinkedHashMap<String, WdLabel> labels = entity.getLabels();
		Assert.assertNotNull(labels);
		Assert.assertNotNull(labels.get("it"));
		Assert.assertEquals("it", labels.get("it").getLanguage());
		Assert.assertEquals("Pagina di prova di Wikidata", labels.get("it").getValue());
		
		LinkedHashMap<String, WdDescription> descriptions = entity.getDescriptions();
		Assert.assertNotNull(descriptions);
		Assert.assertNotNull(descriptions.get("it"));
		Assert.assertEquals("it", descriptions.get("it").getLanguage());
		Assert.assertEquals("test", descriptions.get("it").getValue());
		
		LinkedHashMap<String, List<WdClaim>> claims = entity.getClaims();
		Assert.assertNotNull(claims);
		Assert.assertNotNull(claims.get("p107"));
		Assert.assertEquals("q4115189$e910921d-40f1-0e88-d37b-aad8012a42c9", claims.get("p107").get(0).getId());
		Assert.assertNotNull(claims.get("p107").get(0).getMainSnak());
		Assert.assertEquals("value", claims.get("p107").get(0).getMainSnak().getSnaktype());
		Assert.assertEquals("p107", claims.get("p107").get(0).getMainSnak().getProperty());
		Assert.assertNotNull(claims.get("p107").get(0).getMainSnak().getDatavalue());
		Assert.assertEquals("wikibase-entityid", claims.get("p107").get(0).getMainSnak().getDatavalue().getType());
		Assert.assertNotNull(claims.get("p107").get(0).getMainSnak().getDatavalue().getValue());
		Assert.assertEquals("item", ((WdValue)claims.get("p107").get(0).getMainSnak().getDatavalue().getValue()).getEntitytype());
		Assert.assertEquals(new Integer(3938), ((WdValue)claims.get("p107").get(0).getMainSnak().getDatavalue().getValue()).getNumericId());
		Assert.assertEquals("statement", claims.get("p107").get(0).getType());
		Assert.assertEquals("normal", claims.get("p107").get(0).getRank());
		
		Assert.assertNotNull(claims.get("p107").get(0).getReferences());
		Assert.assertEquals(1, claims.get("p107").get(0).getReferences().size());
		Assert.assertEquals("e6ae2c93b6abc4202b13afb9ee2e18048b78a0a3", 
				claims.get("p107").get(0).getReferences().get(0).getHash());
		Assert.assertNotNull(claims.get("p107").get(0).getReferences().get(0).getSnaks());
		Assert.assertEquals(1, claims.get("p107").get(0).getReferences().get(0).getSnaks().size());
		Assert.assertNotNull(claims.get("p107").get(0).getReferences().get(0).getSnaks().get("p143"));
		Assert.assertEquals(1, claims.get("p107").get(0).getReferences().get(0).getSnaks().get("p143").size());
		
		Assert.assertEquals("value", claims.get("p107").get(0).getReferences().get(0).getSnaks().get("p143").get(0).getSnaktype());
		Assert.assertEquals("p143", claims.get("p107").get(0).getReferences().get(0).getSnaks().get("p143").get(0).getProperty());
		Assert.assertNotNull(claims.get("p107").get(0).getReferences().get(0).getSnaks().get("p143").get(0).getDatavalue());
		Assert.assertEquals("wikibase-entityid", claims.get("p107").get(0).getReferences().get(0).getSnaks().get("p143").get(0).getDatavalue().getType());
		Assert.assertNotNull(claims.get("p107").get(0).getReferences().get(0).getSnaks().get("p143").get(0).getDatavalue().getValue());
		Assert.assertEquals("item", ((WdValue)claims.get("p107").get(0).getReferences().get(0).getSnaks().get("p143").get(0).getDatavalue().getValue()).getEntitytype());
		Assert.assertEquals(new Integer(11920), ((WdValue)claims.get("p107").get(0).getReferences().get(0).getSnaks().get("p143").get(0).getDatavalue().getValue()).getNumericId());
		
		Assert.assertNotNull(claims.get("p393"));
		Assert.assertNotNull(claims.get("p393").get(0));
		Assert.assertNotNull(claims.get("p393").get(0).getQualifiers());
		Assert.assertNotNull(claims.get("p393").get(0).getQualifiers().get("p212"));
		Assert.assertEquals(2, claims.get("p393").get(0).getQualifiers().get("p212").size());
		Assert.assertEquals("value", claims.get("p393").get(0).getQualifiers().get("p212").get(0).getSnaktype());
		Assert.assertEquals("p212", claims.get("p393").get(0).getQualifiers().get("p212").get(0).getProperty());
		Assert.assertNotNull(claims.get("p393").get(0).getQualifiers().get("p212").get(0).getDatavalue());
		Assert.assertEquals("string", claims.get("p393").get(0).getQualifiers().get("p212").get(0).getDatavalue().getType());
		Assert.assertEquals("978-91-64202-96-3", claims.get("p393").get(0).getQualifiers().get("p212").get(0).getDatavalue().getValue());
		
		Assert.assertNotNull(entity.getSitelinks());
		Assert.assertEquals(4, entity.getSitelinks().size());
		Assert.assertNotNull(entity.getSitelinks().get("enwiki"));
		Assert.assertEquals("enwiki", entity.getSitelinks().get("enwiki").getSite());
		Assert.assertEquals("Wikipedia:Wikidata/Wikidata Sandbox", entity.getSitelinks().get("enwiki").getTitle());
	}
	
	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testCreateFromJsonTime() {

		String response = TextUtil.getStringFromFile(TESTFILE1_PATH);
		Api api = JsonUtil.createFromJson(response);
		Assert.assertNotNull(api);
		Assert.assertNotNull(api.getEntities());
		Assert.assertNotNull(api.getEntities().getItems());
		Assert.assertEquals(1, api.getEntities().getItems().size());
		Assert.assertNotNull(api.getEntities().getItems().get(0).getClaims());
		Assert.assertNotNull(api.getEntities().getItems().get(0).getClaims().get("p569"));
		Assert.assertNotNull(api.getEntities().getItems().get(0).getClaims().get("p569").get(0));
		Assert.assertNotNull(api.getEntities().getItems().get(0).getClaims().get("p569").get(0).getMainSnak());
		Assert.assertNotNull(api.getEntities().getItems().get(0).getClaims().get("p569").get(0).getMainSnak().getDatavalue());
		Assert.assertEquals("time", api.getEntities().getItems().get(0).getClaims().get("p569").get(0).getMainSnak().getDatavalue().getType());
		Assert.assertNotNull(api.getEntities().getItems().get(0).getClaims().get("p569").get(0).getMainSnak().getDatavalue().getValue());
		Assert.assertEquals(WdValue.class, api.getEntities().getItems().get(0).getClaims().get("p569").get(0).getMainSnak().getDatavalue().getValue().getClass());
		WdValue value = (WdValue)api.getEntities().getItems().get(0).getClaims().get("p569").get(0).getMainSnak().getDatavalue().getValue();
		Assert.assertEquals("+00000001981-08-08T00:00:00Z", value.getTime());
		Assert.assertEquals((Integer)0, value.getTimezone());
		Assert.assertEquals((Integer)0, value.getBefore());
		Assert.assertEquals((Integer)0, value.getAfter());
		Assert.assertEquals((Integer)11, value.getPrecision());
		Assert.assertEquals("http://www.wikidata.org/entity/Q1985727", value.getCalendarmodel());
		
	}
}
