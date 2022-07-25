package earth.promethee;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Feature extends JSONObject {	
	public String id;
	JsonArray bbox;
	JsonObject geometry;
	JsonObject properties;
	public String collection;
	JsonObject assets;
	
	public Feature(JsonObject json) {
		id = json.get("id").getAsString();
		bbox = json.get("bbox").getAsJsonArray();
		geometry = json.get("geometry").getAsJsonObject();
		properties = json.get("properties").getAsJsonObject();
		collection = json.get("collection").getAsString();
		assets = json.get("assets").getAsJsonObject();
	}
	
	public boolean isCOGS() {
		return collection.endsWith("-cogs");
	}

	public String getPhoto() {
		JsonObject visual = assets.get("visual").getAsJsonObject();
		String herf = visual.get("href").getAsString();
		return herf;
	}

	public String getThumbnail() {
		JsonObject thumbnail = assets.get("thumbnail").getAsJsonObject();
		String href = thumbnail.get("href").getAsString();
		return href;
	}

	public String getPlatform() {
		return properties.get("platform").getAsString();
	}

	public String getConstellation() {
		return properties.get("constellation").getAsString();
	}

	public String getZone() {
		return properties.get("sentinel:utm_zone").getAsString()
				+ properties.get("sentinel:latitude_band").getAsString()
				+ properties.get("sentinel:grid_square").getAsString();
	}

	public List<Image> getImages() {
		List<Image> images = new ArrayList();
		
		String[] ids = { "B02", "B03", "B04", "B05", "B06", "B07", "B08" };
		for (String id : ids) {
			JsonObject asset = assets.get(id).getAsJsonObject();
			String name = asset.get("title").getAsString();
			String url = asset.get("href").getAsString();
			String thumbnail = null;
			int width = 0;
			int height = 0;
			if (asset.get("proj:shape") != null) {
				width = asset.get("proj:shape").getAsJsonArray().get(1).getAsInt();
				height = asset.get("proj:shape").getAsJsonArray().get(0).getAsInt();
			}
			//Image image = new Image(name, url, thumbnail, width, height);
			//images.add(image);
		}

		//Image image = new Image("TCI", getPhoto(), null, 0, 0);
		//images.add(image);

		return images;
	}

	public String toString() {
		return id;
	}
}
