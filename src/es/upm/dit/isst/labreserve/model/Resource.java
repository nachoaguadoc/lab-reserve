package es.upm.dit.isst.labreserve.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Resource implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String finalDate;
	private String state;
	boolean reserved;
	String reservedBy;


	public Resource(String name, String state) {
		this.name = name;
		this.state = state;
//		finalDate = null;
		reserved = false;
		reservedBy = null;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}

	public String getState() {
		return state;
	}

	public void  setState (String state) {
		this.state = state;
	}



	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

} 
