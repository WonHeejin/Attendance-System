package signal;

import java.sql.Connection;
import java.sql.SQLException;

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
}
