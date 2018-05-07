package cdg.properties;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

public class CdgPropertiesManager {
	private static CdgPropertiesManager instance = null;
	private Properties properties;
	
	private CdgPropertiesManager() {
		properties = new Properties();
		try {
			Resource resource = new ClassPathResource(CdgConstants.PROPERTIES_FILE_PATH);
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			throw new IllegalStateException();
		}
	}
	
	public static CdgPropertiesManager getInstance() {
		if (instance == null) {
			instance = new CdgPropertiesManager();
		}
		return instance;
	}
	
	public Properties getProperties() {
		return this.properties;
	}
	
	public Object getProperty(Object key) {
		return properties.get(key);
	}
}
