package management;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.ActionBean;
import beans.EmployeeBean;
import beans.slBean;

public class InfoManagement {
	private HttpServletRequest req;
	private HttpSession session;
	
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
			data = regMember();
			break;	
		case "4":
			data = getStudyList();
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
	private String regMember() {
		String data= null;
		boolean tran=false;
		EmployeeBean emp= new EmployeeBean();		
		emp.setEmCode(this.req.getParameter("emCode"));
		emp.setEmName(this.req.getParameter("emName"));
		emp.setEmPassword(this.req.getParameter("emPassword"));
		DataAccessObject dao= new DataAccessObject();
		Connection conn=dao.getConnection();
		dao.modifyTranStatus(conn, tran);

		if(!this.req.getParameter("slCode").equals("0")) {
			//학생등록
			emp.setSlCode(this.req.getParameter("slCode"));
			if(dao.insStudent(conn, emp)) {
				tran=true;
				data=new Gson().toJson("학생이 등록되었습니다.");
			}else {data="학생 등록에 실패하였습니다.";}
		}else {
			//선생님 or 관리자 등록
			emp.setEmBwCode(this.req.getParameter("bwCode"));
			if(dao.insEmp(conn, emp)) {
				tran=true;
				data="직원이 등록되었습니다.";
			}else {data="직원 등록에 실패하였습니다.";}
		}
		dao.setTransaction(conn, tran);
		dao.modifyTranStatus(conn, true);
		dao.clossConnection(conn);
	
		return data;
	}
	private String getStudyList() {
		String data=null;
		
		DataAccessObject dao=new DataAccessObject();
		Connection conn=dao.getConnection();
		data=new Gson().toJson(dao.getSl(conn));
		
		dao.clossConnection(conn);
		
		
		return data;
	}
}
