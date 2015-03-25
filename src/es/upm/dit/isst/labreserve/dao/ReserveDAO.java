package es.upm.dit.isst.labreserve.dao;

import java.util.HashMap;
import java.util.List;

import es.upm.dit.isst.labreserve.model.Reserve;

public interface ReserveDAO {

	public List<Reserve> listReserves();
	
	public void add (String author, String resourceName, Long resourceID, String date, String initHour, String finalHour);
	
	public void update (Long id, String resourceName, Long resourceID, String date, String initHour, String finalHour);
	
	public Reserve getReserve(Long id);

	public void remove (Long id);
		
	public List<Reserve> listAuthorReserves(String author);
	
	public List<Reserve> listResourceReserves(Long resourceID);
	
	public List<Reserve> listResourceAuthorReserves(Long resourceID, String author);

	public boolean isResourceReserved(Long id, String date, String initHour, String finalHour);
	
	public HashMap<String, Boolean> getConsult(Long id, String date);

}
