package management;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.EmployeeBean;
import beans.SignalBean;
import beans.StudyListBean;



class DataAccessObject extends controller.DataAccessObject{

	public String getMaxCode(Connection conn, String code) {
		String mun = null;
		String query = null;
		if(code.equals("1")) {
			query = "SELECT MAX(ST_CODE) AS maxCode FROM ST";

		}else {
			query = "SELECT MAX(EM_CODE) AS maxCode FROM EM";
		}
			try {
				this.pstmt = conn.prepareStatement(query);
				this.rs =this.pstmt.executeQuery();

				while(rs.next()) {
					mun = (Integer.parseInt(rs.getNString("maxCode"))+1)+"";
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(!rs.isClosed()) rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		
		return mun;
	}
	public boolean insEmp(Connection conn, EmployeeBean emp) {
		boolean result=false;
		String dml="INSERT INTO EM(EM_CODE,EM_NAME,EM_PASSWORD,EM_BWCODE)"
				+ " VALUES(?,?,?,?)";
		try {
			this.pstmt = conn.prepareStatement(dml);
			this.pstmt.setNString(1, emp.getEmCode());
			this.pstmt.setNString(2, emp.getEmName());
			this.pstmt.setNString(3, emp.getEmPassword());
			this.pstmt.setNString(4, emp.getEmBwCode());
			result=this.converToBoolean(this.pstmt.executeUpdate());
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean insStudent(Connection conn, EmployeeBean emp) {
		boolean result=false;
		String dml="INSERT INTO ST(ST_CODE, ST_SLCODE, ST_NAME, ST_PASSWORD)"
				+ " VALUES(?,?,?,?)";
		try {
			this.pstmt = conn.prepareStatement(dml);
			this.pstmt.setNString(1, emp.getEmCode());
			this.pstmt.setNString(2, emp.getSlCode());
			this.pstmt.setNString(3, emp.getEmName());
			this.pstmt.setNString(4, emp.getEmPassword());	
			result=this.converToBoolean(this.pstmt.executeUpdate());
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public ArrayList<StudyListBean> getSl(Connection conn) {
		StudyListBean sl=null;
		ArrayList<StudyListBean> list=new ArrayList<StudyListBean>();
		String query="SELECT*FROM SL";
		try {
			this.pstmt=conn.prepareStatement(query);
			this.rs=this.pstmt.executeQuery();
			while(this.rs.next()) {
				sl= new StudyListBean();
				sl.setSlCode(this.rs.getNString("SL_CODE"));
				sl.setSlName(this.rs.getNString("SL_NAME"));
				list.add(sl);
			}
		}catch(SQLException e) {e.printStackTrace();}
		return list;
	}
	public ArrayList<EmployeeBean> getSs(Connection conn,String slCode) {
		EmployeeBean emp=null;
		ArrayList<EmployeeBean> list=new ArrayList<EmployeeBean>();
		String query="SELECT ST_CODE, ST_NAME FROM ST WHERE ST_SLCODE=?";
		try {
			this.pstmt=conn.prepareStatement(query);
			this.pstmt.setNString(1, slCode);
			this.rs=this.pstmt.executeQuery();
			while(this.rs.next()) {
				emp= new EmployeeBean();
				emp.setEmCode(this.rs.getNString("ST_CODE"));
				emp.setEmName(this.rs.getNString("ST_NAME"));
				list.add(emp);
			}
		}catch(SQLException e) {e.printStackTrace();}
		return list;
	}
	public ArrayList<StudyListBean> getSlList(Connection conn) {
		ArrayList<StudyListBean> list = new ArrayList<StudyListBean>();
		String query = "SELECT SL.SL_CODE AS SLCODE, SL.SL_NAME AS SLNAME, SL.SL_EMCODE AS SLEMCODE, EM.EM_NAME AS EMNAME, SL.SL_CRCODE AS SLCRCODE, CR.CR_NAME AS CRNAME, SL.SL_STARTDATE AS SLSTARTDATE, SL.SL_ENDDATE SLENDDATE "
				+ "FROM SL INNER JOIN EM ON SL_EMCODE = EM_CODE "
						+ "INNER JOIN CR ON SL_CRCODE = CR_CODE";

			try {
				this.pstmt = conn.prepareStatement(query);
				this.rs =this.pstmt.executeQuery();
				while(rs.next()) {
				StudyListBean slb = new StudyListBean();
					slb.setSlCode(rs.getNString("SLCODE"));
					slb.setSlName(rs.getNString("SLNAME"));
					slb.setSlEmCode(rs.getNString("SLEMCODE"));
					slb.setSlEmName(rs.getNString("EMNAME"));
					slb.setSlCrCode(rs.getNString("SLCRCODE"));
					slb.setSlCrName(rs.getNString("CRNAME"));
					slb.setSlStartDate(rs.getNString("SLSTARTDATE"));
					slb.setSlEndDate(rs.getNString("SLENDDATE"));

					list.add(slb);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(!rs.isClosed()) rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}

		return list;
	}

	public ArrayList<StudyListBean> getSlListMaxCode(Connection conn) {
		ArrayList<StudyListBean> list = new ArrayList<StudyListBean>();
		String query = "SELECT MAX(SL_CODE) AS SLCODE FROM SL";

			try {
				this.pstmt = conn.prepareStatement(query);
				this.rs =this.pstmt.executeQuery();
				while(rs.next()) {
				StudyListBean slb = new StudyListBean();
					slb.setSlCode((Integer.parseInt(rs.getNString("SLCODE"))+1)+"");
					list.add(slb);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(!rs.isClosed()) rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}

		return list;
	}
	boolean getSendSignal(Connection conn, SignalBean sb) {
		boolean result = false;
		String dml = "INSERT INTO CS(CS_DATE, CS_SLCODE, CS_EMCODE, CS_SSCODE) VALUES (TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'), ?, ?, ?)";
		try {
			this.pstmt = conn.prepareStatement(dml);
			this.pstmt.setNString(1, sb.getSlCode());
			this.pstmt.setNString(2, sb.getEmCode());
			this.pstmt.setNString(3, sb.getSsCode());
			result = this.converToBoolean(this.pstmt.executeUpdate());
		}catch(Exception e) {
			e.printStackTrace();
		}finally {

		}
		return result;
	}
}
