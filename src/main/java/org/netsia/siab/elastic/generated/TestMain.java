package org.netsia.siab.elastic.generated;

import com.google.gson.Gson;

public class TestMain {
	/**
	 * Sample Request to Elastic Search
	 * 
	 * GET /./_search 
	{
	  "query": { 
	    "bool": { 
	      "must": [
	        { "match": { "serial_number": "PSMO12345678"}}, 
	        { "match": { "type": "cord-kafka" }}  
	      ],
	      "filter": [ 
	        { "term":  { "kafka_topic": "onu.events" }}, 
	        { "range": { "timestamp": { "gte": "2018-11-27T23:27:44.545Z" }}},
	        {"range": { "timestamp": { "lte": "2018-11-27T23:31:18.832Z" } }}
	      ]
	    }
	  }
	}**/

	
	public static void main(String[] args) {
		String jsonString = "{\r\n" + 
				"	  \"query\": { \r\n" + 
				"	    \"bool\": { \r\n" + 
				"	      \"must\": [\r\n" + 
				"	        { \"match\": { \"serial_number\": \"PSMO12345678\"}}, \r\n" + 
				"	        { \"match\": { \"type\": \"cord-kafka\" }}  \r\n" + 
				"	      ],\r\n" + 
				"	      \"filter\": [ \r\n" + 
				"	        { \"term\":  { \"kafka_topic\": \"onu.events\" }}, \r\n" + 
				"	        { \"range\": { \"timestamp\": { \"gte\": \"2018-11-27T23:27:44.545Z\" }}},\r\n" + 
				"	        {\"range\": { \"timestamp\": { \"lte\": \"2018-11-27T23:31:18.832Z\" } }}\r\n" + 
				"	      ]\r\n" + 
				"	    }\r\n" + 
				"	  }\r\n" + 
				"	}";

		Gson gson = new Gson();
		Elastic elastic = gson.fromJson(jsonString, Elastic.class);

		System.out.println("Finished!");

	}
	
	
}
