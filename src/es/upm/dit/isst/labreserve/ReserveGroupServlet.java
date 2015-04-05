package es.upm.dit.isst.labreserve;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import es.upm.dit.isst.labreserve.dao.GroupDAO;
import es.upm.dit.isst.labreserve.dao.GroupDAOImpl;
import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Config;
import es.upm.dit.isst.labreserve.model.Group;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;

public class ReserveGroupServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		GroupDAO groupDAO = GroupDAOImpl.getInstance();		
		ConfigDAO configDAO = ConfigDAOImpl.getInstance();
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		            
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		}
		
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
	    Long id = Long.parseLong(req.getParameter("id"));
		Group group = groupDAO.getGroup(id);
		req.getSession().setAttribute("flashMessageError", null);
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("group", group);
		req.getSession().setAttribute("initTimes", initTimes);
		req.getSession().setAttribute("finalTimes", finalTimes);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		RequestDispatcher view = req.getRequestDispatcher("ReserveGroup.jsp");
        view.forward(req, resp);
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		GroupDAO dao = GroupDAOImpl.getInstance();
		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
	    Long groupID = Long.parseLong(req.getParameter("groupID"));
	    //String groupName = dao.getGroup(groupID).getName();

	    
		String date = checkNull(req.getParameter("initDate"));
		String initHour = checkNull(req.getParameter("initTime"));
		String finalHour = checkNull(req.getParameter("finalTime"));
		ReserveDAO reserveDao = ReserveDAOImpl.getInstance();
		ResourceDAO resourceDao = ResourceDAOImpl.getInstance();

		DateFormat dateFormat = new SimpleDateFormat ("hh:mm");	
		java.util.Date horaini, horafin;
		try{
			horaini = dateFormat.parse(initHour);
			horafin = dateFormat.parse(finalHour);
			
			if(horaini.compareTo(horafin) >= 0){
				req.getSession().setAttribute("flashMessageError", "Hora de sesión incorrecta");
				RequestDispatcher view = req.getRequestDispatcher("ReserveGroup.jsp");
			    view.forward(req, resp);
			    return;
			}
			}catch(Exception parseException){
				req.getSession().setAttribute("flashMessageError", "Hora de sesión incorrecta");
				RequestDispatcher view = req.getRequestDispatcher("ReserveGroup.jsp");
			    view.forward(req, resp);
			    return;
			}
		
		if (date == ""){
			req.getSession().setAttribute("flashMessageError", "Fecha incorrecta");
			RequestDispatcher view = req.getRequestDispatcher("ReserveGroup.jsp");
	        view.forward(req, resp);
	        return;
		}
		
		List<Long> resources = dao.getGroup(groupID).getResources();
		boolean reserved = true;
		for (Long id : resources ) {
			Resource res = resourceDao.getResource(id);
			if (reserveDao.isResourceReserved(id, date, initHour, finalHour) ){
				System.out.println("Resource busy");
				reserved = false;

			} else {
				reserveDao.add(user.getUserId(), res.getName(), id, date, initHour, finalHour );
			}
			
		}
		if (reserved) {
			req.getSession().setAttribute("flashMessageSuccess", "Â¡Grupo de recursos reservados!");
			resp.sendRedirect("/main");
		} else {
			req.getSession().setAttribute("flashMessageError", "Grupo de recursos ocupado");
			RequestDispatcher view = req.getRequestDispatcher("ReserveGroup.jsp");
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

