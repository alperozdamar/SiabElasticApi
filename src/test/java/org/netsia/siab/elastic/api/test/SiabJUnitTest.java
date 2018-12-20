package org.netsia.siab.elastic.api.test;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.netsia.siab.elastic.api.model.ApiQueryParameters;
import org.netsia.siab.elastic.api.parser.NetsiaJSonParser;

import com.google.gson.Gson;

/**
 * Junit Test Scenarios... Integration Test Scenarios... System Test
 * Scenarios...
 * 
 * 
 * Integration Tests : Integration tests generally test a path of execution
 * through your program. You may, for example, have a handler that takes as
 * input a request and then calls another method to post to to Slack. In this
 * case, you could use a mock request object and test that passing that mock
 * object to your handler results in a correct post to Slack.
 * 
 * System Tests System tests will test the end-to-end execution of your program.
 * A system test would use a client program to make a request (valid or invalid)
 * of your deployed server and verify that the response is correct.
 * 
 * @author alperoz
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SiabJUnitTest {

	private static Logger logger = LogManager.getLogger(SiabJUnitTest.class);

	public static final String ElasticApiServletUrl = "http://localhost:8080/search";

	public static final String expectedGeneratedJsonBody = "{\"query\":{\"bool\":{\"must\":[{\"match\":{\"serial_number\":\"PSMO12345678\"}},{\"match\":{\"serial_number\":\"PSMO12345678\"}},{\"match\":{\"type\":\"cord-kafka\"}}],\"filter\":[{\"range\":{\"timestamp\":{\"gte\":\"1542672000\"}}},{\"range\":{\"timestamp\":{\"lte\":\"1542672000\"}}},{\"term\":{\"kafka_topic\":\"onu.events\"}}]}}}";

	/**
	 * System Test
	 */
	// @Test
	// public void test_System_Test_SearchServlet() {
	// try {
	// TestHttpClient httpClient = new TestHttpClient();
	//
	// String resource = "PSMO12345678"; // onu serial number
	//
	// long startTimestamp = 1542672000; // Monday, November 19, 2018 4:00:00 PM
	// GMT-08:00
	// long endTimestamp = System.currentTimeMillis();
	//
	// // String granularity="1d"; //1month //1hour //1ms //5min //7d
	//
	// ApiQueryParameters query = new ApiQueryParameters(resource, startTimestamp,
	// endTimestamp);
	//
	// // ApiQueryParameters query = new ApiQueryParameters(resource);
	//
	// Gson gson = new Gson();
	// String jsonBody = gson.toJson(query);
	//
	// logger.debug(jsonBody);
	// logger.debug("Send Http POST request");
	//
	// httpClient.sendPost(ElasticApiServletUrl, jsonBody);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	@Test
	public void test_JsonGeneration_SearchServlet() {

		String resource = "PSMO12345678"; // onu serial number

		long startTimestamp = 1542672000; // Monday, November 19, 2018 4:00:00 PM GMT-08:00
		long endTimestamp = 1542672000;

		ApiQueryParameters query = new ApiQueryParameters(resource, startTimestamp, endTimestamp);

		Gson gson = new Gson();
		String jsonBody = gson.toJson(query);

		logger.debug(jsonBody);

		ApiQueryParameters incomingQueryParameters = NetsiaJSonParser.parseJSONAsQuery(jsonBody.toString());

		System.out.println(incomingQueryParameters.toString());

		try {

			String generatedJsonString = NetsiaJSonParser.generateJsonForElasticSearch(incomingQueryParameters);

			assertEquals("", expectedGeneratedJsonBody, generatedJsonString);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
