package es.upm.dit.isst.labreserve;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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

import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;
public class ConsultResourceServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;


	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO dao = ResourceDAOImpl.getInstance();
		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
	    Long resourceID = Long.parseLong(req.getParameter("resourceID"));

	    
		String date = checkNull(req.getParameter("consultDate"));

		ReserveDAO resDao = ReserveDAOImpl.getInstance();
		
		HashMap<String, Boolean> consult = new HashMap<String, Boolean>();
		consult = resDao.getConsult(resourceID, date);
		req.getSession().setAttribute("dateSelected", date);
		req.getSession().setAttribute("consult", consult);
		RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
        view.forward(req, resp);
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 

