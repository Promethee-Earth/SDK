import EndPoints from "./EndPoints.json";
import Microservice from "./Microservice.js";

class AreaService {
  static api = new Microservice(EndPoints.area);
}

export default AreaService;
