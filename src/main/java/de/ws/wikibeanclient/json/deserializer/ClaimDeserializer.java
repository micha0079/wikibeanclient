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
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import de.ws.wikibeanclient.json.wikidata.WdClaim;
import de.ws.wikibeanclient.json.wikidata.WdDataValue;
import de.ws.wikibeanclient.json.wikidata.WdReference;
import de.ws.wikibeanclient.json.wikidata.WdSnak;

/**
 * @author Michael
 *
 */
public class ClaimDeserializer  implements JsonDeserializer<WdClaim> {
	
	/**
	 * 
	 */
	@Override
	public WdClaim deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(WdDataValue.class, new DatavalueDeserializer());
		gsonBuilder.registerTypeAdapter(WdReference.class, new ReferenceDeserializer());
		Gson gson = gsonBuilder.create();
		
		WdClaim claim = new WdClaim();
		claim.setId(json.getAsJsonObject().get("id").getAsString());
		claim.setRank(json.getAsJsonObject().get("rank").getAsString());
		claim.setType(json.getAsJsonObject().get("type").getAsString());
		claim.setMainSnak(gson.fromJson(json.getAsJsonObject().get("mainsnak"), WdSnak.class));
		
		List<WdReference> references = new ArrayList<WdReference>();
		JsonElement referencesElement = json.getAsJsonObject().get("references");
		if(referencesElement!=null) {
			references = gson.fromJson(referencesElement, new TypeToken<List<WdReference>>(){}.getType());
		}
		claim.setReferences(references);
		
		LinkedHashMap<String, List<WdSnak>> qualifiers = new LinkedHashMap<String, List<WdSnak>>();
		JsonElement qualifiersElement = json.getAsJsonObject().get("qualifiers");
		if(qualifiersElement!=null) {
			for(Entry<String, JsonElement> qualifierElement : qualifiersElement.getAsJsonObject().entrySet()) {
				List<WdSnak> qualifiersList = gson.fromJson(qualifierElement.getValue(), new TypeToken<List<WdSnak>>(){}.getType());
				qualifiers.put(qualifierElement.getKey(), qualifiersList);
			}
		}
		claim.setQualifiers(qualifiers);
		
		return claim;
	}

}
