package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private Properties properties;

	public ConfigReader() {
		try {
			FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
			properties = new Properties();
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not load config.properties file.");
		}
	}

	public String getProperty(String key) {
		if (properties.containsKey(key)) {
			return properties.getProperty(key);
		} else {
			throw new IllegalArgumentException("Unknown property: " + key);
		}
	}
}
