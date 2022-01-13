package management;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




@WebServlet("/maxCode")
public class AjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AjaxController() {
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
		InfoManagement ig = null;
		

		if(session.getAttribute("emCode") != null) {
			if(jobCode.equals("maxCode")) {
				ig = new InfoManagement(req);
				ajaxData = ig.backController("2");
			}else {

			}
			res.setContentType("text/html; charset=utf-8");
			PrintWriter p = res.getWriter();
			p.write(ajaxData);
		}
	}
}
