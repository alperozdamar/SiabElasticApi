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

	// private String elasticApiUrl = "http://10.1.50.27:31438"; //
	// http://10.1.50.27:30601

	private String elasticsearchUrl = "http://logging-elasticsearch-client";
	private String elasticsearchAddress = ""; // test purpose 10.1.50.27
	private String elasticsearchPort = ""; // test purpose 31438
	private String elasticsearchIndexFormat = ""; // test elastic index


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
				String elasticsearchUrlString = props.getProperty("elastic_search_url").trim();
				elasticsearchUrl = elasticsearchUrlString;
				String elasticsearchAddressString = props.getProperty("elastic_search_address").trim();
				elasticsearchAddress = elasticsearchAddressString;
				String elasticsearchPortString = props.getProperty("elastic_search_port").trim();
				elasticsearchPort = elasticsearchPortString;
				String elasticsearchIndexFormatString = props.getProperty("elastic_index_format").trim();
				elasticsearchIndexFormat = elasticsearchIndexFormatString;

				logger.debug("elasticsearch.url:" + elasticsearchUrl);
				logger.debug("serverPort:" + serverPort);
				logger.debug("elasticsearchAddress:" + elasticsearchAddress);
				logger.debug("elasticsearchPort:" + elasticsearchPort);
				logger.debug("elasticsearchIndexFormat:" + elasticsearchIndexFormat);

				System.out.println("elasticsearch.url:" + elasticsearchUrl);
				System.out.println("serverPort:" + serverPort);
				System.out.println("elasticsearchAddress:" + elasticsearchAddress);
				System.out.println("elasticsearchPort:" + elasticsearchPort);
				System.out.println("elasticsearchIndexFormat:" + elasticsearchIndexFormat);

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
		return elasticsearchUrl;
	}

	public void setElasticApiUrl(String elasticApiUrl) {
		this.elasticsearchUrl = elasticApiUrl;
	}

	public String getElasticsearchUrl() {
		return elasticsearchUrl;
	}

	public void setElasticsearchUrl(String elasticsearchUrl) {
		this.elasticsearchUrl = elasticsearchUrl;
	}

	public String getElasticsearchAddress() {
		return elasticsearchAddress;
	}

	public void setElasticsearchAddress(String elasticsearchAddress) {
		this.elasticsearchAddress = elasticsearchAddress;
	}

}
