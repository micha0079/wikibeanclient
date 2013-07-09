package de.ws.wikibeanclient.json.deserializer;

import java.lang.reflect.Type;
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

import de.ws.wikibeanclient.json.wikidata.WdDataValue;
import de.ws.wikibeanclient.json.wikidata.WdReference;
import de.ws.wikibeanclient.json.wikidata.WdSnak;

/**
 * 
 * @author Michael
 *
 */
public class ReferenceDeserializer implements JsonDeserializer<WdReference>{
	
	/**
	 * 
	 */
	@Override
	public WdReference deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		WdReference reference = new WdReference();
		reference.setHash(json.getAsJsonObject().get("hash").getAsString());
		
		JsonElement snaksElement = json.getAsJsonObject().get("snaks");
		if(snaksElement!=null) {
			LinkedHashMap<String, List<WdSnak>> snaksMap = new LinkedHashMap<String, List<WdSnak>>();
			
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(WdDataValue.class, new DatavalueDeserializer());
			Gson gson = gsonBuilder.create();
			
			for(Entry<String, JsonElement> snakEntry : snaksElement.getAsJsonObject().entrySet()) {
				Type type = new TypeToken<List<WdSnak>>(){}.getType();
				List<WdSnak> snaks = gson.fromJson(snakEntry.getValue(), type);
				snaksMap.put(snakEntry.getKey(), snaks);
			}
			reference.setSnaks(snaksMap);
		}

		return reference;
	}
}
