package signal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.EmployeeBean;

public class DataAccessObject extends controller.DataAccessObject{
	public boolean insSignal(Connection conn, EmployeeBean emp) {
		boolean result = false;
		String dml="INSERT INTO CS(CS_DATE,CS_SLCODE,CS_EMCODE,CS_SSCODE)"
				+ " VALUES(TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),?,?,?)";
		try {
			this.pstmt=conn.prepareStatement(dml);
			this.pstmt.setNString(1, emp.getSlCode());
			this.pstmt.setNString(2, emp.getEmCode());
			this.pstmt.setNString(3, emp.getSignal());
			result=this.converToBoolean(this.pstmt.executeUpdate());

		}catch(SQLException e) {e.printStackTrace();}
		return result;
	}

	public String checkSignal(Connection conn, EmployeeBean emp) {	
		String time=null;
		String query="SELECT CS_DATE FROM CS WHERE SUBSTR(CS_DATE,1,8)=TO_CHAR(SYSDATE,'YYYYMMDD') AND  CS_SLCODE=? AND CS_SSCODE=?";
		try {
			this.pstmt=conn.prepareStatement(query);
			this.pstmt.setNString(1, emp.getSlCode());
			this.pstmt.setNString(2, emp.getSignal());
			this.rs=this.pstmt.executeQuery();
			if(this.rs!=null) {
				while(this.rs.next()) {
					time=this.rs.getNString("CS_DATE");
				}
			}
		}catch(SQLException e) {e.printStackTrace();
		}finally {
			try {if(this.rs!=null&&!this.rs.isClosed()) {this.rs.close();}
			} catch (SQLException e) {e.printStackTrace();}
		}
		return time;
	}
	public String checkStSignal(Connection conn, EmployeeBean emp) {	
		String time=null;
		String query="SELECT SH_DATE FROM SH WHERE SUBSTR(SH_DATE,1,8)=TO_CHAR(SYSDATE,'YYYYMMDD')"
										+ " AND SH_STCODE=? AND SH_STSLCODE=? AND SH_SSCODE=?";
		try {
			this.pstmt=conn.prepareStatement(query);
			this.pstmt.setNString(1, emp.getStCode());
			this.pstmt.setNString(2, emp.getSlCode());
			this.pstmt.setNString(3, emp.getSignal());
			this.rs=this.pstmt.executeQuery();
			if(this.rs!=null) {
				while(this.rs.next()) {
					emp.setDate(this.rs.getNString("SH_DATE"));	
				}
			}
		}catch(SQLException e) {e.printStackTrace();
		}finally {
			try {if(this.rs!=null&&!this.rs.isClosed()) {this.rs.close();}
			} catch (SQLException e) {e.printStackTrace();}
		}
		
		return time=emp.getDate();
	}
	public boolean insStSignal(Connection conn, EmployeeBean emp) {
		boolean result = false;
		String dml="INSERT INTO SH(SH_DATE,SH_STCODE,SH_STSLCODE,SH_SSCODE)"
				+ " VALUES(TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),?,?,?)";
		try {
			this.pstmt=conn.prepareStatement(dml);
			this.pstmt.setNString(1, emp.getStCode());
			this.pstmt.setNString(2, emp.getSlCode());
			this.pstmt.setNString(3, emp.getSignal());
			result=this.converToBoolean(this.pstmt.executeUpdate());

		}catch(SQLException e) {e.printStackTrace();}
		return result;
	}
	public ArrayList<EmployeeBean> getStList(Connection conn,String slCode) {
		EmployeeBean emp=null;
		ArrayList<EmployeeBean> list=new ArrayList<EmployeeBean>();
		String query="SELECT ST_CODE, ST_NAME, ST_SLCODE FROM ST WHERE ST_SLCODE=?";
		try {
			this.pstmt=conn.prepareStatement(query);
			this.pstmt.setNString(1, slCode);
			this.rs=this.pstmt.executeQuery();
			while(this.rs.next()) {
				emp= new EmployeeBean();
				emp.setStCode(this.rs.getNString("ST_CODE"));
				emp.setStName(this.rs.getNString("ST_NAME"));
				emp.setSlCode(this.rs.getNString("ST_SLCODE"));
				list.add(emp);
			}
		}catch(SQLException e) {e.printStackTrace();
		}finally {try {
			if(!this.rs.isClosed()) {this.rs.close();}
		} catch (SQLException e) {e.printStackTrace();}}
		return list;
	}
	public void insStAttendance(Connection conn, ArrayList<EmployeeBean> list) {	
			String dml=" INSERT INTO TH(TH_DATE, TH_STCODE, TH_STSLCODE, TH_STATES)"
						+ " VALUES(SYSDATE,?,?,?)";
			try {
				this.pstmt=conn.prepareStatement(dml);
				for(int idx=0;idx<list.size();idx++) {
					this.pstmt.setNString(1, list.get(idx).getStCode());
					this.pstmt.setNString(2, list.get(idx).getSlCode());
					this.pstmt.setNString(3, "6007");
					this.pstmt.executeUpdate();
				}	
			}catch(SQLException e) {e.printStackTrace();
			}finally {try {
				if(!this.rs.isClosed()) {this.rs.close();}
			} catch (SQLException e) {e.printStackTrace();}}
		
	}
	public String countSignal(Connection conn, EmployeeBean emp) {	
		String count=null;
		String query="SELECT COUNT(*) AS CT FROM CS WHERE SUBSTR(CS_DATE,1,8)=TO_CHAR(SYSDATE,'YYYYMMDD') AND  CS_SLCODE=? AND CS_SSCODE=?";
		try {
			this.pstmt=conn.prepareStatement(query);
			this.pstmt.setNString(1, emp.getSlCode());
			this.pstmt.setNString(2, emp.getSignal());
			this.rs=this.pstmt.executeQuery();
			if(this.rs!=null) {
				while(this.rs.next()) {
					count=this.rs.getNString("CT");
				}
			}
		}catch(SQLException e) {e.printStackTrace();
		}finally {
			try {if(this.rs!=null&&!this.rs.isClosed()) {this.rs.close();}
			} catch (SQLException e) {e.printStackTrace();}
		}
		return count;
	}
	public int countStSignal(Connection conn,EmployeeBean emp) {
		int count=0;
	
		String query="SELECT COUNT(*) AS CT FROM SH WHERE SUBSTR(SH_DATE,1,8)=TO_CHAR(SYSDATE,'YYYYMMDD') AND SH_STCODE=? ";
		try {
			this.pstmt=conn.prepareStatement(query);
			this.pstmt.setNString(1, emp.getStCode());
			this.rs=this.pstmt.executeQuery();
			if(this.rs!=null) {
				while(this.rs.next()) {
					count=this.rs.getInt("CT");
				}
			}
		}catch(SQLException e) {e.printStackTrace();
		}finally {try {
			if(!this.rs.isClosed()) {this.rs.close();}
		} catch (SQLException e) {e.printStackTrace();}}
		return count;
	}
	public void updStSignal(Connection conn,EmployeeBean emp,String signal) {
	
		String query="UPDATE TH SET TH_STATES=? WHERE TH_STCODE=? AND TH_STSLCODE=?";
		try {
			this.pstmt=conn.prepareStatement(query);
			this.pstmt.setNString(1, signal);
			this.pstmt.setNString(2, emp.getStCode());
			this.pstmt.setNString(3, emp.getSlCode());
			this.pstmt.executeUpdate();
			
		}catch(SQLException e) {e.printStackTrace();
		}finally {try {
			if(!this.rs.isClosed()) {this.rs.close();}
		} catch (SQLException e) {e.printStackTrace();}}
		
	}
	public String getStartTime(Connection conn,EmployeeBean emp) {
		String time=null;
	
		String query="SELECT STARTTIME FROM SL WHERE SL_CODE=?";
		try {
			this.pstmt=conn.prepareStatement(query);
			this.pstmt.setNString(1, emp.getSlCode());
			this.rs=this.pstmt.executeQuery();
			if(this.rs!=null) {
				while(this.rs.next()) {
					time=this.rs.getNString("STARTTIME");
				}
			}
		}catch(SQLException e) {e.printStackTrace();
		}finally {try {
			if(!this.rs.isClosed()) {this.rs.close();}
		} catch (SQLException e) {e.printStackTrace();}}
		return time;
	}
	public String getEndTime(Connection conn,EmployeeBean emp) {
		String time=null;
	
		String query="SELECT ENDTIME FROM SL WHERE SL_CODE=?";
		try {
			this.pstmt=conn.prepareStatement(query);
			this.pstmt.setNString(1, emp.getSlCode());
			this.rs=this.pstmt.executeQuery();
			if(this.rs!=null) {
				while(this.rs.next()) {
					time=this.rs.getNString("ENDTIME");
				}
			}
		}catch(SQLException e) {e.printStackTrace();
		}finally {try {
			if(!this.rs.isClosed()) {this.rs.close();}
		} catch (SQLException e) {e.printStackTrace();}}
		return time;
	}
}
