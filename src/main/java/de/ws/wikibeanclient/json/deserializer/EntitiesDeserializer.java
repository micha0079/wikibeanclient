/**
 * 
 */
package de.ws.wikibeanclient.json.deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import de.ws.wikibeanclient.json.wikidata.WdAlias;
import de.ws.wikibeanclient.json.wikidata.WdClaim;
import de.ws.wikibeanclient.json.wikidata.WdDescription;
import de.ws.wikibeanclient.json.wikidata.WdEntities;
import de.ws.wikibeanclient.json.wikidata.WdEntity;
import de.ws.wikibeanclient.json.wikidata.WdLabel;
import de.ws.wikibeanclient.json.wikidata.WdSiteLink;

/**
 * @author Michael
 *
 */
public class EntitiesDeserializer implements JsonDeserializer<WdEntities> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	public WdEntities deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		WdEntities entitesObject = new WdEntities();
		List<WdEntity> entities = new ArrayList<WdEntity>();
		entitesObject.setItems(entities);
		
		JsonObject jsonObject = json.getAsJsonObject();
		for(Entry<String, JsonElement> jsonElement : jsonObject.entrySet()) {
			WdEntity entity = new WdEntity();
			JsonObject entityJsonObject = jsonElement.getValue().getAsJsonObject();
			
			if(entityJsonObject.get("pageid")!=null) {
				entity.setPageid(entityJsonObject.get("pageid").getAsString());	
			}
			
			if(entityJsonObject.get("modified")!=null) {
				entity.setModified(entityJsonObject.get("modified").getAsString());	
			}
			
			if(entityJsonObject.get("ns")!=null) {
				entity.setNs(entityJsonObject.get("ns").getAsString());	
			}
			
			if(entityJsonObject.get("title")!=null) {
				entity.setTitle(entityJsonObject.get("title").getAsString());				
			}
			
			if(entityJsonObject.get("lastrevid")!=null) {
				entity.setLastrevid(entityJsonObject.get("lastrevid").getAsString());	
			}
			
			if(entityJsonObject.get("id")!=null) {
				entity.setId(entityJsonObject.get("id").getAsString());
			}
			if(entityJsonObject.get("type")!=null) {
				entity.setType(entityJsonObject.get("type").getAsString());
			}

			Gson gson = new Gson();	
			LinkedHashMap<String, List<WdAlias>> aliasMap = new LinkedHashMap<String, List<WdAlias>>();
			if(entityJsonObject.get("aliases")!=null) {
				for(Entry<String, JsonElement> aliasEntry : entityJsonObject.get("aliases").getAsJsonObject().entrySet()) {
					Type type = new TypeToken<List<WdAlias>>(){}.getType();
					List<WdAlias> aliases = gson.fromJson(aliasEntry.getValue(), type);
					aliasMap.put(aliasEntry.getKey(), aliases);
				}				
			}
			
			entity.setAliases(aliasMap);
			
			LinkedHashMap<String, WdLabel> labelsMap = new LinkedHashMap<String, WdLabel>();
			if(entityJsonObject.get("labels")!=null) {
				for(Entry<String, JsonElement> labelsEntry : entityJsonObject.get("labels").getAsJsonObject().entrySet()) {
					WdLabel label = gson.fromJson(labelsEntry.getValue(), WdLabel.class);
					labelsMap.put(labelsEntry.getKey(), label);	
				}	
			}
			
			entity.setLabels(labelsMap);
			
			LinkedHashMap<String, WdDescription> descriptionsMap = new LinkedHashMap<String, WdDescription>();
			if(entityJsonObject.get("descriptions")!=null) {
				for(Entry<String, JsonElement> labelsEntry : entityJsonObject.get("descriptions").getAsJsonObject().entrySet()) {
					WdDescription label = gson.fromJson(labelsEntry.getValue(), WdDescription.class);
					descriptionsMap.put(labelsEntry.getKey(), label);	
				}
			}
			entity.setDescriptions(descriptionsMap);
			
			LinkedHashMap<String, WdSiteLink> sitelinksList = new LinkedHashMap<String, WdSiteLink>();
			if(entityJsonObject.get("sitelinks")!=null) {
				for(Entry<String, JsonElement> labelsEntry : entityJsonObject.get("sitelinks").getAsJsonObject().entrySet()) {
					WdSiteLink sitelink = gson.fromJson(labelsEntry.getValue(), WdSiteLink.class);
					sitelinksList.put(labelsEntry.getKey(), sitelink);	
				}
			}
			entity.setSitelinks(sitelinksList);
			
			//Claims
			LinkedHashMap<String, List<WdClaim>> claimMap = new LinkedHashMap<String, List<WdClaim>>();
			if(entityJsonObject.get("claims")!=null) {
				for(Entry<String, JsonElement> aliasEntry : entityJsonObject.get("claims").getAsJsonObject().entrySet()) {
					GsonBuilder gsonBuilder = new GsonBuilder();
					gsonBuilder.registerTypeAdapter(WdClaim.class, new ClaimDeserializer());
					gson = gsonBuilder.create();
					Type type = new TypeToken<List<WdClaim>>(){}.getType();
					List<WdClaim> claims = gson.fromJson(aliasEntry.getValue(), type);
					claimMap.put(aliasEntry.getKey(), claims);
				}
			}
			entity.setClaims(claimMap);
			
			
			entities.add(entity);
		}
		
		return entitesObject;
	}
}
