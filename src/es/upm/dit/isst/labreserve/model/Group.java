package es.upm.dit.isst.labreserve.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Group implements Serializable {


	private static final Long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description; 
	private List<Long> resources;


	public Group(String name, List<Long> resources, String description) {
		this.name = name;
		this.resources = resources;
		this.description = description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Long> getResources() {
		return resources;
	}

	public void  setResources (List<Long> resources) {
		this.resources = resources;
	}

} 
