package es.upm.dit.isst.labreserve.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;

import javax.mail.Session;

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
	public void add(String author, String resourceName, Long resourceID, String date, String initHour, String finalHour, AppUser user) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Reserve isOverReserving = isOverReserving(resourceID, date, initHour, finalHour, user.getPriority());
			if (isOverReserving == null) {
				Reserve reserve = new Reserve(author, user.getEmail(), resourceName, resourceID, date, initHour, finalHour, user.getPriority());
				em.persist(reserve);
				em.close();
			} else {
				Long id = isOverReserving.getId();
				String body = "Estimado usuario:" + "\n\n" + "Su reserva con fecha " + date + " de " + initHour + " a " + finalHour + " del recurso '" + resourceName + "' ha sido cancelada." +  "\n\n" + "Si usted no la ha cancelado, por favor, realice una nueva reserva." + "\n\n" + "Disculpe las molestias." + "\n\n" +  "LabReserve Team";
				remove(id, isOverReserving.getEmail(), body, "usuario");
				Reserve reserve = new Reserve(author, user.getEmail(), resourceName, resourceID, date, initHour, finalHour, user.getPriority());
				em.persist(reserve);
				em.close();
			}
			
		}

	}

	@Override
	public void update(Long id, String resourceName, Long resourceID, String date, String initHour, String finalHour, AppUser user) {
		EntityManager em = EMFService.get().createEntityManager();
		Reserve reserve = em.find(Reserve.class, id);
		Reserve isOverReserving = isOverReserving(resourceID, date, initHour, finalHour, user.getPriority());
		if (isOverReserving == null) {
			reserve.setResourceID(resourceID);
			reserve.setDate(date);
			reserve.setInitHour(initHour);
			reserve.setFinalHour(finalHour);
			em.merge(reserve);
			em.persist(reserve);
			em.close();
		} else {
			Long oldResId = isOverReserving.getId();
			String body = "Estimado " + user.getName() + ":" + "\n\n" + "Su reserva con fecha " + date + " de " + initHour + " a " + finalHour + " del recurso '" + resourceName + "' ha sido cancelada." +  "\n\n" + "Si usted no la ha cancelado, por favor, realice una nueva reserva." + "\n\n" + "Disculpe las molestias." + "\n\n" +  "LabReserve Team";
			remove(oldResId, user.getEmail(), body, user.getName());
			reserve.setResourceID(resourceID);
			reserve.setDate(date);
			reserve.setInitHour(initHour);
			reserve.setFinalHour(finalHour);
			em.merge(reserve);
			em.persist(reserve);
			em.close();
		}
		


	}

	@Override
	public Reserve getReserve(Long id) {
		EntityManager em = EMFService.get().createEntityManager();
		Reserve reserve = em.find(Reserve.class, id);
		return reserve;
		
	}

	@Override
	public void remove(Long id, String to, String body, String name) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Reserve reserve = em.find(Reserve.class, id);
			em.remove(reserve);
		} finally {
			em.close();
		}
		sendEmail(to, body, name);
	}
	
	@Override
	public void removeAll() {
		EntityManager em = EMFService.get().createEntityManager();
		em.createQuery("DELETE FROM " + Reserve.class.getName() + " r").executeUpdate(); 	    
	    em.close();
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
	
	public boolean isResourceReserved(Long resourceID, String date, String initHour, String finalHour, int priority){
		
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select r from Reserve r where r.resourceID = :resourceID");
		q.setParameter("resourceID", resourceID);
		List<Reserve> reserves = q.getResultList();
		for (Reserve r : reserves) {
			int resPriority = r.getPriority();
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
			if (reservedTime && reservedDate) {
				if (resPriority >= priority) return true;
				else return false;
			}
		}
		return false;
	
	}
	
	
	
public Reserve isOverReserving(Long resourceID, String date, String initHour, String finalHour, int priority){
		
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select r from Reserve r where r.resourceID = :resourceID");
		q.setParameter("resourceID", resourceID);
		List<Reserve> reserves = q.getResultList();
		for (Reserve r : reserves) {
			int resPriority = r.getPriority();
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
			if (reservedTime && reservedDate) {
				if (resPriority >= priority) return null;
				else return r;
			}
		}
		return null;
	
	}
	@Override
	public HashMap<String, Boolean> getConsultByDay(Long id, String date, int priority){
		HashMap<String, Boolean> consult = new HashMap<String, Boolean>();
		List<String> hours = Arrays.asList("00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00");
		for (int i = 0; i<hours.size()-1; i++){
			boolean res = isResourceReserved(id, date, hours.get(i), hours.get(i+1), priority);
			consult.put(hours.get(i), res);
		}
		return consult;
	}
	
	@Override
	public HashMap<String, Boolean> getConsultByWeek(Long id, List<String> days, List<String> hours, int priority){
		HashMap<String, Boolean> consult = new HashMap<String, Boolean>();
		for (int j=0; j<days.size(); j++){
			Boolean isBusy = false;
			
			if (isResourceReserved(id, days.get(j), hours.get(0), hours.get(1), priority)){
				isBusy = true;
			}
			
			consult.put(days.get(j), isBusy);
		}
		return consult;
	}
	
	@Override
	public HashMap<String, Boolean> getConsultByMonth(Long id, List<String> days, List<String> hours, int priority){
		HashMap<String, Boolean> consult = new HashMap<String, Boolean>();
		for (int j=0; j<days.size()-1; j++){
			Boolean isBusy = false;
			for (int i = 0; i<hours.size()-1; i++){
				if (isResourceReserved(id, days.get(j), hours.get(i), hours.get(i+1), priority)){
					isBusy = true;
				}
			}
			consult.put(days.get(j), isBusy);
		}
		return consult;
	}
	
	public void sendEmail(String to, String body, String name) {
		if (name == null || name.equals("")){
			name = "usuario";
		}
		  Properties props = new Properties();
		  Session session = Session.getDefaultInstance(props, null);
		try {
		  Message msg = new MimeMessage(session);
	      try {
			msg.setFrom(new InternetAddress("labreserve2015@gmail.com", "LabReserve Notification"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
			msg.addRecipient(Message.RecipientType.TO,
			   new InternetAddress(to, name));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      msg.setSubject("Reserva cancelada");
	      msg.setText(body);
	      Transport.send(msg);
		  } catch (AddressException e) {
		      // ...
		  } catch (MessagingException e) {
		      // ...
		  }	}


}
