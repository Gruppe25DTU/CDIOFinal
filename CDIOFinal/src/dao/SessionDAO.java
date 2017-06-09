package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.Connector;
import daoInterface.SessionInterfaceDAO;

public class SessionDAO implements SessionInterfaceDAO{

	@Override
	public boolean login(String name, String pwd) {
		String cmd = "CALL confirmLogin('','');";
		cmd = String.format(cmd, name,pwd);
		try {
			ResultSet rs = Connector.doQuery(cmd);
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
	 * Why?
	 * @param ID
	 */
	@Override
	public void logout(int ID) {

	}

	

}
