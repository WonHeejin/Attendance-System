package management;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.ActionBean;
import beans.EmployeeBean;
import beans.SignalBean;
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
			data = getSendSignal();
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

	private String getMaxCode() {
		String data = null;
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection(); 
		data = dao.getMaxCode(conn, req.getParameter("code"));
		dao.closeConnection(conn);

		return data;
	}

	private String getSendSignal() {
		String message ="0";
		HttpSession session = this.req.getSession();

		SignalBean sb = new SignalBean();
		
		//sb.setSlCode(this.req.getParameter("slCode"));
		sb.setEmCode(this.req.getParameter("emCode"));
		sb.setSsCode(this.req.getParameter("ssCode"));
		
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		boolean tran = false;
		boolean detail = true;
		dao.modifyTranStatus(conn, false);
		dao.getSendSignal(conn, sb);
		if(sb.getEmCode() != null) {
			if(!dao.getSendSignal(conn, sb)) {
				detail = false;
			}
			if(detail) {
				message = "입실 신호를 보냈습니다.";
				tran = true;
			}
		}
		dao.setTransaction(conn, tran);
		dao.modifyTranStatus(conn, true);
		dao.closeConnection(conn);

		return message;
	}
	
	
}
