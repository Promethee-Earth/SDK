package earth.promethee;

import java.util.Properties;

public class Tasking extends JSONObject {
	public String name;
	public double lonmin;
	public double latmin;
	public double lonmax;
	public double latmax;
	public int detectors;
	
	public Tasking() {
	}

	public Tasking(Properties properties) {
		name = properties.getProperty("name");
		lonmin = Double.parseDouble(properties.getProperty("lonmin"));
		latmin = Double.parseDouble(properties.getProperty("latmin"));
		lonmax = Double.parseDouble(properties.getProperty("lonmax"));
		latmax = Double.parseDouble(properties.getProperty("latmax"));
		detectors = Integer.parseInt(properties.getProperty("detectors"));
	}
	
	public boolean hasStatus() {
		return false;
	}
}
