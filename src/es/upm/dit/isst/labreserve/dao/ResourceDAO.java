package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import es.upm.dit.isst.labreserve.model.Resource;

public interface ResourceDAO {

	public List<Resource> listResources();
	
	public void add (String name, String state, String description);
	
	public void update (long id, String name, String state, String description);
	
	public Resource getResource(long userId);

	public void remove (long id);
	
	public List<String> getUsers();
	
//	public void reserve (long id);
	
}
