package de.microstep;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Class for Configuration management
 * @author Klaus Mattis
 * @since 24.08.2016
 */
public class Configs {
	
	private static final File CONFIG_DIR;
	private static final String CONFIG_FILNAME_EXTENSION = ".cfg";
	
	private static Map<String, Configs> configs = new HashMap<String, Configs>();
	
	static{
		try {
			CONFIG_DIR = new File(Configs.class.getResource("config/").toURI());
		} catch (URISyntaxException e1) {
			throw new IOError(e1);
		}
		
		FilenameFilter configFilenameFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(CONFIG_FILNAME_EXTENSION);
			}
		};
		
		//Load all config files
		for(File configFile : CONFIG_DIR.listFiles(configFilenameFilter)) {
			String configName = configFile.getName().substring(0, configFile.getName().length() - CONFIG_FILNAME_EXTENSION.length());
			try(InputStream is = new FileInputStream(configFile)){
				Properties configProps = new Properties();
				configProps.load(is);
				
				configs.put(configName, new Configs(configProps));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		//Load system config
		configs.put("system", new Configs(System.getProperties()));
	}
	
	public static Configs getConfig(String name) {
		return configs.get(name);
	}
	
	private Properties props;
	
	public Configs(Properties props) {
		this.props = props;
	}
	
	public String getString(String key){
		return props.getProperty(key);
	}
	
	public int getInt(String key){
		return Integer.parseInt(props.getProperty(key));
	}
	
	public double getDouble(String key){
		return Double.parseDouble(props.getProperty(key));
	}
	
	public long getLong(String key){
		return Long.parseLong(props.getProperty(key));
	}
	
	public boolean getBoolean(String key){
		return Boolean.parseBoolean(props.getProperty(key));
	}

}
