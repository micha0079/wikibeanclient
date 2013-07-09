package de.ws.wikibeanclient.connection;

import junit.framework.Assert;
import junit.framework.TestCase;
import de.ws.wikibeanclient.json.Api;
import de.ws.wikibeanclient.json.PageInfo;
import de.ws.wikibeanclient.json.wikidata.WdClaim;
import de.ws.wikibeanclient.json.wikidata.WdEntity;
import de.ws.wikibeanclient.json.wikidata.WdSnak;
import de.ws.wikibeanclient.util.JsonUtil;
import de.ws.wikibeanclient.util.SnakUtil;
import de.ws.wikibeanclient.util.TextUtil;

/**
 * 
 * @author Michael
 * @date 28.06.2013
 */
public class WikiDataConnectionTest extends TestCase {
	
	private static final String DUMMY_MW_URL = "test-mw";
	private static final String TESTFILE_PATH = "src/test/resources/federer.json";
	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testGetItemByWikiLinkNull() {
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		WdEntity item = wikiDataConnection.getItemByWikiLink(null, "title");
		Assert.assertNull(item);
		
		item = wikiDataConnection.getItemByWikiLink("wiki", null);
		Assert.assertNull(item);
		
		item = wikiDataConnection.getItemByWikiLink(null, null);
		Assert.assertNull(item);
	}

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testGetItemByIdNull() {
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		WdEntity item = wikiDataConnection.getItemById(null);
		Assert.assertNull(item);
	}

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testSetDescriptionNull() {
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		Api api = JsonUtil.createFromJson(TextUtil.getStringFromFile(TESTFILE_PATH));
		WdEntity testItem =  api.getEntities().getItems().get(0);
		
		String newRevId = wikiDataConnection.setDescription(null, "wiki", "description");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setDescription(testItem, null, "description");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setDescription(testItem, "wiki", null);
		Assert.assertNull(newRevId);
		 
	}

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testSetLabelNull() {
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		Api api = JsonUtil.createFromJson(TextUtil.getStringFromFile(TESTFILE_PATH));
		WdEntity testItem =  api.getEntities().getItems().get(0);
		
		String newRevId = wikiDataConnection.setLabel(null, "wiki", "label");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setLabel(testItem, null, "label");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setLabel(testItem, "wiki", null);
		Assert.assertNull(newRevId);
	}

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testSetSitelinkNull() {
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		Api api = JsonUtil.createFromJson(TextUtil.getStringFromFile(TESTFILE_PATH));
		WdEntity testItem =  api.getEntities().getItems().get(0);
		
		String newRevId = wikiDataConnection.setSitelink(null, "wiki", "sitelink");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setSitelink(testItem, null, "sitelink");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setSitelink(testItem, "wiki", null);
		Assert.assertNull(newRevId);
		
	}

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testSetAliasNull() {
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		Api api = JsonUtil.createFromJson(TextUtil.getStringFromFile(TESTFILE_PATH));
		WdEntity testItem =  api.getEntities().getItems().get(0);
		
		String newRevId = wikiDataConnection.setAlias(null, "wiki", "alias");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setAlias(testItem, null, "alias");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setAlias(testItem, "wiki", null);
		Assert.assertNull(newRevId);
		
	}

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testCreateClaimNull() {
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		Api api = JsonUtil.createFromJson(TextUtil.getStringFromFile(TESTFILE_PATH));
		WdEntity testItem =  api.getEntities().getItems().get(0);
		
		WdSnak snak = SnakUtil.createStringSnak("propertyid", "value");
		WdClaim claim = new WdClaim();
		claim.setMainSnak(snak);
		
		PageInfo newRevId = wikiDataConnection.createClaim(testItem, null);
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.createClaim(null, claim);
		Assert.assertNull(newRevId);
	}

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testSetReferenceNull() {
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		Api api = JsonUtil.createFromJson(TextUtil.getStringFromFile(TESTFILE_PATH));
		WdEntity testItem =  api.getEntities().getItems().get(0);
		
		WdSnak referenceSnak = SnakUtil.createStringSnak("propertyid", "value");
		
		PageInfo newRevId = wikiDataConnection.setReference(null, "claimid", referenceSnak);
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setReference(testItem, null, referenceSnak);
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setReference(testItem, "claimid", null);
		Assert.assertNull(newRevId);
	}

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testRemoveReferenceNull() {
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		Api api = JsonUtil.createFromJson(TextUtil.getStringFromFile(TESTFILE_PATH));
		WdEntity testItem =  api.getEntities().getItems().get(0);
		
		String newRevId = wikiDataConnection.removeReference(null, "claimid", "referencehash");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.removeReference(testItem, null , "referencehash");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.removeReference(testItem, "claimid", null);
		Assert.assertNull(newRevId);
	}

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testSetQualifierNull() {
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		Api api = JsonUtil.createFromJson(TextUtil.getStringFromFile(TESTFILE_PATH));
		WdEntity testItem =  api.getEntities().getItems().get(0);
		
		PageInfo newRevId = wikiDataConnection.setQualifier(null, "claimid", "qualifierPropertyId", "value", "snakType", null);
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setQualifier(testItem, null, "qualifierPropertyId", "value", "snakType", null);
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setQualifier(testItem, "claimid",null, "value", "snakType", null);
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setQualifier(testItem, "claimid", "qualifierPropertyId", null, "snakType", null);
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.setQualifier(testItem, "claimid", "qualifierPropertyId", "value", null, null);
		Assert.assertNull(newRevId);
	}

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testRemoveQualifierNull() {
		
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		Api api = JsonUtil.createFromJson(TextUtil.getStringFromFile(TESTFILE_PATH));
		WdEntity testItem =  api.getEntities().getItems().get(0);

		String newRevId = wikiDataConnection.removeQualifier(null, "claimId", "snakHashToRemove");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.removeQualifier(testItem, null, "snakHashToRemove");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.removeQualifier(testItem, "claimId", null);
		Assert.assertNull(newRevId);
	}

	/**
	 * 
	 * @author Michael
	 * @date 28.06.2013
	 */
	public void testRemoveClaimNull() {
		WikiDataConnection wikiDataConnection = new WikiDataConnection(DUMMY_MW_URL);
		
		Api api = JsonUtil.createFromJson(TextUtil.getStringFromFile(TESTFILE_PATH));
		WdEntity testItem =  api.getEntities().getItems().get(0);
		
		String newRevId = wikiDataConnection.removeClaim(null, "claimId");
		Assert.assertNull(newRevId);
		
		newRevId = wikiDataConnection.removeClaim(testItem, null);
		Assert.assertNull(newRevId);
	}
}
