package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;

public class ResourceDAOImpl implements ResourceDAO {

	
	private static ResourceDAOImpl instance;

	private ResourceDAOImpl() {
	}
	
	public static ResourceDAOImpl getInstance(){
		if (instance == null)
			instance = new ResourceDAOImpl();
		return instance;
	}
	@Override
	public List<Resource> listResources() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Resource m");
		List<Resource> resources = q.getResultList();
		return resources;
	}

	@Override
	public void add(String name, String state, String description) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Resource resource = new Resource(name, state, description);
			em.persist(resource);
			em.close();
		}
	}
	
	@Override
	public void update(Long id, String name, String state, String description) {
		EntityManager em = EMFService.get().createEntityManager();
		Resource resource = em.find(Resource.class, id);
		resource.setName(name);
		resource.setState(state);
		resource.setDescription(description);
		em.merge(resource);
		em.close();
	}

	@Override
	public Resource getResource(Long id) {
		EntityManager em = EMFService.get().createEntityManager();
		Resource resource = em.find(Resource.class, id);
		return resource;
	}
	

	@Override
	public void remove(Long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Resource resource = em.find(Resource.class, id);
			em.remove(resource);
		} finally {
			em.close();
		}
	}
	@Override
	public void removeAll() {
		EntityManager em = EMFService.get().createEntityManager();
		em.createQuery("DELETE FROM " + Resource.class.getName() + " r").executeUpdate(); 	    
	    em.close();
	}

	
}

