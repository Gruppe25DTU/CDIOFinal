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
		String cmd = "CALL getOperator('');";
		cmd = String.format(cmd, ID);

		try {
			ResultSet rs = Connector.doQuery(cmd);
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
			return new UserDTO(opr_ID,username,firstname,lastname,ini,cpr,password,email,roles,active);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	}

	/**
	 * Returns a list over a users roles
	 * @param ID
	 * @return
	 */
	private List<String> getRoles(int ID) {
		List<String> roles = new ArrayList<String>();

		String cmd = "CALL getOperatorRoles('');";
		cmd = String.format(cmd, ID);

		ResultSet rs1;
		try {
			rs1 = Connector.doQuery(cmd);
			while(rs1.next()) {
				roles.add(rs1.getString("role_Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return roles;
	}

	/**
	 * Returns a list over every user with a specific role
	 * @param roleName
	 * @return List < UserDTO >
	 */
	@Override
	public List<UserDTO> getUserWithRole(String roleName) {
		String cmd = "CALL getuserWithRole('');";
		cmd = String.format(cmd, roleName);
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
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
		String cmd = "CALL setActive('','');";
		int status;
		if(active) {
			status = 1;
		}
		else
			status = 0;
		cmd = String.format(cmd, ID, status);

		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Creates an user
	 * User is pr. default active.
	 */
	@Override
	public boolean create(UserDTO dto){
		String addOperator = "CALL addOperator('%d','%d','%s','%s','%s');";
		String addOperatorInfo = "CALL addOperatorInfo('%s','%s','%s','%s')";

		addOperator = String.format(addOperator, dto.getUserID(),dto.getCpr(),dto.getPassword(),dto.getUserName(),dto.getEmail());
		addOperatorInfo = String.format(addOperatorInfo, dto.getFirstName(),dto.getLastName(),dto.getIni(),dto.getCpr());

		try {
			Connector.doUpdate(addOperatorInfo);
			Connector.doUpdate(addOperator);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates a userDTO
	 * @param dto
	 * @return
	 */
	@Override
	public boolean update(UserDTO dto,String old_cpr){
		String updateOperator = "CALL updateOperator('%d','%s','%s','%d','%s');";
		String updateOperatorInfo = "CALL updateOperatorInfo('%s','%s','%s','%s','%s');";


		updateOperator = String.format(updateOperator, dto.getUserID(),dto.getUserName(),dto.getPassword(),dto.getStatus(),dto.getEmail());
		updateOperatorInfo = String.format(updateOperatorInfo, dto.getFirstName(),dto.getLastName(),dto.getIni(),dto.getCpr(),old_cpr);

		try {
			Connector.doUpdate(updateOperatorInfo);
			Connector.doUpdate(updateOperator);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	}

	/**
	 * Checks the database to see if the username is in use.
	 */
	@Override
	public boolean userExists(String name){
		String cmd = "CALL userExists('%s');";
		cmd = String.format(cmd, name);
		try {
			ResultSet rs = Connector.doQuery(cmd);
			int result = rs.getInt("result");
			if(result == 1) {
				return true;
			}
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns a list of the deactivated users <br>
	 * returns null if function failed.
	 */
	@Override
	public List<UserDTO> getDeactiveUsers(){
		String cmd = "CALL getDeactivatedOperatorList();";
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
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
	}

	/**
	 * Returns a list of activated users
	 * @return
	 */
	@Override
	public List<UserDTO> getActivatedUsers() {
		String cmd = "CALL getActivatedOperatorList();";
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
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
	}

	/**
	 * Returns every user regardless of their status
	 */
	@Override
	public List<UserDTO> getUserList(){
		String cmd = "CALL getOperatorList();";
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
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
		
	}

	
	/**
	 * Finds a free userID that is not used. <br>
	 * It's possible to use the ID returned as a new ID.
	 * @return returns 0 if function fails <br>
	 * A number in the interval 1-99999999 if functions succeeds
	 */
	@Override
	public int fintFreeUserID() {
		String cmd = "CALL findFreeUserID();";
		try {
			ResultSet rs = Connector.doQuery(cmd);
			return rs.getInt("max");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}	}
}
