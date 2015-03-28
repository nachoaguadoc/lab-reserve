package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import javax.persistence.EntityManager;

import es.upm.dit.isst.labreserve.model.Config;
import es.upm.dit.isst.labreserve.model.Group;
import es.upm.dit.isst.labreserve.model.Resource;

public class ConfigDAOImpl implements ConfigDAO {
	private static ConfigDAOImpl instance;

	private ConfigDAOImpl() {
	}
	
	public static ConfigDAOImpl getInstance(){
		if (instance == null)
			instance = new ConfigDAOImpl();
		return instance;
	}
	@Override
	public List<Config> listConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(String type, String webmaster, String sessionTime) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Config config = new Config(type, webmaster, sessionTime);
			em.persist(config);
			em.close();
		}
	}
	@Override
	public void update (String type, String webmaster, String sessionTime) {
		EntityManager em = EMFService.get().createEntityManager();
		Config config = em.find(Config.class, type);
		config.setWebmaster(webmaster);
		config.setSessionTime(sessionTime);
		em.merge(config);
		em.close();
	}

	
	@Override
	public void updateWebmaster(String type, String webmaster) {
		EntityManager em = EMFService.get().createEntityManager();
		Config config = em.find(Config.class, type);
		config.setWebmaster(webmaster);
		em.merge(config);
		em.close();
	}

	@Override
	public void updateSessionTime(String type, String sessionTime) {
		EntityManager em = EMFService.get().createEntityManager();
		Config config = em.find(Config.class, type);
		config.setSessionTime(sessionTime);
		em.merge(config);
		em.close();
	}

	@Override
	public Config getConfig(String type) {
		EntityManager em = EMFService.get().createEntityManager();
		Config config = em.find(Config.class, type);
		return config;
	}

	@Override
	public void remove(String type) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Config config = em.find(Config.class, type);
			em.remove(config);
		} finally {
			em.close();
		}
	}

}
