package org.netsia.siab.elastic.api.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netsia.siab.elastic.api.model.ApiQueryParameters;

import com.google.gson.Gson;

/**
 * Helper for our JUNIT tests.
 * 
 * And also it is neccessary for integration and system tests. This client sends
 * get and post request.
 * 
 * 
 * @author alperoz
 *
 */
public class TestHttpClient {

	private static Logger logger = LogManager.getLogger(TestHttpClient.class);

	private final String USER_AGENT = "Alper Test/1.0";

	public static void main(String[] args) throws Exception {
		TestHttpClient httpClient = new TestHttpClient();

		String servletUrl = "http://10.1.50.27:31120/search";

		// String servletUrl = "http://10.1.50.27:32025/search";

		//String servletUrl = "http://localhost:8085/search";

		String resource = "PSMO12345678"; // onu serial number

		long startTimestamp = 1542672000; // Monday, November 19, 2018 4:00:00 PM GMT-08:00
		long endTimestamp = System.currentTimeMillis();

		// String granularity="1d"; //1month //1hour //1ms //5min //7d

		ApiQueryParameters query = new ApiQueryParameters(resource, startTimestamp, endTimestamp);

		// ApiQueryParameters query = new ApiQueryParameters(resource);

		Gson gson = new Gson();
		String jsonBody = gson.toJson(query);

		logger.debug(jsonBody);
		logger.debug("Send Http POST request");

		httpClient.sendPost(servletUrl, jsonBody);
	}

	// HTTP GET request
	public String sendGet(String url) throws Exception {

		URL obj = new URL(url);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		logger.debug("\nSending 'GET' request to URL : " + url);
		logger.debug("Response Code : " + responseCode);

		StringBuffer response = new StringBuffer();
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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

	public String sendPost(String targetURL, String postBody) throws Exception {
		URL url = new URL(targetURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// add request header
		connection.setRequestMethod("POST");
		connection.setRequestProperty("User-Agent", USER_AGENT);
		connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		// Send post request
		connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

		logger.debug("jsonBody:" + postBody);

		wr.write(postBody.getBytes("UTF-8"));
		wr.flush();
		wr.close();

		int responseCode = connection.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);

		System.out.println("Response Code : " + responseCode);

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

		// print result
		System.out.println(response.toString());

		System.out.println("response Length:" + response.length());

		return response.toString();
	}

}
