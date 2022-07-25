package earth.promethee;

import java.util.Properties;

public class User extends JSONObject {
	public String username;
	public String password;
	public String name;
	public String company;
	public String email;
	public String mobile;
	
	public User() {
	}

	public User(Properties properties) {
		username = properties.getProperty("username");
		password = properties.getProperty("password");
		name = properties.getProperty("name");
		company = properties.getProperty("company");
		email = properties.getProperty("email");
		mobile = properties.getProperty("mobile");
	}
}
