package login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import beans.EmployeeBean;

class DataAccessObject extends controller.DataAccessObject{

	boolean isEmployee(Connection connection, EmployeeBean log) {
		ResultSet rs = null;
		boolean result = false;
		String sql = "SELECT COUNT(*) FROM EM WHERE EM_CODE = ? AND EM_PASSWORD = ?";
		try {
			this.pstmt = connection.prepareStatement(sql);
			this.pstmt.setNString(1, log.getEmCode());
			this.pstmt.setNString(2, log.getEmPassword());

			rs = pstmt.executeQuery();

			while(rs.next()) {
				result = this.converToBoolean(rs.getInt(1));
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	boolean insAccessHistory(Connection connection, EmployeeBean log) {
		boolean result = false;
		String dml = "INSERT INTO AH(AH_EMCODE, AH_ACCESSTIME, AH_ACCESSTYPE) "
				+ "				 VALUES(?, DEFAULT, ?)";
		try {
			this.pstmt = connection.prepareStatement(dml);
			this.pstmt.setNString(1, log.getEmCode());
			this.pstmt.setNString(2, log.getAccessType());
			result = this.converToBoolean(this.pstmt.executeUpdate());
		}catch(Exception e) {
			e.printStackTrace();
		}finally {

		}
		return result;
	}
}
