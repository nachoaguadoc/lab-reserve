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



	public Reserve(String author, Long resourceID, String date) {
		this.author = author;
		this.resourceID= resourceID;
		this.date = date;

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

	public Long getResourceID() {
		return resourceID;
	}

	public void setResourceID(Long resourceID) {
		this.resourceID = resourceID;
	}

	public String getDate() {
		return date;
	}

	public void  setDate (String date) {
		this.date = date;
	}
	

} 
