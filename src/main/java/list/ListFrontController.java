package list;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ActionBean;


@WebServlet("/SFM")
public class ListFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public ListFrontController() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


		ActionBean action = new ActionBean();
		ListManagement log;
		String jobCode = req.getRequestURI().substring(req.getContextPath().length()+1);

		HttpSession session = req.getSession();




		if(session.getAttribute("emCode")!= null) {
			if(jobCode.equals("SFM")) {
				log = new ListManagement(req);
				action = log.backController(1);
			}
		}else {

		}


		if(action.isRedirect()) {
			res.sendRedirect(action.getPage());
		}else {
			RequestDispatcher dp = req.getRequestDispatcher(action.getPage());
			dp.forward(req, res);
		}
	}


}
