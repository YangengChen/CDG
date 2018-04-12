package cdg.properties;

import java.io.InputStream;
import java.util.Properties;


public class CdgPropertiesManager {
	
	Properties properties;
	
	public CdgPropertiesManager() {
		this.properties = new Properties();
	}

	public void setProperties(InputStream inStream) throws Exception {
		this.properties.load(inStream);
	}
	
	public Properties getProperties() {
		return this.properties;
	}
	

}
