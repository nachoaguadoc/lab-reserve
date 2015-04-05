package es.upm.dit.isst.labreserve;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.labreserve.dao.ConfigDAO;
import es.upm.dit.isst.labreserve.dao.ConfigDAOImpl;
import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Config;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;
public class ReserveResourceServlet extends HttpServlet {
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
		List<String> list = Arrays.asList("00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00");
		
		req.getSession().setAttribute("flashMessageError", null);
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("resource", resource);
		req.getSession().setAttribute("initTimes", initTimes);
		req.getSession().setAttribute("finalTimes", finalTimes);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		req.getSession().setAttribute("consult", null);
		req.getSession().setAttribute("list", list);
		req.getSession().setAttribute("dateSelected", null);


		RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
        view.forward(req, resp);
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO resourceDAO = ResourceDAOImpl.getInstance();
		ReserveDAO reserveDAO = ReserveDAOImpl.getInstance();

		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
	    Long resourceID = Long.parseLong(req.getParameter("resourceID"));
	    String resourceName = resourceDAO.getResource(resourceID).getName();

	    
		String date = checkNull(req.getParameter("initDate"));
//		String finalDate = checkNull(req.getParameter("finalDate"));
		String initHour = checkNull(req.getParameter("initTime"));
		String finalHour = checkNull(req.getParameter("finalTime"));
		
		DateFormat dateFormat = new SimpleDateFormat ("hh:mm");	
		java.util.Date horaini, horafin;
		try{
			horaini = dateFormat.parse(initHour);
			horafin = dateFormat.parse(finalHour);
			if(horaini.compareTo(horafin) >= 0){
				req.getSession().setAttribute("flashMessageError", "Hora de sesión incorrecta");
				RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
			    view.forward(req, resp);
			    return;
			}
			
		}catch(Exception parseException){
			req.getSession().setAttribute("flashMessageError", "Hora de sesión incorrecta");
			RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
		    view.forward(req, resp);
		    return;
		}
			
			if (date == ""){
				req.getSession().setAttribute("flashMessageError", "Fecha incorrecta");
				RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
		        view.forward(req, resp);
			}
			else if (reserveDAO.isResourceReserved(resourceID, date, initHour, finalHour) ){
				System.out.println("Resource busy");
				req.getSession().setAttribute("flashMessageError", "Recurso Ocupado");
				RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
		        view.forward(req, resp);
			} else {
				reserveDAO.add(user.getUserId(), resourceName, resourceID, date, initHour, finalHour );
				req.getSession().setAttribute("flashMessageSuccess", "Â¡Recurso reservado!");
				resp.sendRedirect("/main");
			}
		
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 

