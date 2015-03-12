package es.upm.dit.isst.labreserve.dao;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;

public class ReserveDAOImpl implements ReserveDAO {
	
	private static ReserveDAOImpl instance;

	private ReserveDAOImpl() {
	}
	
	public static ReserveDAOImpl getInstance(){
		if (instance == null)
			instance = new ReserveDAOImpl();
		return instance;
	}
	
	@Override
	public List<Reserve> listReserves() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Reserve m");
		List<Reserve> reserves = q.getResultList();
		return reserves;
	}

	@Override
	public void add(String author, String resourceName, Long resourceID, String date, String initHour, String finalHour) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Reserve reserve = new Reserve(author, resourceName, resourceID, date, initHour, finalHour);
			em.persist(reserve);
			em.close();
		}

	}

	@Override
	public void update(Long id, String resourceName, Long resourceID, String date, String initHour, String finalHour) {
		EntityManager em = EMFService.get().createEntityManager();
		Reserve reserve = em.find(Reserve.class, id);
		reserve.setResourceID(resourceID);
		reserve.setDate(date);
		reserve.setInitHour(initHour);
		reserve.setFinalHour(finalHour);

		em.merge(reserve);
		em.close();

	}

	@Override
	public Reserve getReserve(Long id) {
		EntityManager em = EMFService.get().createEntityManager();
		Reserve reserve = em.find(Reserve.class, id);
		return reserve;
		
	}

	@Override
	public void remove(Long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Reserve reserve = em.find(Reserve.class, id);
			em.remove(reserve);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Reserve> listAuthorReserves(String author) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select r from Reserve r where r.author = :author");
		q.setParameter("author", author);
		List<Reserve> reserves = q.getResultList();
		return reserves;
	}

	public List<Reserve> listResourceReserves(Long resourceID){
		
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select r from Reserve r where r.resourceID = :resourceID");
		q.setParameter("resourceID", resourceID);
		List<Reserve> reserves = q.getResultList();
		return reserves;
	}
	public List<Reserve> listResourceAuthorReserves(Long resourceID, String author){
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select r from Reserve r where r.resourceID = :resourceID and r.author = :author");
		q.setParameter("resourceID", resourceID);
		q.setParameter("author", author);

		List<Reserve> reserves = q.getResultList();
		return reserves;
	}
	
	public boolean isResourceReserved(Long resourceID, String date, String initHour, String finalHour){
		
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select r from Reserve r where r.resourceID = :resourceID");
		q.setParameter("resourceID", resourceID);
		List<Reserve> reserves = q.getResultList();
		for (Reserve r : reserves) {
			String stringDate = r.getDate();
			String initString = r.getInitHour();
			String finalString = r.getFinalHour();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			boolean reservedDate = true;
			boolean reservedTime = true;
			try {
				
				reservedDate = sdf.parse(stringDate).equals(sdf.parse(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
			try {
				reservedTime = (sd.parse(finalHour).after(sd.parse(initString)) && sd.parse(finalHour).before(sd.parse(finalString))) || 
						(sd.parse(initHour).before(sd.parse(finalString)) &&  !sd.parse(initHour).before(sd.parse(initString)));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (reservedTime && reservedDate) return true;
		}
		return false;
	


	}

}
