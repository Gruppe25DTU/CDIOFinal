package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




public class Connector {

	private static boolean testMode = false;



	/**
	 * Used to create a connection to the database. <br> Changes between the test database <br>and normal database based on the boolean testmode
	 * @return
	 * Connection to database
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection connectToDatabase()
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException
	{
		// call the driver class' no argument constructor
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		// get Connection-object via DriverManager
		if(!testMode) {
			String url = "jdbc:mysql://"+Constant.server+":"+Constant.port+"/"+Constant.database;
			String username = Constant.username;
			String password = Constant.password;

			return (Connection) DriverManager.getConnection(url, username, password);
		}
		else {
			String url = "jdbc:mysql://"+Constant.testserver+":"+Constant.testport+"/"+Constant.testdatabase;
			String username = Constant.testusername;
			String password = Constant.testpassword;
			return (Connection) DriverManager.getConnection(url,username,password);
		}
	}

	/**
	 * Changes the testmode <br>
	 * if true the system will connect to the testdatabase <br>
	 * if false the system will connect to the normal database<br>
	 * Default: false	
	 * @param testmode
	 */
	public static void changeTestMode(boolean testmode) {
		testMode = testmode;
	}

	/**
	 * Executes the given SQL statement, which returns a single ResultSet object.
	 * @param cmd
	 * @return
	 * ResultSet
	 * @throws DALException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ResultSet doQuery(String cmd) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Connection conn = connectToDatabase();
		Statement stm = conn.createStatement();
		try { return stm.executeQuery(cmd); }
		catch (SQLException e) { throw new DALException(e); }
		finally {
			stm.close();
			conn.close();
		}
		
	}
	/**
	 * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement or an SQL statement that returns nothing, such as an SQL DDL statement. 
	 * @param cmd
	 * @return
	 * either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
	 * @throws DALException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int doUpdate(String cmd) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Connection conn = connectToDatabase();
		Statement stm = conn.createStatement();
		try { return stm.executeUpdate(cmd); }
		catch (SQLException e) { throw new DALException(e); }
		finally {
			stm.close();
			conn.close();

		}
	}

}
