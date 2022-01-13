package login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import beans.EmployeeBean;

class DataAccessObject extends controller.DataAccessObject{

	boolean isEmployee(Connection connection, EmployeeBean log) {
		ResultSet rs = null;
		boolean result = false;
		String sql = "SELECT COUNT(*) FROM EM WHERE EM_CODE = ? AND EM_PASSWORD = ? AND EM_BWCODE = ?";
		try {
			this.pstmt = connection.prepareStatement(sql);
			this.pstmt.setNString(1, log.getEmCode());
			this.pstmt.setNString(2, log.getEmPassword());
			this.pstmt.setNString(3, log.getEmBwCode());

			rs = pstmt.executeQuery();

			while(rs.next()) {
				result = this.converToBoolean(rs.getInt(1));
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	boolean insAccessHistory(Connection connection, EmployeeBean emp) {
		boolean result = false;
		String dml = "INSERT INTO AH(AH_EMCODE, AH_ACCESSTIME, AH_ACCESSTYPE) "
				+ "				 VALUES(?, DEFAULT, ?)";
		try {
			this.pstmt = connection.prepareStatement(dml);
			this.pstmt.setNString(1, emp.getStCode());
			this.pstmt.setNString(2, emp.getStLog());
			result = this.converToBoolean(this.pstmt.executeUpdate());
		}catch(Exception e) {
			e.printStackTrace();
		}finally {

		}
		return result;
	}
	
	boolean insAccessHistory1(Connection connection, EmployeeBean emp) {
		boolean result = false;
		String dml = "INSERT INTO AH(AH_EMCODE, AH_ACCESSTIME, AH_ACCESSTYPE) "
				+ "				 VALUES(?, DEFAULT, ?)";
		try {
			this.pstmt = connection.prepareStatement(dml);
			this.pstmt.setNString(1, emp.getEmCode());
			this.pstmt.setNString(2, emp.getEmPassword());
			result = this.converToBoolean(this.pstmt.executeUpdate());
		}catch(Exception e) {
			e.printStackTrace();
		}finally {

		}
		return result;
	}
	
	boolean isStudent(Connection connection, EmployeeBean emp) {
		ResultSet rs = null;
		boolean result = false;
		String sql = "SELECT COUNT(*) FROM ST WHERE ST_CODE = ? AND ST_PASSWORD = ?";
		try {
			this.pstmt = connection.prepareStatement(sql);
			this.pstmt.setNString(1, emp.getStCode());
			this.pstmt.setNString(2, emp.getStPassword());

			rs = pstmt.executeQuery();

			while(rs.next()) {
				result = this.converToBoolean(rs.getInt(1));
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
}
