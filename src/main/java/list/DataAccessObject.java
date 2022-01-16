package list;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.EmployeeBean;
import beans.THBean;

public class DataAccessObject extends controller.DataAccessObject{

	public ArrayList<THBean> getTHList(Connection conn, EmployeeBean emp){
		ArrayList<THBean> list= new ArrayList<THBean>();
		THBean th=null;
		String query="SELECT*FROM THINFO WHERE TO_CHAR(SYSDATE,'YYYYMM') = ? AND STCODE=? AND SLCODE=?";
		try {
			this.pstmt = conn.prepareStatement(query);
			this.pstmt.setNString(1, emp.getDate());
			this.pstmt.setNString(2, emp.getStCode());
			this.pstmt.setNString(3, emp.getSlCode());
			rs = this.pstmt.executeQuery();
			while(rs.next()) {			
				th = new THBean();
				th.setThDate(this.rs.getNString("THDATE"));
				th.setThName(this.rs.getNString("SSNAME"));
				th.setThStName(this.rs.getNString("STNAME"));
				list.add(th);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public ArrayList<THBean> getTHListforTeacher(Connection conn, EmployeeBean emp){
		ArrayList<THBean> list= new ArrayList<THBean>();
		THBean th=null;
		String query="SELECT*FROM THINFO WHERE TO_CHAR(SYSDATE,'YYYYMMDD') = ? AND SLCODE=?";
		try {
			this.pstmt = conn.prepareStatement(query);
			this.pstmt.setNString(1, emp.getDate());
			this.pstmt.setNString(2, emp.getSlCode());
			rs = this.pstmt.executeQuery();
			while(rs.next()) {			
				th = new THBean();
				th.setThDate(this.rs.getNString("THDATE"));
				th.setThName(this.rs.getNString("SSNAME"));
				th.setThStName(this.rs.getNString("STNAME"));
				list.add(th);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public int getTotal1(Connection conn, EmployeeBean emp) {
		int result=0;		
		try {
			String query1="SELECT COUNT(*) AS CT FROM THINFO WHERE STCODE=? AND SLCODE=? AND SSCODE='6006'";
			this.pstmt = conn.prepareStatement(query1);
			this.pstmt.setNString(1, emp.getStCode());
			this.pstmt.setNString(2, emp.getSlCode());
			rs = this.pstmt.executeQuery();
			while(rs.next()) {			
				result=rs.getInt(1);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public int getTotal2(Connection conn, EmployeeBean emp) {
		int result=0;		
		try {
			String query1="SELECT COUNT(*) AS CT FROM THINFO WHERE STCODE=? AND SLCODE=? AND SSCODE='6007'";
			this.pstmt = conn.prepareStatement(query1);
			this.pstmt.setNString(1, emp.getStCode());
			this.pstmt.setNString(2, emp.getSlCode());
			rs = this.pstmt.executeQuery();
			while(rs.next()) {			
				result=rs.getInt(1);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public int getTotal3(Connection conn, EmployeeBean emp) {
		int result=0;		
		try {
			String query1="SELECT COUNT(*) AS CT FROM THINFO WHERE STCODE=? AND SLCODE=? AND SSCODE='6008'";
			this.pstmt = conn.prepareStatement(query1);
			this.pstmt.setNString(1, emp.getStCode());
			this.pstmt.setNString(2, emp.getSlCode());
			rs = this.pstmt.executeQuery();
			while(rs.next()) {			
				result=rs.getInt(1);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public int getTotal4(Connection conn, EmployeeBean emp) {
		int result=0;		
		try {
			String query1="SELECT COUNT(*) AS CT FROM THINFO WHERE STCODE=? AND SLCODE=? AND SSCODE='6009'";
			this.pstmt = conn.prepareStatement(query1);
			this.pstmt.setNString(1, emp.getStCode());
			this.pstmt.setNString(2, emp.getSlCode());
			rs = this.pstmt.executeQuery();
			while(rs.next()) {			
				result=rs.getInt(1);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
/*
 * String query2="SELECT COUNT(*) AS CT FROM THINFO WHERE STCODE=? AND SLCODE=? AND SSCODE='6007'";
			this.pstmt = conn.prepareStatement(query2);
			this.pstmt.setNString(1, emp.getStCode());
			this.pstmt.setNString(2, emp.getSlCode());
			rs = this.pstmt.executeQuery();
			while(rs.next()) {			
				result[1]=rs.getInt(1);
			}
			String query3="SELECT COUNT(*) AS CT FROM THINFO WHERE STCODE=? AND SLCODE=? AND SSCODE='6008'";
			this.pstmt = conn.prepareStatement(query3);
			this.pstmt.setNString(1, emp.getStCode());
			this.pstmt.setNString(2, emp.getSlCode());
			rs = this.pstmt.executeQuery();
			while(rs.next()) {			
				result[2]=rs.getInt(1);
			}
			String query4="SELECT COUNT(*) AS CT FROM THINFO WHERE STCODE=? AND SLCODE=? AND SSCODE='6009'";
			this.pstmt = conn.prepareStatement(query4);
			this.pstmt.setNString(1, emp.getStCode());
			this.pstmt.setNString(2, emp.getSlCode());
			rs = this.pstmt.executeQuery();
			while(rs.next()) {			
				result[3]=rs.getInt(1);
			}*/
