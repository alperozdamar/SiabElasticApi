package org.netsia.siab.elastic.api.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElasticClient {

	private static Logger logger = LogManager.getLogger(ElasticClient.class);

	private final String USER_AGENT = "Elastic Search/1.0";

	
	/**
	 * Or you can just put * instead of these indexes : logstash-2018.11.27,logstash-2018.11.29
	 * 
	 * curl -XGET
	 * "http://logging-elasticsearch-client:9200/logstash-2018.11.27,logstash-2018.11.29/_search"
	 * -H 'Content-Type: application/json' -d' { "query": { "bool": { "must": [ {
	 * "match": { "serial_number": "PSMO12345678" }}, { "match": { "type":
	 * "cord-kafka" }} ], "filter": [ { "term": { "kafka_topic": "onu.events" }}, {
	 * "range": { "timestamp": { "gte": "2018-11-20" }}} ] } } }'
	 * 
	 * 
	 * @param url
	 * @param jsonBody
	 * @return
	 * @throws Exception
	 */
	
	// HTTP GET request
	public String sendGet(String url, String jsonBody) throws Exception {

		url = url + "/*/_search";
		// url = url + "/logstash-2018.11.27/_search";
		URL obj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("GET");

		// add request header
		connection.setRequestProperty("User-Agent", USER_AGENT);

		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());


		logger.debug("jsonBody:" + jsonBody);

		wr.write(jsonBody.getBytes("UTF-8"));
		wr.flush();
		wr.close();

		int responseCode = connection.getResponseCode();
		logger.debug("\nSending 'GET' request to URL : " + url);
		logger.debug("Response Code : " + responseCode);

		StringBuffer response = new StringBuffer();
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} else {
			response.append(responseCode);
		}

		logger.debug(response.toString());

		return response.toString();
	}

}
