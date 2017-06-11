package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.Connector;
import daoInterface.SessionInterfaceDAO;

public class SessionDAO implements SessionInterfaceDAO{

	/**
	 * confirm login. If username and password login exists in database.
	 * @return
	 * true if username and password exists for an user <br>
	 * false if username and password doesn't exists for an user
	 */
	@Override
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param ID
	 */
	@Override
	public void logout(int ID) {

	}

	

}
