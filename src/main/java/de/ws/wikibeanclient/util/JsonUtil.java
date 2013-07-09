/**
 * @author Michael
 * @date 28.06.2013
 */
package de.ws.wikibeanclient.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.ws.wikibeanclient.json.Api;
import de.ws.wikibeanclient.json.Pages;
import de.ws.wikibeanclient.json.deserializer.EntitiesDeserializer;
import de.ws.wikibeanclient.json.deserializer.PagesDeserializer;
import de.ws.wikibeanclient.json.wikidata.WdEntities;

/**
 * @author Michael
 * @date 28.06.2013
 */
public class JsonUtil {

	private static Logger log = LogManager.getLogger(JsonUtil.class);

	/**
	 * creates the Api and child objects from a json string
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Api createFromJson(String jsonString) {
		
		Api api = null;
		
		if(TextUtil.isSet(jsonString)) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Pages.class,
					new PagesDeserializer());
			
			gsonBuilder.registerTypeAdapter(WdEntities.class, new EntitiesDeserializer());

			Gson gson = gsonBuilder.create();

			if (!jsonString.equals("[]")) {
				api = gson.fromJson(jsonString, Api.class);
			}	
		}
		return api;
	}
}
