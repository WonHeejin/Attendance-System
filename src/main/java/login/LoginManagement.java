package login;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;

import beans.ActionBean;
import beans.EmployeeBean;
import beans.StudyListBean;


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
		ArrayList<StudyListBean> slList = null;
		String check  = this.req.getParameter("people");
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		this.emp = new EmployeeBean();
		boolean tran = false;
		if(check.equals("1")){
			this.emp.setStCode(this.req.getParameter("code"));
			this.emp.setStPassword(this.req.getParameter("password"));
			this.emp.setStLog("1001");
			this.emp.setEmBwCode(check);

			dao = new DataAccessObject();

			dao.modifyTranStatus(conn, false);

			if(dao.isStudent(conn, emp)) {
				if(dao.insAccessHistory(conn, emp)) {
					tran = true;
					session = this.req.getSession();
					session.setAttribute("stCode", emp.getStCode());
					slList = dao.getclassInfo(conn);
					if((list = dao.getLogInfo2(conn, emp))!= null) {
						for(int idx=0;idx<slList.size();idx++) {
							if(slList.get(idx).getSlCode().equals(list.get(0).getSlCode())) {
								req.setAttribute("slInfo", slList.get(idx));
							}
						}
						
						req.setAttribute("accessInfo", list);
						req.setAttribute("check", "1");
					}
				}
			}
			action.setPage(tran?"student.jsp":"index.html");




		}else if(check.equals("1000")){
			this.emp.setEmCode(this.req.getParameter("code"));
			this.emp.setEmPassword(this.req.getParameter("password"));
			this.emp.setAccessType("1001");
			this.emp.setEmBwCode(check);

			dao = new DataAccessObject();

			dao.modifyTranStatus(conn, false);

			if(dao.isEmployee(conn, emp)) {
				if(dao.insAccessHistory1(conn, emp)) {
					tran = true;
					session = this.req.getSession();
					session.setAttribute("emCode", emp.getEmCode());
					slList = dao.getclassInfo(conn);
					if((list = dao.getLogInfo(conn, emp))!= null) {	
						for(int idx=0;idx<slList.size();idx++) {
							if(slList.get(idx).getSlEmCode().equals(list.get(0).getEmCode())) {
								req.setAttribute("slInfo", slList.get(idx));
							}
						}
						req.setAttribute("accessInfo", list);
						req.setAttribute("check", "1000");
					}
				}
			}
			action.setPage(tran?"teacher.jsp":"index.html");


		}else if(check.equals("1001")){
			this.emp.setEmCode(this.req.getParameter("code"));
			this.emp.setEmPassword(this.req.getParameter("password"));
			this.emp.setAccessType("1001");
			this.emp.setEmBwCode(check);

			dao = new DataAccessObject();


			dao.modifyTranStatus(conn, false);

			if(dao.isEmployee(conn, emp)) {
				if(dao.insAccessHistory1(conn, emp)) {
					tran = true;
					session = this.req.getSession();
					session.setAttribute("emCode", emp.getEmCode());
					slList = dao.getclassInfo(conn);
					if((list = dao.getLogInfo(conn, emp))!= null) {	
						req.setAttribute("accessInfo", list);
						req.setAttribute("check", "1001");
						
					}
				}
			}
			action.setPage(tran?"administrator.jsp":"index.html");


		}
		action.setRedirect(tran?false: true);
		dao.setTransaction(conn, tran);
		dao.modifyTranStatus(conn, true);
		dao.closeConnection(conn);
		return action;
	}

	private ActionBean accessOutCtl() {
		ActionBean action = new ActionBean();
		ArrayList<EmployeeBean> list = null;
		boolean tran = false;
		this.emp = new EmployeeBean();
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		String check  = this.req.getParameter("people");
	
		if(check.equals("1")){
			this.emp.setStCode(this.req.getParameter("stCode"));
			this.emp.setStLog("9009");
			dao = new DataAccessObject();
			dao.modifyTranStatus(conn, false);
			session = this.req.getSession();

			if(dao.insAccessHistory(conn, emp)) {
				tran = true;
				session.invalidate();
			}

			action.setPage(tran?"index.html":"student.jsp");
		}else{
			
			this.emp.setEmCode(this.req.getParameter("emCode"));
			this.emp.setEmBwCode(this.req.getParameter("people"));
			this.emp.setStLog("9009");
			dao = new DataAccessObject();
			dao.modifyTranStatus(conn, false);
			session = this.req.getSession();

			if(dao.insAccessHistory1(conn, emp)) {
				tran = true;
				session.invalidate();
			}
			if(check.equals("1000")) {
				action.setPage(tran?"index.html":"teacher.jsp");
			}else {
				action.setPage(tran?"index.html":"administrator.jsp");
			}
			
		}



		action.setRedirect(tran?false: true);
		dao.setTransaction(conn, tran);
		dao.modifyTranStatus(conn, true);
		dao.closeConnection(conn);

		return action;
	}

}
