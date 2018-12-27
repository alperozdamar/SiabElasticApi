package org.netsia.siab.elastic.config;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * Configuration Manager .
 * 
 * @author alperoz
 *
 */
public class ConfigurationManager {

	private static Logger logger = LogManager.getLogger(ConfigurationManager.class);

	public static final String SIAB_ELASTIC_API_CONFIG_FILE = "resources/siabElasticApi.properties";

	private static ConfigurationManager instance;
	private final static Object classLock = new Object();

	private int serverPort = 7070;

	private String elasticApiUrl = "http://10.1.50.27:31438"; // http://10.1.50.27:30601

	private ConfigurationManager() {
		readConfigFile();
	}

	public static ConfigurationManager getInstance() {
		synchronized (classLock) {
			if (instance == null) {
				instance = new ConfigurationManager();
			}
			return instance;
		}
	}

	public void readConfigFile() {

		Properties props = new Properties();
		try {
			props.load(new FileInputStream(SIAB_ELASTIC_API_CONFIG_FILE));

			try {
				String serverPortString = props.getProperty("searchServerPort").trim();
				serverPort = (serverPortString == null) ? 7070 : Integer.parseInt(serverPortString);

				String elasticApiUrlString = props.getProperty("elastic.api.url").trim();

				elasticApiUrl = elasticApiUrlString;

				logger.debug("elastic.api.url:" + elasticApiUrl);
				logger.debug("serverPort:" + serverPort);

			} catch (Exception e) {
				serverPort = 7070;
				e.printStackTrace();
			}

		} catch (Exception e) {
			System.err.println("Exception occured while parsing Configuration File:" + SIAB_ELASTIC_API_CONFIG_FILE);
			e.printStackTrace();
		}
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getElasticApiUrl() {
		return elasticApiUrl;
	}

	public void setElasticApiUrl(String elasticApiUrl) {
		this.elasticApiUrl = elasticApiUrl;
	}

}
