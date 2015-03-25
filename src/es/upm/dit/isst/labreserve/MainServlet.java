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

import es.upm.dit.isst.labreserve.dao.GroupDAO;
import es.upm.dit.isst.labreserve.dao.GroupDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Group;
import es.upm.dit.isst.labreserve.model.Resource;

public class MainServlet extends HttpServlet {

	private static final Long serialVersionUID = 1L;
	int tries = 2;
	String lastValue = "aaaaaa";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO dao = ResourceDAOImpl.getInstance();
		GroupDAO groupDAO = GroupDAOImpl.getInstance();
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
		List<Group> groups = groupDAO.listGroups();
		req.getSession().setAttribute("groups", new ArrayList<Group>(groups));
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("resources", new ArrayList<Resource>(resources));
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
