package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import daoInterface.UserInterfaceDAO;
import dto.UserDTO;

public class UserDAO implements UserInterfaceDAO{

	/**
	 * Returns a user
	 * @return userDTO 
	 */
	@Override
	public UserDTO getUser(int ID){
		String cmd = "CALL getUser('%d');";
		cmd = String.format(cmd, ID);
		UserDTO dto;
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while(rs.next()) {
				String cpr = rs.getString("cpr");
				int opr_ID = rs.getInt("user_ID");
				String username = rs.getString("username");
				String password = rs.getString("password");
				int active = rs.getInt("active");
				String email = rs.getString("email");
				String firstname = rs.getString("user_firstname");
				String lastname = rs.getString("user_lastname");
				String ini = rs.getString("ini");
				rs.close();
				List<String> roles = getRoles(opr_ID);
				dto = new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,password,email,roles,active);
				return dto;
			}
			return null;
		} catch (SQLException e) {	
			e.printStackTrace();
			return null;
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
	private static List<String> getRoles(int ID) {
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
			e.printStackTrace();
			return null;
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
	 */
	@Override
	public List<UserDTO> getUserWithRole(String roleName) {
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
				int opr_ID = rs.getInt("opr_ID");
				String username = rs.getString("username");
				String password = rs.getString("password");
				int active = rs.getInt("active");
				String email = rs.getString("email");
				String firstname = rs.getString("opr_firstname");
				String lastname = rs.getString("opr_lastname");
				String ini = rs.getString("ini");

				List<String> roles = getRoles(opr_ID);
				list.add(new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,password,email,roles,active));

			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
	@Override
	public boolean changeStatus(int ID, boolean active){
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
	 * Creates an user
	 * User is pr. default active.
	 */
	@Override
	public boolean create(UserDTO dto){
		String addUser = "CALL addUser('%d','%s','%s','%s','%s');";
		String addUserInfo = "CALL addUserInfo('%s','%s','%s','%s');";
		String addUserRoles = "CALL addUserRole('%s','%d');";
		List<String> roles = dto.getRoles();

		addUser = String.format(addUser, dto.getId(),dto.getCpr(),dto.getPassword(),dto.getUserName(),dto.getEmail());
		addUserInfo = String.format(addUserInfo, dto.getFirstName(),dto.getLastName(),dto.getIni(),dto.getCpr());


		try {
			int result1 = Connector.doUpdate(addUserInfo);
			int result2 = Connector.doUpdate(addUser);
			for(int i = 0;i<roles.size();i++) {
				addUserRoles = String.format(addUserRoles, roles.get(i),dto.getId());
				try {
					Connector.doUpdate(addUserRoles);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(result1 == 0 && result2 == 0) {
				return false;
			}
			return true;
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
	 * Updates a userDTO
	 * @param dto
	 * @return
	 */
	@Override
	public boolean update(UserDTO dto,String old_cpr){
		String updateUser = "CALL updateUser('%d','%s','%s','%d','%s');";
		String updateUserInfo = "CALL updateUserInfo('%s','%s','%s','%s','%s');";
		String deleteExistingRoles = "CALL deleteUserRoles('%d');";
		String addUserRoles = "CALL addUserRole('%s','%d');";
		updateUser = String.format(updateUser, dto.getId(),dto.getUserName(),dto.getPassword(),dto.getStatus(),dto.getEmail());
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
					e.printStackTrace();
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	}

	/**
	 * Checks the database to see if the username is in use.
	 * @return
	 * returns true if username exists <br>
	 * returns false if username doesn't exists <br>
	 * returns true if ResultSet == null
	 */
	@Override
	public boolean userExists(String name){
		String cmd = "CALL userExists('%s');";
		cmd = String.format(cmd, name);
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return true;
			}
			rs.next();

			int result = rs.getInt("result");
			if(result == 1) {
				return true;
			}
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
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
	@Override
	public List<UserDTO> getDeactiveUsers(){
		String cmd = "CALL getDeactivatedUserList();";
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while (rs.next()) 
			{
				String cpr = rs.getString("cpr");
				int opr_ID = rs.getInt("opr_ID");
				String username = rs.getString("username");
				String password = rs.getString("password");
				int active = rs.getInt("active");
				String email = rs.getString("email");
				String firstname = rs.getString("opr_firstname");
				String lastname = rs.getString("opr_lastname");
				String ini = rs.getString("ini");

				List<String> roles = getRoles(opr_ID);
				list.add(new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,password,email,roles,active));

			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
	 */
	@Override
	public List<UserDTO> getActivatedUsers() {
		String cmd = "CALL getActivatedUserList();";
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while (rs.next()) 
			{
				String cpr = rs.getString("cpr");
				int opr_ID = rs.getInt("opr_ID");
				String username = rs.getString("username");
				String password = rs.getString("password");
				int active = rs.getInt("active");
				String email = rs.getString("email");
				String firstname = rs.getString("opr_firstname");
				String lastname = rs.getString("opr_lastname");
				String ini = rs.getString("ini");

				List<String> roles = getRoles(opr_ID);
				list.add(new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,password,email,roles,active));

			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
	 */
	@Override
	public List<UserDTO> getUserList(){
		String cmd = "CALL getUserList();";
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while (rs.next()) 
			{
				String cpr = rs.getString("cpr");
				int opr_ID = rs.getInt("opr_ID");
				String username = rs.getString("username");
				String password = rs.getString("password");
				int active = rs.getInt("active");
				String email = rs.getString("email");
				String firstname = rs.getString("opr_firstname");
				String lastname = rs.getString("opr_lastname");
				String ini = rs.getString("ini");

				List<String> roles = getRoles(opr_ID);
				list.add(new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,password,email,roles,active));

			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
	 * Finds a free userID that is not used. <br>
	 * It's possible to use the ID returned as a new ID.
	 * @return returns 0 if function fails <br>
	 * A number in the interval 1-99999999 if functions succeeds
	 */
	@Override
	public int findFreeUserID() {
		String cmd = "CALL findFreeUserID();";
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return 0;
			}
			int result = 0;
			while(rs.next())  {

				result = Integer.parseInt((rs.getString("max")));
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}	
		finally {
			try {
				Connector.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}
}
