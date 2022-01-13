package management;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.ActionBean;
import beans.EmployeeBean;

public class InfoManagement {
	private HttpServletRequest req;
	private HttpSession session;
	private EmployeeBean emp;
	
	public InfoManagement(HttpServletRequest req) {
		this.req = req;
	}
	
	public ActionBean backController(int jobCode) {
		ActionBean action = null;

		switch(jobCode) {
		case 1:
			action = this.insForm();
			break;
			
		default:
		}

		return action;
	}
	
	public String backController(String jobCode) {
		String data = null;
		
		switch(jobCode) {
		case "2":
			data = getMaxCode();
			break;
		}
		
		return data;
	}
	
	private ActionBean insForm() {
		ActionBean action = new ActionBean();
		ArrayList<EmployeeBean> list = null;
		EmployeeBean emp = new EmployeeBean();
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		session = this.req.getSession();
		emp.setEmCode((String)session.getAttribute("emCode"));


		if((list = dao.getLogInfo(conn, emp)) != null) {
			req.setAttribute("accessInfo", list);
			action.setRedirect(false);
			action.setPage("joinMember.jsp");
		}
		return action;
	}
	
	private String getMaxCode() {
		String data = null;
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection(); 
		data = dao.getMaxCode(conn, req.getParameter("code"));
		dao.clossConnection(conn);
		
		return data;
	}
}
