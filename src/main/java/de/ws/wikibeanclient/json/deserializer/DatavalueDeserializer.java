package de.ws.wikibeanclient.json.deserializer;

import java.lang.reflect.Type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import de.ws.wikibeanclient.json.wikidata.WdDataValue;
import de.ws.wikibeanclient.json.wikidata.WdValue;
import de.ws.wikibeanclient.util.TextUtil;

/**
 * 
 * @author Michael
 *
 */
public class DatavalueDeserializer implements JsonDeserializer<WdDataValue>{

	private static Logger log = LogManager
			.getLogger(DatavalueDeserializer.class);
	
	/**
	 * 
	 */
	@Override
	public WdDataValue deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		WdDataValue dataValue = new WdDataValue();
		dataValue.setType(json.getAsJsonObject().get("type").getAsString());
		if(TextUtil.isSet(dataValue.getType())) {
			if(dataValue.getType().equals("string")) {
				dataValue.setValue(json.getAsJsonObject().get("value").getAsString());
			} else if (dataValue.getType().equals("wikibase-entityid") || dataValue.getType().equals("time")) {
				Gson gson = new Gson();
				dataValue.setValue(gson.fromJson(json.getAsJsonObject().get("value"), WdValue.class));
			}  else if (dataValue.getType().equals("globecoordinate")) {
				//TODO 
			} else {
				log.error("Unrecognized datavalue type: "+dataValue.getType());
			}
		}
 		
		return dataValue;
	}
}
