package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.Connector;

public class SessionDAO {

	/**
	 * confirm login. If username and password login exists in database.
	 * @return
	 * true if username and password exists for an user <br>
	 * false if username and password doesn't exists for an user
	 */
	
	public boolean login(String name, String pwd) {
		String cmd = "CALL confirmLogin('%s','%s');";
		cmd = String.format(cmd, name,pwd);
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return false;
			}
			rs.next();

			if(rs.getInt("result") == 1) {
				return true;
			}
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				Connector.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	/**
	 * 
	 * @param ID
	 */
	
	public void logout(int ID) {

	}

	

}
