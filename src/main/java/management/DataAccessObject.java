package management;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.EmployeeBean;
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
	
	
}
