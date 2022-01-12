package management;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.EmployeeBean;
import beans.slBean;



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
	public ArrayList<slBean> getSl(Connection conn) {
		slBean sl=null;
		ArrayList<slBean> list=new ArrayList<slBean>();
		String query="SELECT*FROM SL";
		try {
			this.pstmt=conn.prepareStatement(query);
			this.rs=this.pstmt.executeQuery();
			while(this.rs.next()) {
				sl= new slBean();
				sl.setSlCode(this.rs.getNString("SL_CODE"));
				sl.setSlName(this.rs.getNString("SL_NAME"));
				list.add(sl);
			}
		}catch(SQLException e) {e.printStackTrace();}
		return list;
	}
}
