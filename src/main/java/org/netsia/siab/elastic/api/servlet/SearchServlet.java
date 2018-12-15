package org.netsia.siab.elastic.api.servlet;

import java.io.BufferedReader;
import java.io.IOException;

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
import org.netsia.siab.elastic.generated.Bool;
import org.netsia.siab.elastic.generated.Criteria;
import org.netsia.siab.elastic.generated.Elastic;
import org.netsia.siab.elastic.generated.Filter;
import org.netsia.siab.elastic.generated.Must;
import org.netsia.siab.elastic.generated.Query;
import org.netsia.siab.elastic.generated.Range;
import org.netsia.siab.elastic.generated.Term;

import com.google.gson.Gson;

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

			String jsonBody = "";

			/**
			 * Prepare JsonBody with incoming parameters from Client.
			 */
			if (incomingQueryParameters != null) {
				logger.debug("Incoming Query Parameters:" + incomingQueryParameters.toString());

				Query query = new Query();
				Bool boolObject = new Bool();

				Term term = new Term();
				term.setKafka_topic("onu.events");

				if (incomingQueryParameters.getStartTimeStamp() != 0) {
					Range range1 = new Range();
					Criteria timeStamp = new Criteria();
					timeStamp.setGte("" + incomingQueryParameters.getStartTimeStamp());
					range1.setTimestamp(timeStamp);
					Filter filter2 = new Filter();
					filter2.setRange(range1);
					boolObject.getFilterList().add(filter2);
				}

				if (incomingQueryParameters.getEndTimeStamp() != 0) {
					Range range2 = new Range();
					Criteria timeStamp2 = new Criteria();
					timeStamp2.setLte("" + incomingQueryParameters.getEndTimeStamp());
					range2.setTimestamp(timeStamp2);
					Filter filter3 = new Filter();
					filter3.setRange(range2);
					boolObject.getFilterList().add(filter3);
				}

				Filter filter = new Filter();
				filter.setTerm(term);
				boolObject.getFilterList().add(filter);

				if (incomingQueryParameters.getResource() != null) {
					Match match1 = new Match();
					match1.setSerial_number(incomingQueryParameters.getResource());
					Must must = new Must();
					must.setMatch(match1);
					boolObject.getMustList().add(must);
					boolObject.getMustList().add(must);
				}

				Match match2 = new Match();
				match2.setType("cord-kafka");
				Must must2 = new Must();
				must2.setMatch(match2);
				boolObject.getMustList().add(must2);
				query.setBool(boolObject);
				Elastic elastic = new Elastic();
				elastic.setQuery(query);

				Gson gson = new Gson();
				String generatedJsonString = gson.toJson(elastic);

				logger.debug("Build Json String as :" + generatedJsonString);

			}

			elasticClient.sendGet(ConfigurationManager.getInstance().getElasticApiUrl(), jsonBody);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
