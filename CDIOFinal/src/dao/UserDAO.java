package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dto.UserDTO;
import logic.CDIOException.DALException;

public class UserDAO {

	/**
	 * Returns a user
	 * @return {@code userDTO} 
	 * @throws DALException 
	 */

	public static UserDTO get(Integer ID) throws DALException{
		String cmd = "CALL getUser('%d');";
		cmd = String.format(cmd, ID);
		UserDTO dto = null;
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while(rs.next()) {
				String cpr = rs.getString("cpr");
				int opr_ID = rs.getInt("user_ID");
				String username = rs.getString("username");
				int active = rs.getInt("active");
				String email = rs.getString("email");
				String firstname = rs.getString("user_firstname");
				String lastname = rs.getString("user_lastname");
				String ini = rs.getString("ini");
				rs.close();
				List<String> roles = getRoles(opr_ID);
				dto = new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,email,roles,active);
				return dto;
			}
			return null;
		} catch (SQLException e) {	
			throw new DALException(e);
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
	 * Returns a list over a users roles
	 * @param ID
	 * @return
	 */
	private static List<String> getRoles(int ID) throws DALException{
		List<String> roles = new ArrayList<String>();

		String cmd = "CALL getUserRoles('%d');";
		cmd = String.format(cmd, ID);
		ResultSet rs;
		try {
			rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while(rs.next()) {
				roles.add(rs.getString("role_Name"));
			}
			return roles;
		} catch (SQLException e) {
			throw new DALException(e);
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
	 * Returns a list over every user with a specific role
	 * @param roleName
	 * @return List < UserDTO >
	 * @throws DALException 
	 */
	public List<UserDTO> getUserWithRole(String roleName) throws DALException {
		String cmd = "CALL getUserWithRole('%s');";
		cmd = String.format(cmd, roleName);
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while (rs.next()) 
			{
				String cpr = rs.getString("cpr");
				int opr_ID = rs.getInt("user_ID");
				String username = rs.getString("username");
				int active = rs.getInt("active");
				String email = rs.getString("email");
				String firstname = rs.getString("user_firstname");
				String lastname = rs.getString("user_lastname");
				String ini = rs.getString("ini");

				List<String> roles = getRoles(opr_ID);
				list.add(new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,email,roles,active));

			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
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
	 * Changes a status of an user <br>
	 * false if user is inactive<br>
	 * true if user in active
	 * 
	 * 
	 */

	public static boolean changeStatus(int ID, boolean active) throws DALException{
		String cmd = "CALL setActive('%d','%d');";
		int status;
		if(active) {
			status = 1;
		}
		else
			status = 0;
		cmd = String.format(cmd, status, ID);

		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
			throw new DALException(e);
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
	 * Creates an user. <br>
	 * Database selects ID. <br>
	 * User is pr. default active.
	 * @return Function returns userID if successful<br>
	 * Function returns -1 if not
	 * 
	 */
	public static int create(UserDTO dto, String password) throws DALException{
		String addUser = "CALL addUser('%s','%s','%s');";
		String addUserInfo = "CALL addUserInfo('%s','%s','%s','%s');";
		List<String> roles = dto.getRoles();
		addUser = String.format(addUser,dto.getCpr(),dto.getUserName(),dto.getEmail());
		addUserInfo = String.format(addUserInfo, dto.getFirstName(),dto.getLastName(),dto.getIni(),dto.getCpr());


		try {
			Connector.doUpdate(addUserInfo);
			ResultSet rs = Connector.doQuery(addUser);
			rs.next();
			int ID = rs.getInt("ID");
			for(int i = 0;i<roles.size();i++) {
				String addUserRoles = "CALL addUserRole('%s','%d');";

				addUserRoles = String.format(addUserRoles, roles.get(i),ID);
				try {
					Connector.doUpdate(addUserRoles);
				} catch (SQLException e) {
					throw new DALException(e);

				}
			}
			passwordDAO.createPassword(dto.getId(), password);
			return ID;
		} catch (SQLException e) {
			throw new DALException(e);
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
	 * Updates a userDTO
	 * @param dto
	 * @return
	 */

	public static boolean update(UserDTO dto,String old_cpr) throws DALException{
		String updateUser = "CALL updateUser('%d','%s','%d','%s');";
		String updateUserInfo = "CALL updateUserInfo('%s','%s','%s','%s','%s');";
		String deleteExistingRoles = "CALL deleteUserRoles('%d');";
		String addUserRoles = "CALL addUserRole('%s','%d');";
		updateUser = String.format(updateUser, dto.getId(),dto.getUserName(),dto.getStatus(),dto.getEmail());
		updateUserInfo = String.format(updateUserInfo, dto.getFirstName(),dto.getLastName(),dto.getIni(),dto.getCpr(),old_cpr);
		deleteExistingRoles = String.format(deleteExistingRoles, dto.getId());


		try {
			Connector.doUpdate(updateUserInfo);
			Connector.doUpdate(updateUser);
			Connector.doUpdate(deleteExistingRoles);
			for(int i = 0;i<dto.getRoles().size();i++) {
				addUserRoles = String.format(addUserRoles, dto.getRoles().get(i),dto.getId());
				try {
					Connector.doUpdate(addUserRoles);
				} catch (SQLException e) {
					throw new DALException();
				}
			}
			return true;
		} catch (SQLException e) {
			throw new DALException(e);
		}	}

	/**
	 * Checks the database to see if the username is in use.
	 * @return
	 * returns true if username exists <br>
	 * returns false if username doesn't exists <br>
	 * returns true if ResultSet == null
	 */

	public boolean userExists(String name) throws DALException{
		String cmd = "CALL userExists('%s');";
		cmd = String.format(cmd, name);
		try {
			ResultSet rs = Connector.doQuery(cmd);
			rs.next();

			int result = rs.getInt("result");
			if(result == 1) {
				return true;
			}
			else
				return false;
		} catch (SQLException e) {
			throw new DALException(e);
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
	 * Returns a list of the deactivated users <br>
	 * returns null if function failed.
	 */

	public List<UserDTO> getDeactiveUsers() throws DALException {
		String cmd = "CALL getDeactivatedUserList();";
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null)
				return list;
			while (rs.next()) 
			{
				String cpr = rs.getString("cpr");
				int opr_ID = rs.getInt("user_ID");
				String username = rs.getString("username");
				int active = rs.getInt("active");
				String email = rs.getString("email");
				String firstname = rs.getString("user_firstname");
				String lastname = rs.getString("user_lastname");
				String ini = rs.getString("ini");

				List<String> roles = getRoles(opr_ID);
				list.add(new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,email,roles,active));

			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
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
	 * Returns a list of activated users
	 * @return
	 * @throws DALException 
	 */

	public List<UserDTO> getActivatedUsers() throws DALException {
		String cmd = "CALL getActivatedUserList();";
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return list;
			}
			while (rs.next()) 
			{
				String cpr = rs.getString("cpr");
				int opr_ID = rs.getInt("user_ID");
				String username = rs.getString("username");
				int active = rs.getInt("active");
				String email = rs.getString("email");
				String firstname = rs.getString("user_firstname");
				String lastname = rs.getString("user_lastname");
				String ini = rs.getString("ini");

				List<String> roles = getRoles(opr_ID);
				list.add(new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,email,roles,active));

			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
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
	 * Returns every user regardless of their status
	 * @return {@code List<UserDTO>} 
	 * @throws DALException 
	 */

	public static List<UserDTO> getList() throws DALException{
		String cmd = "CALL getUserList();";
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return list;
			}
			while (rs.next()) 
			{
				String cpr = rs.getString("cpr");
				int opr_ID = rs.getInt("user_ID");
				String username = rs.getString("username");
				int active = rs.getInt("active");
				String email = rs.getString("email");
				String firstname = rs.getString("user_firstname");
				String lastname = rs.getString("user_lastname");
				String ini = rs.getString("ini");

				List<String> roles = getRoles(opr_ID);
				list.add(new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,email,roles,active));

			}
			return list;
		} catch (SQLException e) {
			throw new DALException(e);
		}	
		finally {
			try {
				Connector.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}

	public static UserDTO getUserFromUserName(String uName) throws DALException{
		String cmd = "CALL getUserFromUserName('%s');";
		cmd = String.format(cmd,uName);

		try {
			ResultSet rs = Connector.doQuery(cmd);
			while(rs.next()) {
				String cpr = rs.getString("cpr");
				int opr_ID = rs.getInt("user_ID");
				String username = rs.getString("username");
				int active = rs.getInt("active");
				String email = rs.getString("email");
				String firstname = rs.getString("user_firstname");
				String lastname = rs.getString("user_lastname");
				String ini = rs.getString("ini");
				rs.close();
				List<String> roles = getRoles(opr_ID);
				return new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,email,roles,active);
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		finally {
			try {
				Connector.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return null;

	}



}
