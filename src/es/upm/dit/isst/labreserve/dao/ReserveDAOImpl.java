package es.upm.dit.isst.labreserve.dao;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
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
	
	@Override
	public HashMap<String, Boolean> getConsult(Long id, String date){
		HashMap<String, Boolean> consult = new HashMap<String, Boolean>();
		List<String> hours = Arrays.asList("01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00");
		for (int i = 0; i<hours.size()-1; i++){
			boolean res = isResourceReserved(id, date, hours.get(i), hours.get(i+1));
			consult.put(hours.get(i), res);
		}
		return consult;
	}

}
