package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import javax.persistence.EntityManager;

import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Config;

public class AppUserDAOImpl implements AppUserDAO {
	
	private static AppUserDAOImpl instance;

	private AppUserDAOImpl() {
	}
	
	public static AppUserDAOImpl getInstance(){
		if (instance == null)
			instance = new AppUserDAOImpl();
		return instance;
	}
	
	@Override
	public List<AppUser> listUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(String id, String email, int priority) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			AppUser user = new AppUser(id, email, priority);
			em.persist(user);
			em.close();
		}
	}

	@Override
	public void update(String id, int priority) {
		EntityManager em = EMFService.get().createEntityManager();
		AppUser user = em.find(AppUser.class, id);
		user.setPriority(priority);
		em.merge(user);
		em.close();
	}

	@Override
	public AppUser getUser(String id) {
		EntityManager em = EMFService.get().createEntityManager();
		AppUser user = em.find(AppUser.class, id);
		return user;
	}

	@Override
	public void remove(String id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			AppUser user = em.find(AppUser.class, id);
			em.remove(user);
		} finally {
			em.close();
		}
	}
}
