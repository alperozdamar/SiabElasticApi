package org.netsia.siab.elastic.api.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netsia.siab.elastic.api.model.ApiQueryParameters;

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

	/**
	 * For Test purposes..
	 */
	public static void main(String[] args) {
		JsonParser parser = new JsonParser();

	}

}
