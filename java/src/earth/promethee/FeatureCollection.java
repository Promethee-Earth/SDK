package earth.promethee;

import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FeatureCollection extends JSONObject implements Iterable<Feature> {
	String type;
	String stac_version;
	JsonArray stac_extensions;
	JsonObject context;
	int numberMatched;
	int numberReturned;
	JsonArray features;
	JsonArray links;
	
	public FeatureCollection(JsonObject json) {
		type = json.get("type").getAsString();
		stac_version = json.get("stac_version").getAsString();
		stac_extensions = json.get("stac_extensions").getAsJsonArray();
		context = json.get("context").getAsJsonObject();
		numberMatched = json.get("numberMatched").getAsInt();
		numberReturned = json.get("numberReturned").getAsInt();
		features = json.get("features").getAsJsonArray();
		links = json.get("links").getAsJsonArray();
	}

	public int size() {
		return features.size();
	}

	public Iterator<Feature> iterator() {
		return new Iterator<Feature>() {
			Iterator<JsonElement> i = features.iterator();

			public boolean hasNext() {
				return i.hasNext();
			}

			public Feature next() {
				return new Feature(i.next().getAsJsonObject());
			}
		};
	}
}
