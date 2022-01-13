package login;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import beans.ActionBean;
import beans.EmployeeBean;



public class LoginManagement {
	private HttpServletRequest req;
	private HttpSession session;
	private EmployeeBean emp;

	public LoginManagement(HttpServletRequest req) {
		this.req = req;
	}

	public ActionBean backController(int jobCode) {
		ActionBean action = null;

		switch(jobCode) {
		case 1:
			action = this.afterAccess();
			break;
		case 2:
			action = this.accessCtl();
			break;
		case 3:
			action = this.accessOutCtl();
			break;

		default:
		}

		return action;
	}

	private ActionBean afterAccess() {
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
			action.setPage("administrator.jsp");
		}
		return action;
	}

	private ActionBean accessCtl() {
		ActionBean action = new ActionBean();
		ArrayList<EmployeeBean> list = null;
		DataAccessObject dao = null;
		boolean tran = false;
		this.emp = new EmployeeBean();
		this.emp.setEmCode(this.req.getParameter("emCode"));
		this.emp.setEmPassword(this.req.getParameter("emPassword"));
		this.emp.setAccessType("1001");

		dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		dao.modifyTranStatus(conn, false);

		if(dao.isEmployee(conn, emp)) {
			if(dao.insAccessHistory(conn, emp)) {
				tran = true;
				session = this.req.getSession();
				session.setAttribute("emCode", emp.getEmCode());

				if((list = dao.getLogInfo(conn, emp))!= null) {	
					req.setAttribute("accessInfo", list);
				}
			}
		}
		action.setPage(tran?"administrator.jsp":"index.html");
		action.setRedirect(tran?false: true);

		dao.setTransaction(conn, tran);
		dao.modifyTranStatus(conn, true);
		dao.clossConnection(conn);

		return action;
	}

	private ActionBean accessOutCtl() {
		ActionBean action = new ActionBean();
		ArrayList<EmployeeBean> list = null;
		DataAccessObject dao = null;
		boolean tran = false;
		this.emp = new EmployeeBean();
		this.emp.setEmCode(this.req.getParameter("emCode"));
		this.emp.setAccessType("9009");

		dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		dao.modifyTranStatus(conn, false);


		if(dao.insAccessHistory(conn, emp)) {
			tran = true;
			session.invalidate();
		}
		action.setPage(tran?"index.html":"administrator.jsp");
		action.setRedirect(tran?false: true);

		dao.setTransaction(conn, tran);
		dao.modifyTranStatus(conn, true);
		dao.clossConnection(conn);

		return action;
	}

}
