package management;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ActionBean;
import list.ListManagement;


@WebServlet({"/SSI","/SLM"})
public class Management extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Management() {
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
		InfoManagement log;
		String jobCode = req.getRequestURI().substring(req.getContextPath().length()+1);

		HttpSession session = req.getSession();




		if(session.getAttribute("emCode")!= null) {
			if(jobCode.equals("SSI")) {
				log = new InfoManagement(req);
				action = log.backController(1);
			}else if(jobCode.equals("SLM")) {
				log = new InfoManagement(req);
				action = log.backController(2);
			}else {}
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
