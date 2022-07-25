import EndPoints from "EndPoints.json";

export class Gateway {
	constructor() {
		this.url = EndPoints.gateway;
	}
	
	async createArea(name, lonmin, latmin, lonmax, latmax) {
		try {
			const request = { name, lonmin, latmin, lonmax, latmax };
			const response = await call('createArea', request);
			return response;
		} catch (e) {
			console.error(e);
			return null;
		}
	}

	async getAreas() {
		try {
			const request = {};
			const response = await call('getAreas', request);
			return response;
		} catch (e) {
			console.error(e);
			return null;
		}
	}

	async call(endpoint, payload) {
		/* add token to payload before each call */
		payload.token = Utils.getCookie("promethee_token")
		
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
