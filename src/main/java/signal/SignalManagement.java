package signal;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
		case "3":
			data = checkSign();
			break;	

		}

		return data;
	}
	private String checkSign() {
		String data =null;
		boolean tran=false;
		EmployeeBean emp= new EmployeeBean();
		emp.setSlCode(this.req.getParameter("slCode"));
		emp.setSignal(this.req.getParameter("ssCode"));
		emp.setStCode(this.req.getParameter("stCode"));
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
		Date time= new Date();
		String realTime= sdf.format(time);	
		String shDate=null;
		DataAccessObject dao= new DataAccessObject();
		Connection conn=dao.getConnection();
		dao.modifyTranStatus(conn, false);
		String csDate=null;;
		Date d1=null;
		Date d2=null;
		String signal=(emp.getSignal().equals("2002"))?"입실신호":(emp.getSignal().equals("3003"))?"중간확인신호":(emp.getSignal().equals("4004"))?"외출신호":"퇴실신호";

		if((csDate=dao.checkSignal(conn, emp))!=null) {
			try {
				d1=sdf.parse(realTime);
				d2=sdf.parse(csDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long gap=d1.getTime()-d2.getTime();
			if(gap/(60*1000)<=5) {			
				if((shDate=dao.checkStSignal(conn, emp))==null) {					
					if(dao.insStSignal(conn, emp)) {
						tran=true;
						data=signal+" 전송 완료";	
						if(emp.getSignal().equals("5005")) {
							//퇴실 신호(5005) 존재여부 확인
							if(dao.checkStSignal(conn, emp)!=null) {
								//출석 처리
								dao.updStSignal(conn, emp,"6006");
								//입실 신호(2002) 존재여부 확인
								emp.setSignal("2002");
								if(dao.checkStSignal(conn, emp)!=null) {
									//지각 처리
									sdf= new SimpleDateFormat("HHmmss");
									try {
										d1=sdf.parse(dao.getStartTime(conn, emp));
										d2=sdf.parse(dao.checkStSignal(conn, emp).substring(9));
									} catch (ParseException e) {e.printStackTrace();}
									long gap2=d2.getTime()-d1.getTime()/(60*1000);
									if(gap2>30) {
										dao.updStSignal(conn, emp,"6008");
									}
									//중간 신호(3003) 존재여부 확인
									emp.setSignal("3003");
									if(dao.checkStSignal(conn, emp)!=null) {
										//조퇴 처리
										sdf= new SimpleDateFormat("HHmmss");
										try {
											d1=sdf.parse(dao.getEndTime(conn, emp));
											emp.setSignal("5005");
											d2=sdf.parse(dao.checkStSignal(conn, emp).substring(9));
											System.out.println(d1+":"+d2+"조퇴");
										} catch (ParseException e) {e.printStackTrace();}
										long gap3=d1.getTime()-d2.getTime()/(60*1000);
										if(gap3>60) {
											dao.updStSignal(conn, emp,"6009");
										}
									}else {dao.updStSignal(conn, emp,"6007");}	
								}else {dao.updStSignal(conn, emp,"6007");}															
							}		
						}
					}
				}else {
					data="이미 "+signal+" 기록이 존재합니다"+shDate;
				}
			}else {
				data=signal+"가 존재하지 않습니다.";
			}		
		}else {
			data=signal+"가 존재하지 않습니다.";
		}

		dao.closeConnection(conn);
		return data;
	}

	private String regSign() {
		String data=null;
		boolean tran=false;
		ArrayList<EmployeeBean> list=new ArrayList<EmployeeBean>();
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
				list=dao.getStList(conn, emp.getSlCode());
				if(dao.countSignal(conn, emp).equals("1")) {dao.insStAttendance(conn, list);}			
				data="입실신호 전송 완료";
			}else if(this.req.getParameter("signal").equals("3003")) {
				data="중간신호 전송 완료";
			}else if(this.req.getParameter("signal").equals("4004")) {
				data="외출신호 전송 완료";
			}else {
				data="퇴실신호 전송 완료";
			}

		}else {
			if(this.req.getParameter("signal").equals("2002")) {
				data="입실신호 전송 실패";
			}else if(this.req.getParameter("signal").equals("3003")) {
				data="중간신호 전송 실패";
			}else if(this.req.getParameter("signal").equals("4004")) {
				data="외출신호 전송 실패";
			}else {
				data="퇴실신호 전송 실패";
			}
		}

		dao.setTransaction(conn, tran);
		dao.modifyTranStatus(conn, true);
		dao.closeConnection(conn);
		return data;
	}
}
