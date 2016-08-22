package de.microstep;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
	
	private static final File CONFIG_DIR = new File("cfg");
	private static final String CONFIG_FILNAME_EXTENSION = ".cfg";
	
	private static Map<String, Config> configs = new HashMap<String, Config>();
	
	static{
		FilenameFilter configFilenameFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(CONFIG_FILNAME_EXTENSION);
			}
		};
		
		//Load all config files
		for(File configFile : CONFIG_DIR.listFiles(configFilenameFilter)) {
			String configName = configFile.getName().substring(0, configFile.getName().length() - CONFIG_FILNAME_EXTENSION.length());
			try{
				InputStream is = new FileInputStream(configFile);
				Properties configProps = new Properties();
				configProps.load(is);
				
				if(configProps.containsKey("config.name")){
					configName = configProps.getProperty("config.name");
					configProps.remove("config.name");
				}
				
				addConfig(configName, new Config(configProps));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		//Load system config
		addConfig("system", new Config(System.getProperties()));
	}
	
	public static Config getConfig(String name) {
		return configs.get(name);
	}
	
	private static void addConfig(String name, Config cfg){
		Config bevor = configs.put(name, cfg);
		if(bevor != null){
			System.err.println("Multiple configs with config name " + name + " were found, they will override themselves");
		}
	}
	
	private Properties props;
	
	public Config(Properties props) {
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
