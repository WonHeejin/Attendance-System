package signal;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import beans.EmployeeBean;

public class SignalManagement {
	private HttpServletRequest req;
	public SignalManagement(HttpServletRequest req) {
		this.req=req;
	}
	
	public String backController(String jobCode) {
		String data = null;
		
		switch(jobCode) {
		case "2":
			data = regSign();
			break;
		
		}
		
		return data;
	}
	
	private String regSign() {
		String data=null;
		boolean tran=false;
		EmployeeBean emp=new EmployeeBean();
		emp.setSignal(this.req.getParameter("signal"));
		emp.setEmCode(this.req.getParameter("emCode"));
		emp.setSlCode(this.req.getParameter("slCode"));
		
		DataAccessObject dao=new DataAccessObject();
		Connection conn=dao.getConnection();
		dao.modifyTranStatus(conn, tran);
		if(dao.insSignal(conn, emp)) {
			tran=true;
			if(this.req.getParameter("signal").equals("2002")) {
				data=new Gson().toJson("입실신호 전송 완료");
			}else if(this.req.getParameter("signal").equals("3003")) {
				data=new Gson().toJson("중간신호 전송 완료");
			}else if(this.req.getParameter("signal").equals("4004")) {
				data=new Gson().toJson("외출신호 전송 완료");
			}else {
				data=new Gson().toJson("퇴실신호 전송 완료");
			}
			
		}else {
			if(this.req.getParameter("signal").equals("2002")) {
				data=new Gson().toJson("입실신호 전송 실패");
			}else if(this.req.getParameter("signal").equals("3003")) {
				data=new Gson().toJson("중간신호 전송 실패");
			}else if(this.req.getParameter("signal").equals("4004")) {
				data=new Gson().toJson("외출신호 전송 실패");
			}else {
				data=new Gson().toJson("퇴실신호 전송 실패");
			}
		}
		
		dao.setTransaction(conn, tran);
		dao.modifyTranStatus(conn, true);
		dao.clossConnection(conn);
		return data;
	}
}
