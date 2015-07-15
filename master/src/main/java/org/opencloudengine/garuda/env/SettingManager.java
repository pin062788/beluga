package org.opencloudengine.garuda.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 엔진내 conf/하위 셋팅들을 가지고 있다.
 * garuda.conf : 시스템 설정.
 *
 * @author Sang Wook, Song
 *
 */
public class SettingManager {
	private final Logger logger = LoggerFactory.getLogger(SettingManager.class);
	private Environment environment;
	private static Map<String, Object> settingCache = new HashMap<String, Object>();
	private static SettingManager instance;

	public SettingManager(Environment environment) {
		this.environment = environment;
	}

	public static SettingManager getInstance(){
		return instance;
	}
	public void asSingleton(){
		instance = this;
	}

	public String getConfigFilepath(String filename) {
		return environment.filePaths().configPath().path(filename).toString();
	}

	public Environment getEnvironment() {
		return environment;
	}

	private Properties getProperties(String configFilepath) {
		
		logger.debug("Read properties = {}", configFilepath);
		Properties result = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(configFilepath);
			result.load(is);
			return result;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(is != null){
					is.close();
				}
			} catch (IOException ignore) {
			}
		}
		return null;
	}

	private boolean storeProperties(Properties properties, String filename) {
		String configFilepath = getConfigFilepath(filename);
		logger.trace("Store properties = {}", configFilepath);
		
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(configFilepath);
			properties.store(os, new Date().toString());
			settingCache.put(configFilepath, new Settings(properties));
			return true;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(os != null){
					os.close();
				}
			} catch (IOException ignore) {
			}

		}
		
		return false;
	}
	
	public Settings getSystemSettings() {
		return getSettings(SettingFileNames.systemProperties);
	}
	
	public boolean storeSystemSettings(Settings settings) {
		return storeProperties(settings.properties(), SettingFileNames.systemProperties);
	}
	
	private Settings getSettings(String configFilename) {
		String configFilepath = getConfigFilepath(configFilename);
		Object obj = settingCache.get(configFilepath);
		if(obj != null){
			return (Settings) obj;
		}
		
		Properties properties = getProperties(configFilepath);
		Settings settings = new Settings(properties);
		settingCache.put(configFilepath, settings);
		return settings;
	}

}