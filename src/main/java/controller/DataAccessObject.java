package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import beans.EmployeeBean;

public class DataAccessObject {
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	public DataAccessObject() {
		this.pstmt = null;
		this.rs = null;
	}
	
	 public ArrayList<EmployeeBean> getLogInfo(Connection connection, EmployeeBean log){
			ArrayList<EmployeeBean> list = new ArrayList<EmployeeBean>();
			ResultSet rs = null;

			String sql = "SELECT EMCODE, EMNAME, ACCESSTIME FROM ACCESSINFO "
					+"WHERE ACCESSTIME = (SELECT TO_CHAR(MAX(AH_ACCESSTIME), 'YYYY-MM-DD HH24:MI:SS') FROM AH"
					+"                     WHERE AH_EMCODE=?)";

			try {
				this.pstmt = connection.prepareStatement(sql);
				this.pstmt.setNString(1, log.getEmCode());
				rs = this.pstmt.executeQuery();
				
				while(rs.next()) {
					
					EmployeeBean em = new EmployeeBean();
					em.setEmCode(rs.getNString("EMCODE"));
					em.setEmName(rs.getNString("EMNAME"));
					em.setDate(rs.getNString("ACCESSTIME"));

					list.add(em);
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
	 
	 /* Driver Loading & Create Connection */
		public Connection getConnection() {
			Connection connection = null;
			String[] url = {"jdbc:oracle:thin:@192.168.0.8:1521:xe", "sys as sysdba", "1234"};
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection(url[0], url[1], url[2]);

			}catch(Exception e) {
				e.printStackTrace();
			}

			return connection;
		}

		/* Transaction 상태 변경 */
		 public void modifyTranStatus(Connection connection, boolean status) {
			try {
				if(connection!=null && !connection.isClosed()) {
					connection.setAutoCommit(status);
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/* Transaction 처리 :: Commit | Rollback */
		 public void setTransaction(Connection connection, boolean tran) {

			try {
				if(connection!=null && !connection.isClosed()) {
					if(tran) {connection.commit();
					}else {
						connection.rollback();
					} 
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}

		 }
		 
			/* Close Connection */
		 public void closeConnection (Connection connection) {
			try {
				if(!pstmt.isClosed()) {pstmt.close();}
				if(connection!=null && !connection.isClosed()) {
					connection.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/* converToBoolean */
		 public boolean converToBoolean(int value) {
			return (value > 0)? true : false;
		}
		 
}
