package management;

import java.sql.Connection;
import java.sql.SQLException;

import beans.EmployeeBean;
import beans.SignalBean;



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
