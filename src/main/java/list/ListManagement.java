package list;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.ActionBean;
import beans.EmployeeBean;
import beans.StudyListBean;


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
		case 2:
			action = this.getMyInfo();
			break;
		case 3:
			action = this.getStudentInfo();
			break;		
			default:
		}
		
		return action;
	}
	public String backController(String jobCode) {
		String data = null;
		
		switch(jobCode) {
			
		case "8":
			data = getAttendanceList();
			break;	
		case "9":
			data = getAttendanceListforT();
			break;	
		case "10":
			data = getTotalAttendance();
			break;	
		}
		
		return data;
	}
	
	private ActionBean getStudentInfo() {
		ActionBean action = new ActionBean();
		DataAccessObject dao = new DataAccessObject();
		ArrayList<EmployeeBean> list = null;
		ArrayList<StudyListBean> slList = null;
		emp =new EmployeeBean();
		Connection conn = dao.getConnection();
		this.emp.setEmCode(this.req.getParameter("emCode"));
		
		session = this.req.getSession();
		session.setAttribute("emCode", emp.getEmCode());
		if((list = dao.getLogInfo(conn, emp)) != null ) {
			slList = dao.getclassInfo(conn);
			for(int idx=0;idx<slList.size();idx++) {
				if(slList.get(idx).getSlEmCode().equals(list.get(0).getEmCode())) {
					req.setAttribute("slInfo", slList.get(idx));
				}
			}
			req.setAttribute("accessInfo", list);
			req.setAttribute("check", "1001");
		}
		
		action.setPage("statesForTeacher.jsp");
		action.setRedirect(false);
		dao.closeConnection(conn);
		
		return action;
	}
	private ActionBean getMyInfo() {
		ActionBean action = new ActionBean();
		DataAccessObject dao = new DataAccessObject();
		ArrayList<EmployeeBean> list = null;
		ArrayList<StudyListBean> slList = null;
		emp =new EmployeeBean();
		Connection conn = dao.getConnection();
		this.emp.setStCode(this.req.getParameter("stCode"));
		
		session = this.req.getSession();
		session.setAttribute("stCode", emp.getStCode());
		slList = dao.getclassInfo(conn);
		if((list = dao.getLogInfo2(conn, emp))!= null) {
			for(int idx=0;idx<slList.size();idx++) {
				if(slList.get(idx).getSlCode().equals(list.get(0).getSlCode())) {
					req.setAttribute("slInfo", slList.get(idx));
					req.setAttribute("check", "1");
				}
			}
			req.setAttribute("accessInfo", list);
		}
		action.setPage("statesForStudent.jsp");
		action.setRedirect(false);
		dao.closeConnection(conn);
		
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
			req.setAttribute("check", "1001");
		}
		action.setPage("statesForManager.jsp");
		action.setRedirect(false);
		dao.closeConnection(conn);
		
		return action;
	}
	private String getAttendanceList() {
		String data=null;
		String[] date=new String[2];
		EmployeeBean emp= new EmployeeBean();
		emp.setSlCode(this.req.getParameter("slCode"));
		emp.setStCode(this.req.getParameter("stCode"));
		date=this.req.getParameter("date").split("-");
		String sdate=date[0]+date[1];
		emp.setDate(sdate);
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		
		data=new Gson().toJson(dao.getTHList(conn, emp));
		dao.closeConnection(conn);
	
		return data;
	}
	private String getAttendanceListforT() {
		String data=null;
		String[] date=new String[3];
		EmployeeBean emp= new EmployeeBean();
		emp.setSlCode(this.req.getParameter("slCode"));
		emp.setStCode(this.req.getParameter("stCode"));
		date=this.req.getParameter("date").split("-");
		String sdate=date[0]+date[1]+date[2];
		emp.setDate(sdate);
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		
		data=new Gson().toJson(dao.getTHListforTeacher(conn, emp));
		dao.closeConnection(conn);
	
		return data;
	}
	private String getTotalAttendance() {
		String data=null;
		int[] total = new int[4];
		DataAccessObject dao = new DataAccessObject();
		Connection conn = dao.getConnection();
		EmployeeBean emp= new EmployeeBean();
		
		emp.setSlCode(this.req.getParameter("slCode"));
		emp.setStCode(this.req.getParameter("stCode"));
		total[0]=dao.getTotal1(conn, emp);
		total[1]=dao.getTotal2(conn, emp);
		total[2]=dao.getTotal3(conn, emp);
		total[3]=dao.getTotal4(conn, emp);
		data=new Gson().toJson(total);
		dao.closeConnection(conn);
		return data;
	}
}
