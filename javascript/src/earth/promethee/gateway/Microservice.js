import Utils from "../utils/Utils";

class Microservice {
  
  /**
   * Construct a new microservice API with the server's root URL.
   * @param {string} url Server's root address
   */
  constructor(url) {
    if (!url.endsWith("/")) {
      url += "/";
    }
    this.url = url;
  }

  /**
   * Remote procedure call to the microservice API with a payload.
   * @param {string} endpoint API endpoint path
   * @param {object} payload Params for the request
   * @returns {object} Result fields from the response
   * @throws Error if the path is wrong or the response invalid
   */
  async call(endpoint, payload) {
	/* add token to payload before each call */
	payload.token = Utils.getCookie("promethee_token");
    const response = await fetch(
      this.url + endpoint,
      {method: "POST", body: JSON.stringify(payload)});

    if (!response.ok) {
      throw new Error("HTTP error " + response.status + " " + response.statusText);
    }

    const text = await response.text();
    
    try {
      return JSON.parse(text);
    } catch {
      throw new Error("Invalid response: " + text);
    }
  }
}

export default Microservice;
