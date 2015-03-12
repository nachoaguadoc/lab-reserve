package es.upm.dit.isst.labreserve;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Resource;

public class MainServlet extends HttpServlet {

	private static final Long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO dao = ResourceDAOImpl.getInstance();

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		List<Resource> resources = new ArrayList<Resource>();
		            
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		    resources = dao.listResources();
		}
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("resources", new ArrayList<Resource>(resources));
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		
		
		try {
			boolean admin = userService.isUserAdmin();
			if (admin) {
				RequestDispatcher view = req.getRequestDispatcher("ResourceAdminApplication.jsp");
		        view.forward(req, resp);
			}
			else {
				RequestDispatcher view = req.getRequestDispatcher("ResourceUserApplication.jsp");
		        view.forward(req, resp);
			}
		} catch (IllegalStateException e){
			System.out.println("User is not logged in");
			RequestDispatcher view = req.getRequestDispatcher("home.jsp");
	        view.forward(req, resp);
		}
		

		
	}

}
