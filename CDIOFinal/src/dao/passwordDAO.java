package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.Connector;
import logic.CDIOException.DALException;

public class passwordDAO {

	public passwordDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public static void createPassword(int ID,String pwd) throws DALException{
    Connector conn = new Connector();
		String cmd = "CALL createPassword('%d','%s');";
		cmd = String.format(cmd, ID,pwd);
		try {
			conn.doUpdate(cmd);	
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new DALException(e);
      }
    }
		
	}
	
	public static String getPassword(int ID) throws DALException{
    Connector conn = new Connector();
		String cmd = "CALL getPassword('%d');";
		cmd = String.format(cmd, ID);
		
		try {
			ResultSet rs = conn.doQuery(cmd);
			return rs.getString("password");
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new DALException(e);
      }
    }
		
	}
	
	public static int updatePassword(int user_ID, String token, String pwd) throws DALException{
    Connector conn = new Connector();
		String cmd = "CALL updatePassword('%d','%s','%s');";
		cmd = String.format(cmd, user_ID,token,pwd);
		
		try {
			ResultSet rs = conn.doQuery(cmd);
			return rs.getInt("Result");
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}
	
	public static void addPasswordToken(int user_ID) {
		
	}
	

}
