import EndPoints from "./EndPoints.json";
import Microservice from "./Microservice.js";

class ImageService {
  static api = new Microservice(EndPoints.image);
}

export default ImageService;
