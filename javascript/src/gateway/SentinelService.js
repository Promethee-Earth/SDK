import EndPoints from "./EndPoints.json";
import Microservice from "./Microservice.js";

class SentinelService {
  static api = new Microservice(EndPoints.sentinel);

  static async search(lonmin, latmin, lonmax, latmax) {
    try {
	  const payload = {
		lonmin: lonmin,
		latmin: latmin,
		lonmax: lonmax,
		latmax: latmax
	  };
      const response = await SentinelService.api.call("search", payload);
      console.log(response);
      return response;
    } catch (e) {
      console.error(e);
      return false;
    }
  }
}

export default SentinelService;
