package es.upm.dit.isst.labreserve.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppUser implements Serializable{

	private static final Long serialVersionUID = 1L;
	@Id
	private String id;
	private String email;
	private int priority;	
	private String name;
	
	public AppUser(String id, String email, int priority, String name) {
		this.id = id;
		this.email = email;
		this.priority = priority;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void  setEmail (String email) {
		this.email = email;
	}
	
	public int getPriority() {
		return priority;
	}

	public void  setPriority (int priority) {
		this.priority = priority;
	}
	
	public String getName() {
		return name;
	}

	public void  setName (String name) {
		this.name = name;
	}

} 
