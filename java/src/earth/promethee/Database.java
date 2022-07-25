package earth.promethee;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Database {
	private static long id = System.currentTimeMillis();
	private String home;

	public Database() {
		this("/opt/wildfly");
	}
	
	public Database(String home) {
		this.home = home;
	}
	
	public void create(User user) throws IOException {
		user.id = this.id++;
		
		File folder = new File(home, "users");
		File file = new File(folder, user.id + ".properties");

		Properties properties = new Properties();
		properties.setProperty("id", String.valueOf(user.id));
		properties.setProperty("username", user.username);
		properties.setProperty("password", user.password);
		properties.setProperty("name", user.name);
		properties.setProperty("company", user.company);
		properties.setProperty("email", user.email);

		Writer out = new FileWriter(file);
		properties.store(out, null);
		out.close();
	}

	public List<User> getUsers() throws IOException {
		List<User> users = new ArrayList();

		File folder = new File(home, "users");
		String[] filenames = folder.list();
		Arrays.sort(filenames);
		
		for (String filename : filenames) {
			String id = filename.replace(".properties", "");
			User user = getUser(Long.parseLong(id));
			users.add(user);
		}

		return users;
	}

	public User getUser(long id) throws IOException {
		File folder = new File(home, "users");
		File file = new File(folder, id + ".properties");
		if (!file.exists())
			return null;

		Reader in = new FileReader(file);
		Properties properties = new Properties();
		properties.load(in);
		in.close();
		
		User user = new User(properties);
		return user;
	}
	
	public User getUserByUsername(String username) throws IOException {
		for (User user : getUsers()) {
			if (user.username.equals(username))
				return user;
		}
		return null;
	}

	public void create(Tasking task) throws IOException {
		task.id = this.id++;
		
		File folder = new File(home, "tasks");
		File file = new File(folder, task.id + ".properties");

		Properties properties = new Properties();
		properties.setProperty("id", String.valueOf(task.id));
		properties.setProperty("name", task.name);
		properties.setProperty("lonmin", String.valueOf(task.lonmin));
		properties.setProperty("latmin", String.valueOf(task.latmin));
		properties.setProperty("lonmax", String.valueOf(task.lonmax));
		properties.setProperty("latmax", String.valueOf(task.latmax));
		properties.setProperty("detectors", String.valueOf(task.detectors));

		Writer out = new FileWriter(file);
		properties.store(out, null);
		out.close();
	}

	public List<Tasking> getTaskings() throws IOException {
		List<Tasking> tasks = new ArrayList();

		File folder = new File(home, "tasks");
		String[] filenames = folder.list();
		Arrays.sort(filenames);
		
		for (String filename : filenames) {
			String id = filename.replace(".properties", "");
			Tasking task = getTasking(Long.parseLong(id));
			tasks.add(task);
		}

		return tasks;
	}
	
	public Tasking getTasking(long id) throws IOException {
		File folder = new File(home, "tasks");
		File file = new File(folder, id + ".properties");
		if (!file.exists())
			return null;

		Reader in = new FileReader(file);
		Properties properties = new Properties();
		properties.load(in);
		in.close();
		
		Tasking task = new Tasking(properties);
		return task;
	}

	public void create(Library folder) throws IOException {
	}

	public void create(Area area) throws IOException {
	}

	public void create(Image image) throws IOException {
	}
	
	public void setTaskingStatus(long id, int status) {
	}
}
