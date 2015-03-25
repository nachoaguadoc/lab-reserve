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

import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;

public class CheckReservesServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;
  int tries = 2;
  String lastValue = "";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ReserveDAO dao = ReserveDAOImpl.getInstance();
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		            
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		}

		List<Reserve> reserves = dao.listAuthorReserves(user.getUserId());
		req.getSession().setAttribute("reserves", new ArrayList<Reserve>(reserves));
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		
		if (req.getSession().getAttribute("flashMessageSuccess") != null){
			tries --;
			if (tries == 0){
				if (lastValue == req.getSession().getAttribute("flashMessageSuccess").toString()) {
					lastValue = req.getSession().getAttribute("flashMessageSuccess").toString();
					req.getSession().setAttribute("flashMessageSuccess", null);
				} else {
					lastValue = req.getSession().getAttribute("flashMessageSuccess").toString();
				}
				
				tries = 2;
			}


		}
		
		RequestDispatcher view = req.getRequestDispatcher("CheckReserves.jsp");
        view.forward(req, resp);
		
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 

