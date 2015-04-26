package es.upm.dit.isst.labreserve;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.labreserve.dao.AppUserDAO;
import es.upm.dit.isst.labreserve.dao.AppUserDAOImpl;
import es.upm.dit.isst.labreserve.dao.ConfigDAO;
import es.upm.dit.isst.labreserve.dao.ConfigDAOImpl;
import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Config;
import es.upm.dit.isst.labreserve.model.Resource;

public class CheckResourceServlet extends HttpServlet {
	
	  private static final Long serialVersionUID = 1L;

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
	
		ResourceDAO resourceDAO = ResourceDAOImpl.getInstance();
		ConfigDAO configDAO = ConfigDAOImpl.getInstance();
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		            
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		}
		
	    Long id = Long.parseLong(req.getParameter("id"));
		Resource resource = resourceDAO.getResource(id);
		
		Config config = configDAO.getConfig("global");
		List<String> initTimes = new ArrayList<String>();
		List<String> finalTimes = new ArrayList<String>();
		List<String> list = new ArrayList<String>();

		for (int i = Integer.parseInt(config.getOpening()); i < Integer.parseInt(config.getClosing()); i++){
			String hour;
			if (i < 10) {  
				hour = "0" + Integer.toString(i) + ":00";
			} else {
				hour = Integer.toString(i) + ":00";
			}
			initTimes.add(hour);
		}
		for (int i = Integer.parseInt(config.getOpening())+1; i <= Integer.parseInt(config.getClosing()); i++){
			String hour;
			if (i < 10) {  
				hour = "0" + Integer.toString(i) + ":00";
			} else {
				hour = Integer.toString(i) + ":00";
			}
			finalTimes.add(hour);
		}
		
		for (int i = Integer.parseInt(config.getOpening()); i <= Integer.parseInt(config.getClosing()); i++){
			String hour;
			if (i < 10) {  
				hour = "0" + Integer.toString(i) + ":00";
			} else {
				hour = Integer.toString(i) + ":00";
			}
			list.add(hour);
		}
		
		
		req.getSession().setAttribute("flashMessageError", null);
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("resource", resource);
		req.getSession().setAttribute("initTimes", initTimes);
		req.getSession().setAttribute("finalTimes", finalTimes);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		req.getSession().setAttribute("consult", null);
		req.getSession().setAttribute("type", null);
		req.getSession().setAttribute("list", list);
		req.getSession().setAttribute("dateSelected", null);


		RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
        view.forward(req, resp);
		
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO dao = ResourceDAOImpl.getInstance();
		ReserveDAO resDao = ReserveDAOImpl.getInstance();
		AppUserDAO userDAO = AppUserDAOImpl.getInstance();
		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
	    Long resourceID = Long.parseLong(req.getParameter("resourceID"));

	    
		String type = req.getParameter("type");
		String month = req.getParameter("month");
		String year = req.getParameter("year");
		int priority = userDAO.getUser(user.getUserId()).getPriority();
		
		if (type.equals("day")){

			String date = checkNull(req.getParameter("consultDate"));

			HashMap<String, Boolean> consult = new HashMap<String, Boolean>();
			consult = resDao.getConsultByDay(resourceID, date, priority);
			req.getSession().setAttribute("dateSelected", date);
			req.getSession().setAttribute("type", "day");
			req.getSession().setAttribute("consult", consult);			
			req.getSession().setAttribute("resourceID", resourceID);
			RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
	        view.forward(req, resp);
	        
	        
		} else if (type.equals("month")){
			String first = checkNull(req.getParameter("initTimeMonth"));
			String last = checkNull(req.getParameter("finalTimeMonth"));
			HashMap<String, Boolean> consult = new HashMap<String, Boolean>();
			String date = "01/" + month + "/" + year;
			int initHour = Integer.parseInt(first.split(":")[0]);
			int finalHour = Integer.parseInt(last.split(":")[0]);
			List<String> hours = new ArrayList<String>();
			hours.add(first);
			hours.add(last);
			List<String> days = new ArrayList<String>();
			List<Date> dates = new ArrayList<Date>();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
			int startsWith = 0;
			try {
				Date dateDate = (Date) formatter.parse(date);
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(dateDate);
		        startsWith = cal.get(Calendar.DAY_OF_WEEK)-1;
		        if (startsWith == 0){
		        	startsWith = 7;
		        }
		        int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		        for (int i = 0; i< maxDays; i++){
			        Date day = cal.getTime();
			        days.add(formatter.format(day));
			        cal.add(Calendar.DATE, 1);
		        } 
			} catch (ParseException e) {
				e.printStackTrace();
			}

			
			consult = resDao.getConsultByWeek(resourceID, days, hours, priority);
			req.getSession().setAttribute("dateSelected", date);
			req.getSession().setAttribute("monthNumber", month);
			req.getSession().setAttribute("oldMonth", month);
			req.getSession().setAttribute("oldYear", year);
			req.getSession().setAttribute("oldInit", first);
			req.getSession().setAttribute("oldFinal", last);
			req.getSession().setAttribute("resourceID", resourceID);
			req.getSession().setAttribute("month", days);
			req.getSession().setAttribute("type", "month");
			req.getSession().setAttribute("startsWith", startsWith);
			req.getSession().setAttribute("consult", consult);
			RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
	        view.forward(req, resp);			
		}
		

	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}
