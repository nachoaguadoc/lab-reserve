package es.upm.dit.isst.labreserve.dao;

import java.util.HashMap;
import java.util.List;

import es.upm.dit.isst.labreserve.model.Resource;

public interface ResourceDAO {

	public List<Resource> listResources();
	
	public void add (String name, String state, String description);
	
	public void update (Long id, String name, String state, String description);
	
	public Resource getResource(Long userId);

	public void remove (Long id);
			
	public void removeAll ();

}
