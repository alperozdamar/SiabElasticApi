package org.netsia.siab.elastic.api.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netsia.siab.elastic.api.model.ApiQueryParameters;
import org.netsia.siab.elastic.api.servlet.Match;
import org.netsia.siab.elastic.generated.Bool;
import org.netsia.siab.elastic.generated.Criteria;
import org.netsia.siab.elastic.generated.Elastic;
import org.netsia.siab.elastic.generated.Filter;
import org.netsia.siab.elastic.generated.Must;
import org.netsia.siab.elastic.generated.Query;
import org.netsia.siab.elastic.generated.Range;
import org.netsia.siab.elastic.generated.Term;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 * 
 * @author alperoz
 *
 *         This is the class that JSON datas are parsed with the help of GSON
 *         API.
 *
 */
public class NetsiaJSonParser {

	private static Logger logger = LogManager.getLogger(NetsiaJSonParser.class);

	public static ApiQueryParameters parseJSONAsQuery(String data) {
		ApiQueryParameters query = null;
		try {
			// JsonParser parser = new JsonParser();
			// JsonElement jsonElement = parser.parse(data);
			// JsonObject object = jsonElement.getAsJsonObject();
			// String startDatetime = object.get("datetime").getAsString();
			// SimpleDateFormat insdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
			// Locale.US);
			// SimpleDateFormat outsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// String parsedStartDatetime = outsdf.format(insdf.parse(startDatetime));
			// String endDatetime = object.get("datetime").getAsString();
			// insdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			// outsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// String parsedEndDatetime = outsdf.format(insdf.parse(endDatetime));
			// String resource = object.get("resource").getAsString();
			// String granularity = object.get("granularity").getAsString();
			//
			// query = new Query(resource, parsedStartDatetime, parsedEndDatetime);

			// } else {
			// System.err.println("Error in line of QA. Null value in a record asin:<" +
			// asin
			// + ">");
			// }

			Gson gson = new Gson();

			query = gson.fromJson(data, ApiQueryParameters.class);

		} catch (Exception e) {
			System.err.println("Exception occured while parsing JSON DATA:" + data);
			e.printStackTrace();
		}
		return query;
	}

	public static String generateJsonForElasticSearch(ApiQueryParameters incomingQueryParameters) {
		String generatedJsonString = "";

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
			generatedJsonString = gson.toJson(elastic);

			logger.debug("Build Json String as :" + generatedJsonString);

		}
		return generatedJsonString;
	}

	/**
	 * For Test purposes..
	 */
	public static void main(String[] args) {
		JsonParser parser = new JsonParser();

	}

}
