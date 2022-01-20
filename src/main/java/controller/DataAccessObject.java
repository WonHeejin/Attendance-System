package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import beans.EmployeeBean;
import beans.StudyListBean;

public class DataAccessObject {
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	public DataAccessObject() {
		this.pstmt = null;
		this.rs = null;
	}
	 
	public ArrayList<StudyListBean> getclassInfo(Connection connection){
	 	ArrayList<StudyListBean> slList = new ArrayList<StudyListBean>();
		ResultSet rs = null;

		String sql = "SELECT SL_CODE, SL_NAME, SL_EMCODE FROM SL";

		try {
			this.pstmt = connection.prepareStatement(sql);

			rs = this.pstmt.executeQuery();
			while(rs.next()) {

				StudyListBean slb = new StudyListBean();
				slb.setSlCode(rs.getNString("SL_CODE"));
				slb.setSlName(rs.getNString("SL_NAME"));
				slb.setSlEmCode(rs.getNString("SL_EMCODE"));

				slList.add(slb);
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
		return slList;
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

			//System.out.println(list.get(0).getEmName());
			return list;
		}
	 public ArrayList<EmployeeBean> getLogInfo2(Connection connection, EmployeeBean log){
			ArrayList<EmployeeBean> list = new ArrayList<EmployeeBean>();
			ResultSet rs = null;

			String sql = "SELECT EMCODE, ACCESSTIME, EMNAME ,SLCODE FROM ACCESSINFO2 WHERE ACCESSTIME = (SELECT MAX(AH_ACCESSTIME) FROM AH WHERE AH_EMCODE=?)";
			
			try {
				this.pstmt = connection.prepareStatement(sql);
				this.pstmt.setNString(1, log.getStCode());
				

				rs = this.pstmt.executeQuery();
				while(rs.next()) {
					
					EmployeeBean em = new EmployeeBean();
					em.setStCode(rs.getNString("EMCODE"));
					em.setDate(rs.getNString("ACCESSTIME"));
					em.setStName(rs.getNString("EMNAME"));
					em.setSlCode(rs.getNString("SLCODE"));
					
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
				if(pstmt!=null&&!pstmt.isClosed()) {pstmt.close();}
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
