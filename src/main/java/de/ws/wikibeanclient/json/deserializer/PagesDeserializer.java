/**
 * 
 */
package de.ws.wikibeanclient.json.deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import de.ws.wikibeanclient.json.Page;
import de.ws.wikibeanclient.json.Pages;

/**
 * @author Michael
 *
 */
public class PagesDeserializer implements JsonDeserializer<Pages> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	public Pages deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		Pages pagesObject = new Pages();
		List<Page> pages = new ArrayList<Page>();
		pagesObject.setPages(pages);
		
		JsonObject jsonObject = json.getAsJsonObject();
		for(Entry<String, JsonElement> jsonElement : jsonObject.entrySet()) {
			Gson gson = new Gson();
			pages.add(gson.fromJson(jsonElement.getValue(), Page.class));
		}
		
		return pagesObject;
	}

}
