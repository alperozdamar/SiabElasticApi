package org.netsia.siab.elastic.api.model;

/***
 * 
 * @author alperoz
 *
 */
public class ApiQueryParameters {

	private String resource;

	private long startTimeStamp;

	private long endTimeStamp;

	private String granularity;

	public ApiQueryParameters(String resource, long startTimeStamp, long endTimeStamp) {
		super();
		this.resource = resource;
		this.startTimeStamp = startTimeStamp;
		this.endTimeStamp = endTimeStamp;
	}

	public ApiQueryParameters(String resource) {
		super();
		this.resource = resource;
	}
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getGranularity() {
		return granularity;
	}

	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}

	public long getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	public long getEndTimeStamp() {
		return endTimeStamp;
	}

	public void setEndTimeStamp(long endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}

	@Override
	public String toString() {
		return "Query [resource=" + resource + ", startTimeStamp=" + startTimeStamp + ", endTimeStamp=" + endTimeStamp
				+ ", granularity=" + granularity + "]";
	}

}
