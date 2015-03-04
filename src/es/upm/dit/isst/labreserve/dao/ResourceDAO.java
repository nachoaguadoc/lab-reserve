package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import es.upm.dit.isst.labreserve.model.Resource;

public interface ResourceDAO {

	public List<Resource> listResources();
	
	public void add (String name, String state);
	
	public List<Resource> getResources(String userId);
	
	public void remove (long id);
	
	public List<String> getUsers();
	
	
}
