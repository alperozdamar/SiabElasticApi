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

				Filter filter[] = new Filter[3];
				filter[0] = new Filter();
				filter[1] = new Filter();
				filter[2] = new Filter();


				Term term = new Term();
				term.setKafka_topic("onu.events");

				Range range1 = new Range();
				Criteria timeStamp = new Criteria();
				timeStamp.setGte("" + incomingQueryParameters.getStartTimeStamp());
				range1.setTimestamp(timeStamp);
				Range range2 = new Range();
				Criteria timeStamp2 = new Criteria();
				timeStamp2.setLte("" + incomingQueryParameters.getEndTimeStamp());
				range2.setTimestamp(timeStamp2);

				filter[0].setTerm(term);
				filter[1].setRange(range1);
				filter[2].setRange(range2);


				Must must[] = new Must[2];
				must[0] = new Must();
				must[1] = new Must();

				Match match1 = new Match();
				match1.setSerial_number(incomingQueryParameters.getResource());

				Match match2 = new Match();
				match2.setType("cord-kafka");

				must[0].setMatch(match1);
				must[1].setMatch(match2);



				boolObject.setFilterArray(filter);
				boolObject.setMustArray(must);


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
