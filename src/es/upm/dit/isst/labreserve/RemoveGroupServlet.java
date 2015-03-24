package es.upm.dit.isst.labreserve;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.isst.labreserve.dao.GroupDAO;
import es.upm.dit.isst.labreserve.dao.GroupDAOImpl;

public class RemoveGroupServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;

  public void doGet(HttpServletRequest req, HttpServletResponse resp)
  throws IOException {
		System.out.println("Removing resource ");

    String id = req.getParameter("id");
    GroupDAO dao = GroupDAOImpl.getInstance();
    dao.remove(Long.parseLong(id));
    resp.sendRedirect("/main");
  }
} 
