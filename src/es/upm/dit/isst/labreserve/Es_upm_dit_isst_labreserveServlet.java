package es.upm.dit.isst.labreserve;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Es_upm_dit_isst_labreserveServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
