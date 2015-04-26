package es.upm.dit.isst.labreserve.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reserve implements Serializable {


	private static final Long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String author;
	private Long resourceID;
	String date;
	String email;
	String initHour;
	String finalHour;
	int priority;
	String resourceName;


	public Reserve(String author, String email, String resourceName, Long resourceID, String date, String initHour, String finalHour, int priority) {
		this.author = author;
		this.resourceID= resourceID;
		this.email = email;
		this.date = date;
		this.initHour = initHour;
		this.finalHour = finalHour;
		this.resourceName = resourceName;
		this.priority = priority;
	}

	public Long getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getResourceID() {
		return resourceID;
	}

	public void setResourceID(Long resourceID) {
		this.resourceID = resourceID;
	}
	
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getDate() {
		return date;
	}

	public void  setDate (String date) {
		this.date = date;
	}
	
	public String getInitHour() {
		return initHour;
	}

	public void  setInitHour (String initHour) {
		this.initHour = initHour;
	}
	
	public String getFinalHour() {
		return finalHour;
	}

	public void  setFinalHour (String finalHour) {
		this.finalHour = finalHour;
	}
	
	public int getPriority() {
		return priority;
	}

	public void  setPriority (int priority) {
		this.priority = priority;
	}
} 
