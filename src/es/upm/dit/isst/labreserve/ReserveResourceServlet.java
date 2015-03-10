package es.upm.dit.isst.labreserve;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;

public class ReserveResourceServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doGet(HttpServletRequest req, HttpServletResponse resp)
  throws IOException {
		System.out.println("Reserving resource ");

    String id = req.getParameter("id");
    ResourceDAO dao = ResourceDAOImpl.getInstance();
    dao.reserve(Long.parseLong(id));
    resp.sendRedirect("/main");
  }
} 
