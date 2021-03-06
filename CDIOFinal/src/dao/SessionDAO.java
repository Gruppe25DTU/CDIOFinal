package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.Connector;
import logic.CDIOException.*;

public class SessionDAO {

	/**
	 * confirm login. If username and password login exists in database.
	 * @return
	 * true if username and password exists for an user <br>
	 * false if username and password doesn't exists for an user
	 * @throws InvalidLoginException 
	 */

	public int login(String name, String pwd) throws DALException, InvalidLoginException{
    Connector conn = new Connector();
		String cmd = "CALL confirmLogin('%s','%s');";
		cmd = String.format(cmd, name,pwd);
		try {
			ResultSet rs = conn.doQuery(cmd);
			int id;
			if(!rs.next() || (id = rs.getInt("result")) == 0) {
			  throw new InvalidLoginException();
			}
			return id;
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


	/**
	 * 
	 * @param ID
	 */

	public void logout(int ID) {

	}



}
