package com.sap.mentors.lemonaid.entities;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="events")
public class Event {	

	@Id
	private String id;
	private String source;
    private String sourceId;
    private String name;
    private String location;
	@Temporal(TIMESTAMP) private Calendar startDate;
	@Temporal(TIMESTAMP) private Calendar endDate;
	private String url;
	private String deepUrl;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Calendar getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
	public Calendar getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getDeepUrl() {
		return deepUrl;
	}

	public void setDeepUrl(String deepUrl) {
		this.deepUrl = deepUrl;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", source=" + source + ", sourceId=" + sourceId + ", name=" + name + ", location="
				+ location + ", startDate=" + startDate + ", endDate=" + endDate + ", url=" + url + ", deepUrl="
				+ deepUrl + "]";
	}

}
