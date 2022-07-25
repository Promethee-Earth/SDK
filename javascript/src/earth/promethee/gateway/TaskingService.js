import EndPoints from "./EndPoints.json";
import Microservice from "./Microservice.js";

class TaskingService {
  static api = new Microservice(EndPoints.task);

  static async getTaskings() {
    try {
      const response = await TaskingService.api.call("getTasks", {});
      console.log(response);
      return response;
    } catch (e) {
      console.error(e);
      return false;
    }
  }

  static findModuleInData = (data, module_name) => {
    return data.find(e => e.name === module_name);
  }

  static twoPointsCoords(coords) {
    let latmin = coords[0][0][0];
    let latmax = coords[0][0][0];
    let lngmin = coords[0][0][1];
    let lngmax = coords[0][0][1];
    coords.forEach((elem) => elem.forEach((e) => {
      if (e[0] < latmin)
        latmin = e[0];
      if (e[0] > latmax)
        latmax = e[0];
      if (e[1] < lngmin)
        lngmin = e[1];
      if (e[1] > lngmax)
        lngmax = e[1];
    }))
    return [[latmin, lngmin], [latmax, lngmax]];
  }

  static dateToRequest(data) {
    const date_mod = TaskingService.findModuleInData(data, "Date");

    if (!date_mod)
      return "";

    let date;
    if (typeof date_mod.fields.calendar === "number")
      date = new Date(date_mod.fields.calendar);
    else
      date = new Date(date_mod.fields.calendar[0]);

    const year = date.getFullYear().toString();

    let month = (date.getMonth() + 1).toString();
    if (month.length === 1)
      month = "0" + month;

    let day = date.getDate().toString();
    if (day.length === 1)
      day = "0" + day;

    return `&date_in=${year}${month}${day}`;
  }

  static detect_algo_values = {
    land: 1,
    boat: 2,
    cloud: 4
  };

  static detectorsValue(data) {
    const land_mod = TaskingService.findModuleInData(data, "Classification des sols");
    const boat_mod = TaskingService.findModuleInData(data, "Détection de bateaux");
    const cloud_mod = TaskingService.findModuleInData(data, "Détection de nuages");

    const detect_algo_values = TaskingService.detect_algo_values;

    let detect_value = 0;
    if (land_mod)
      detect_value += detect_algo_values.land;
    if (boat_mod)
      detect_value += detect_algo_values.boat;
    if (cloud_mod)
      detect_value += detect_algo_values.cloud;

    return detect_value;
  }

  static async saveTasking(data) {
    const properties_mod = TaskingService.findModuleInData(data, "Propriétés");
    const name = properties_mod.fields.name;

    const zone_mod = TaskingService.findModuleInData(data, "Zone");
    const coords = JSON.parse(zone_mod.fields.geoJSON_object).coordinates;
    const two_points_coords = TaskingService.twoPointsCoords(coords);
    const longitude0 = two_points_coords[0][0];
    const latitude0 = two_points_coords[0][1];
    const longitude1 = two_points_coords[1][0];
    const latitude1 = two_points_coords[1][1];

    const date = TaskingService.dateToRequest(data);

    const detect_value = TaskingService.detectorsValue(data);

    try {
      const response = await TaskingService.api.call("createTask", {
        name: name + date,
        longitude0: longitude0,
        latitude0: latitude0,
        longitude1: longitude1,
        latitude1: latitude1,
        detectors: detect_value});
      console.log(response);
      return true;
    } catch (e) {
      console.error(e);
      return false
    }
  }
}

export default TaskingService;
