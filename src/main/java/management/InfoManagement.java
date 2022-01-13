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
			data = regMember();
			break;	
		case "4":
			data = getStudyListhj();
			break;	
		case "5":
			data = getStudentList();
			break;	
		case "6":
			data = getStudyListgj();
			break;
		case "7":
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
		dao.closeConnection(conn);
	
		return data;
	}
	private String getStudyListhj() {
		String data=null;
		
		DataAccessObject dao=new DataAccessObject();
		Connection conn=dao.getConnection();
		data=new Gson().toJson(dao.getSl(conn));		
		dao.closeConnection(conn);
		return data;
	}
	private String getStudentList() {
		String data=null;
		DataAccessObject dao=new DataAccessObject();
		Connection conn=dao.getConnection();
		data=new Gson().toJson(dao.getSs(conn,this.req.getParameter("slCode")));		
		dao.closeConnection(conn);
		return data;
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
		private String getStudyListgj() {
			String jsonData=null;
			DataAccessObject dao = new DataAccessObject();
			Connection conn = dao.getConnection();

			jsonData = new Gson().toJson(dao.getSlList(conn));

			dao.closeConnection(conn);

			return jsonData;
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
