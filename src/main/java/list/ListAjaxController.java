package list;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet({"/getTH","/getTHforT","/getTA"})
public class ListAjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ListAjaxController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doAjax(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		this.doAjax(request, response);
	}
	private void doAjax(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String ajaxData = null;
		String jobCode = req.getRequestURI().substring(req.getContextPath().length()+1);

		HttpSession session = req.getSession();
		ListManagement lm = null;
		

		if(session.getAttribute("emCode")!= null || session.getAttribute("stCode")!= null) {
			
			if(jobCode.equals("getTH")) {
				lm = new ListManagement(req);
				ajaxData = lm.backController("8");
			}else if(jobCode.equals("getTHforT")) {
				lm = new ListManagement(req);
				ajaxData = lm.backController("9");
			}else if(jobCode.equals("getTA")) {
				lm = new ListManagement(req);
				ajaxData = lm.backController("10");
			}
		}
			res.setContentType("text/html; charset=utf-8");
			PrintWriter p = res.getWriter();
			p.write(ajaxData);
		}
	
}
