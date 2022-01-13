package management;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.ActionBean;
import beans.EmployeeBean;

import beans.StudyListBean;

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
		case 2:
			action = this.studyManagementOpen();
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

		case "3":
			data = getStudyList();
			break;


		case "4":
			data = getStudyListMaxCode();
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

		dao.closeConnection(conn);

		return action;
	}



	//studyManagement 연결 (기준)
	private ActionBean studyManagementOpen() {
		ActionBean action = new ActionBean();
		EmployeeBean emp = new EmployeeBean();
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		session = this.req.getSession();
		emp.setEmCode((String)session.getAttribute("emCode"));		

		this.req.setAttribute("accessInfo", dao.getLogInfo(conn, emp));

		action.setPage("studyManagement.jsp");
		action.setRedirect(false);
		dao.closeConnection(conn);

		return action;
	}

	//수업
	private String getStudyList() {
		String jsonData=null;
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();

		jsonData = new Gson().toJson(dao.getSlList(conn));

		dao.closeConnection(conn);

		return jsonData;
	}

	private String getMaxCode() {
		String data = null;
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection(); 
		data = dao.getMaxCode(conn, req.getParameter("code"));
		dao.closeConnection(conn);

		return data;
	}
	
	private String getStudyListMaxCode() {
		String jsonData=null;
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();

		jsonData = new Gson().toJson(dao.getSlListMaxCode(conn));

		dao.closeConnection(conn);

		return jsonData;
	}

}
