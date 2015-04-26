package es.upm.dit.isst.labreserve.dao;

import java.util.HashMap;
import java.util.List;

import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Reserve;

import javax.mail.Session;

public interface ReserveDAO {

	public List<Reserve> listReserves();
	
	public void add (String author, String resourceName, Long resourceID, String date, String initHour, String finalHour, AppUser user);
	
	public void update (Long id, String resourceName, Long resourceID, String date, String initHour, String finalHour, AppUser user);
	
	public Reserve getReserve(Long id);

	public void remove (Long id, String to, String body, String name);
	
	public void removeAll ();

	public List<Reserve> listAuthorReserves(String author);
	
	public List<Reserve> listResourceReserves(Long resourceID);
	
	public List<Reserve> listResourceAuthorReserves(Long resourceID, String author);

	public boolean isResourceReserved(Long id, String date, String initHour, String finalHour, int priority);
	
	public HashMap<String, Boolean> getConsultByDay(Long id, String date, int priority);
	
	public HashMap<String, Boolean> getConsultByWeek(Long id,List<String> days, List<String> hours, int priority);

	public HashMap<String, Boolean> getConsultByMonth(Long id, List<String> days, List<String> hours, int priority);

	public void sendEmail(String to, String body, String name);

}
