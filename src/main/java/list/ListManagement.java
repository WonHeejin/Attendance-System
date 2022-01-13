package list;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.ActionBean;
import beans.EmployeeBean;
import controller.DataAccessObject;

public class ListManagement {
	private HttpServletRequest req;
	private HttpSession session;
	private EmployeeBean emp;
	
	public ListManagement(HttpServletRequest req) {
		this.req = req;
	}
	
	public ActionBean backController(int jobCode) {
		ActionBean action = null;
		
		switch(jobCode){
		case 1:
			action = this.studentList();
			break;
			
			default:
		}
		
		return action;
	}
	
	private ActionBean studentList() {
		ActionBean action = new ActionBean();
		DataAccessObject dao = new DataAccessObject();
		ArrayList<EmployeeBean> list = null;
		emp =new EmployeeBean();
		Connection conn = dao.getConnection();
		this.emp.setEmCode(this.req.getParameter("emCode"));
		
		session = this.req.getSession();
		session.setAttribute("emCode", emp.getEmCode());
		if((list = dao.getLogInfo(conn, emp)) != null ) {
			req.setAttribute("accessInfo", list);
			
		}
		
		action.setPage("statesForManager.jsp");
		action.setRedirect(false);
		dao.clossConnection(conn);
		
		return action;
	}
}
