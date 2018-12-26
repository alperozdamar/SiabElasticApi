package org.netsia.siab.elastic.api.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netsia.siab.elastic.api.client.ElasticClient;
import org.netsia.siab.elastic.api.model.ApiQueryParameters;
import org.netsia.siab.elastic.api.parser.NetsiaJSonParser;
import org.netsia.siab.elastic.config.ConfigurationManager;

public class SearchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6728769656081772510L;

	private static Logger logger = LogManager.getLogger(SearchServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.debug("doGet...");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.debug("doPost...");

		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			/* report an error */ }

		ApiQueryParameters incomingQueryParameters = NetsiaJSonParser.parseJSONAsQuery(jb.toString());

		System.out.println(incomingQueryParameters.toString());

		ElasticClient elasticClient = new ElasticClient();
		try {

			String generatedJsonString = NetsiaJSonParser.generateJsonForElasticSearch(incomingQueryParameters);

			String searchResult = elasticClient.sendGet(ConfigurationManager.getInstance().getElasticApiUrl(),
					generatedJsonString);

			System.out.println("Response sending back to Client...");

			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			PrintWriter out = response.getWriter();
			out.println(searchResult);

			System.out.println("Response is sent!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}



}
