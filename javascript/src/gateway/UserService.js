import EndPoints from "./EndPoints.json";
import Microservice from "./Microservice.js";

class UserService {
  static api = new Microservice(EndPoints.user);

  static async createAccount(account) {
    try {
      const response = await UserService.api.call("createUser", {
        username: account.email,
        email: account.email,
        name: account.name,
        company: account.company,
        password: account.password});
      console.log("Account created: ", response);
      return true;
    } catch (e) {
      console.error(e);
      return false;
    }
  }

  static async connect(username, password) {
    try {
      const response = await UserService.api.call("login",
        {username: username, password: password});
      console.log("Connected: ", response);
      document.cookie = `promethee_token=${response.token};path=/`;
      return response.token ? true : false;
    } catch (e) {
      console.error(e);
      return false;
    }
  }

  static async disconnect() {
    console.log("try to disconnect...");
    try {
      const response = await UserService.api.call("logout", {});
      document.cookie = `promethee_token=;expires=${new Date(0)};path=/`;
      console.log("Disconnected: ", response);
      return true;
    } catch (e) {
      console.error(e);
      return false;
    }
  }

  static async getUser(username) {
    try {
      const response = await UserService.api.call("getUser",
        {username: username});
      console.log("User: ", response);
      return response;
    } catch (e) {
      console.error(e);
      return false;
    }
  }
}

export default UserService;
