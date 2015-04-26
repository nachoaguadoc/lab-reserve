package es.upm.dit.isst.labreserve;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import es.upm.dit.isst.labreserve.dao.AppUserDAO;
import es.upm.dit.isst.labreserve.dao.AppUserDAOImpl;
import es.upm.dit.isst.labreserve.dao.ConfigDAO;
import es.upm.dit.isst.labreserve.dao.ConfigDAOImpl;
import es.upm.dit.isst.labreserve.dao.RequestDAO;
import es.upm.dit.isst.labreserve.dao.RequestDAOImpl;
import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.dao.MovimientoDAO;
import es.upm.dit.isst.labreserve.dao.MovimientoDAOImpl;
import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Request;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;
import es.upm.dit.isst.labreserve.model.Movimiento;
public class SignUpServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
	
		AppUserDAO dao = AppUserDAOImpl.getInstance();
		RequestDAO requestDAO = RequestDAOImpl.getInstance();
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		AppUser appUser= null;
		Request request = null;
		String oldName = "";
		int oldPriority = 0;
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		    appUser = dao.getUser(user.getUserId());
		    request = requestDAO.getRequest(user.getUserId());
		    if (appUser != null){
			    oldName = appUser.getName();
			    oldPriority = appUser.getPriority();
		    } else {
			    oldName = "";
			    oldPriority = 0;
		    }

		}
		
		
		if (request != null) {
			req.getSession().setAttribute("requestState", "Su peticiÃ³n se estÃ¡ procesando");
		} else  {
			req.getSession().setAttribute("requestState", null);
		}
		req.getSession().setAttribute("oldName", oldName);
		req.getSession().setAttribute("oldPriority", oldPriority);
		req.getSession().setAttribute("flashMessageError", null);
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("appUser", appUser);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		req.getSession().setAttribute("dateSelected", null);


		RequestDispatcher view = req.getRequestDispatcher("NewUser.jsp");
        view.forward(req, resp);
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		AppUserDAO dao = AppUserDAOImpl.getInstance();
		RequestDAO reqDAO = RequestDAOImpl.getInstance();
		MovimientoDAO movimientoDAO = MovimientoDAOImpl.getInstance();
		UserService userService = UserServiceFactory.getUserService();
		Date datehoy = Calendar.getInstance().getTime();
		System.out.println("la fecha de hoy es:"+ datehoy);
		SimpleDateFormat paramov = new SimpleDateFormat("dd/MM/yyyy");
		String fechahoy = paramov.format(datehoy);
		String ndia, nmes, naño;
		String[] trozos=fechahoy.split("/");
		if(trozos[0].length()==2 && trozos[0].startsWith("0")){
				ndia=(trozos[0].substring(1));
		}else{
			ndia=trozos[0];
		}
		if(trozos[1].length()==2 && trozos[1].startsWith("0")){
				nmes=(trozos[1].substring(1));
		}else{
			nmes=trozos[1];
		}
		if(trozos[2].length()==2){
			naño=trozos[2];
		}else{
			naño=(trozos[2].substring(2));
		}
		fechahoy=(ndia+"/"+nmes+"/"+naño);

		User user = (User) req.getAttribute("user");
		if (user == null) {
			user = userService.getCurrentUser();
		}
		String userId = req.getParameter("userId");
		Long longID;
		if(userId.length()>18){
			longID = Long.parseLong(userId.substring(0, 18));
		}else{
			longID = Long.parseLong(userId);
		}
		int oldPriority;
		if (dao.getUser(userId) != null) {
			oldPriority = dao.getUser(userId).getPriority();
		} else {
			oldPriority = 1;
		}
		int priority = Integer.parseInt(req.getParameter("priority"));
		String email = user.getEmail();
		String name = checkNull(req.getParameter("name"));
		String desc = checkNull(req.getParameter("description"));
		Date date = new Date();
		
		if (userService.isUserAdmin()){
			if (dao.getUser(userId) != null ){
				dao.update(userId, priority, name);
			} else {
				dao.add(userId, user.getEmail(), priority, name);
				movimientoDAO.add(longID, user.getEmail(), fechahoy, 3);
				
			}
			req.getSession().setAttribute("flashMessageSuccess", "Usuario actualizado");
			resp.sendRedirect("/main");

		}
		else {
			if (priority != 1 && priority != oldPriority){
				
				if (reqDAO.getRequest(userId) != null) {
					reqDAO.update(userId, email, priority, desc, date);
				} else {
					reqDAO.add(userId, email, priority, desc, date);
				}
				
				if (dao.getUser(userId) != null ){
					dao.update(userId, oldPriority, name);
				} else {
					//Se le pone prioridad 1 hasta que el administrador acepte su peticiÃ³n
					dao.add(userId, user.getEmail(), 1, name);
					dao.add(userId, user.getEmail(), priority, name);
					movimientoDAO.add(longID, user.getEmail(), fechahoy, 3);
				}
				req.getSession().setAttribute("flashMessageSuccess", "PeticiÃ³n enviada al administrador");
	
			} else {
				if (dao.getUser(userId) != null ){
					dao.update(userId, priority, name);
				} else {
					dao.add(userId, user.getEmail(), priority, name);
					movimientoDAO.add(longID, user.getEmail(), fechahoy, 3);
				}
				req.getSession().setAttribute("flashMessageSuccess", "Usuario actualizado");
	
			}
		}
		
		resp.sendRedirect("/main");
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 

