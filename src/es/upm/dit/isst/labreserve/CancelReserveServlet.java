package es.upm.dit.isst.labreserve;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;

public class CancelReserveServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;

  public void doPost(HttpServletRequest req, HttpServletResponse resp)
  throws IOException {
		System.out.println("Canceling reserve ");

    String id = req.getParameter("id");
    ReserveDAO dao = ReserveDAOImpl.getInstance();
    dao.remove(Long.parseLong(id));
	req.getSession().setAttribute("flashMessageSuccess", "¡Reserva cancelada!");
    resp.sendRedirect("/main");
  }
} 
