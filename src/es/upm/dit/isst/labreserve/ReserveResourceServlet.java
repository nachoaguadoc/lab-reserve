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
import java.util.Properties;

import javax.mail.Session;
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
import es.upm.dit.isst.labreserve.dao.MovimientoDAO;
import es.upm.dit.isst.labreserve.dao.MovimientoDAOImpl;
import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Config;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;
import es.upm.dit.isst.labreserve.model.Movimiento;
public class ReserveResourceServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;

	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		ResourceDAO resourceDAO = ResourceDAOImpl.getInstance();
		ReserveDAO reserveDAO = ReserveDAOImpl.getInstance();
		MovimientoDAO movimientoDAO = MovimientoDAOImpl.getInstance();
		AppUserDAO userDAO = AppUserDAOImpl.getInstance();
		
		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
	    Long resourceID = Long.parseLong(req.getParameter("resourceID"));
	    String resourceName = resourceDAO.getResource(resourceID).getName();
	    
	    AppUser appUser = userDAO.getUser(user.getUserId());
	    int priority = appUser.getPriority();
	    
	    String type = req.getParameter("resType");
	    if (type.equals("month")){
			String initHour = checkNull(req.getParameter("startingHour"));
			String finalHour = checkNull(req.getParameter("finishingHour"));
			String month = checkNull(req.getParameter("resMonth"));
			String year = checkNull(req.getParameter("resYear"));
			String[] days = req.getParameterValues("resDays");
			for (int i=0; i<days.length; i++){
				reserveDAO.add(user.getUserId(), resourceName, resourceID, days[i], initHour, finalHour, appUser);
				movimientoDAO.add(resourceID, resourceName, days[i], 1);
			}
			resp.sendRedirect("/main");

	    } else {
	    	String date = checkNull(req.getParameter("resDate"));
			String[] hours = req.getParameterValues("resHours");
			for (String hour: hours){
				String initHour = hour;
				int lastHour = (Integer.parseInt(initHour.split(":")[0]) + 1);
				String finalHour = "";
				if (lastHour < 10 ){
					finalHour ="0" + Integer.toString(lastHour) + ":00";
				} else {
					finalHour = Integer.toString(lastHour) + ":00";
				}

				reserveDAO.add(user.getUserId(), resourceName, resourceID, date, hour, finalHour, appUser);
				movimientoDAO.add(resourceID, resourceName, date, 1);
			}
			resp.sendRedirect("/main");

			/**
			DateFormat dateFormat = new SimpleDateFormat ("hh:mm");	
			java.util.Date horaini, horafin;
			
			if (date == ""){
				req.getSession().setAttribute("flashMessageError", "Fecha incorrecta");
				RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
		        view.forward(req, resp);
		        return;
			}
			
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
			
			if (reserveDAO.isResourceReserved(resourceID, date, initHour, finalHour) ){
				System.out.println("Resource busy");
				req.getSession().setAttribute("flashMessageError", "Recurso Ocupado");
				RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
		        view.forward(req, resp);
			} else {
				reserveDAO.add(user.getUserId(), resourceName, resourceID, date, initHour, finalHour );
				req.getSession().setAttribute("flashMessageSuccess", "¡Recurso reservado!");
				resp.sendRedirect("/main");
			}
			**/
	    }
	    
		
		
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 

