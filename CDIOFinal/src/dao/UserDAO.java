package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dto.UserDTO;
import logic.CDIOException.*;

public class UserDAO {

  /**
   * Returns a user
   * 
   * @return {@code userDTO}
   * @throws DALException
   */

  public static UserDTO get(Integer ID) throws DALException {
    Connector conn = new Connector();
    String cmd = "CALL getUser('%d');";
    cmd = String.format(cmd, ID);
    UserDTO dto = null;
    try {
      ResultSet rs = conn.doQuery(cmd);
      while (rs.next()) {
        String cpr = rs.getString("cpr");
        int user_ID = rs.getInt("user_ID");
        String username = rs.getString("username");
        int active = rs.getInt("active");
        String email = rs.getString("email");
        String firstname = rs.getString("user_firstname");
        String lastname = rs.getString("user_lastname");
        String ini = rs.getString("ini");
        rs.close();
        String[] roles = getRoles(user_ID);
        dto = new UserDTO(user_ID, username, firstname, lastname, ini, cpr, email, roles, active);
        return dto;
      }
        throw new EmptyResultSetException();
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
   * Returns a list over a users roles
   * 
   * @param ID
   * @return
   */
  private static String[] getRoles(int ID) throws DALException {
    Connector conn = new Connector();
    List<String> roles = new ArrayList<String>();

    String cmd = "CALL getUserRoles('%d');";
    cmd = String.format(cmd, ID);
    ResultSet rs;
    try {
      rs = conn.doQuery(cmd);
      while (rs.next()) {
        String role = rs.getString("role_Name");
        if (role != null) {
          roles.add(role);
        }
      }
      if (roles.isEmpty()) {
        throw new EmptyResultSetException();
      }
      return (String[]) roles.toArray(new String[roles.size()]);
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
   * Returns a list over every user with a specific role
   * 
   * @param roleName
   * @return List < UserDTO >
   * @throws DALException
   */
  public UserDTO[] getUserWithRole(String roleName) throws DALException {
    Connector conn = new Connector();
    String cmd = "CALL getUserWithRole('%s');";
    cmd = String.format(cmd, roleName);
    List<UserDTO> list = new ArrayList<UserDTO>();
    try {
      ResultSet rs = conn.doQuery(cmd);
      while (rs.next()) {
        String cpr = rs.getString("cpr");
        int opr_ID = rs.getInt("user_ID");
        String username = rs.getString("username");
        int active = rs.getInt("active");
        String email = rs.getString("email");
        String firstname = rs.getString("user_firstname");
        String lastname = rs.getString("user_lastname");
        String ini = rs.getString("ini");

        String[] roles = getRoles(opr_ID);
        list.add(new UserDTO(opr_ID, username, firstname, lastname, ini, cpr, email, roles, active));
      }
      if (list.isEmpty()) {
        throw new EmptyResultSetException();
      }
      return (UserDTO[]) list.toArray(new UserDTO[list.size()]);
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
	 * Changes the status of an user: <br>
	 * Function not allowed if function is deactivating the last administrator in the system. <br>
	 * @param ID
	 * @param active
	 * @throws DALException
   */
	public static void changeStatus(int ID, boolean active) throws DALException {
    Connector conn = new Connector();
    String cmd = "CALL setActive('%d','%d');";
    int status;
    if (active) {
      status = 1;
    } else
      status = 0;
    cmd = String.format(cmd, status, ID);

    try {
			ResultSet rs = conn.doQuery(cmd);
			if(rs.getInt("result") == 0) {
				throw new DALException("Deactivation not allowed");
			}
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
   * Creates an user. <br>
   * Database selects ID. <br>
   * User is pr. default active.
   * 
   * @return Function returns userID if successful<br>
   *         Function returns -1 if not
   * 
   */
  public static int create(UserDTO dto, String password) throws DALException {
    Connector conn = new Connector();
    String addUser = "CALL addUser('%s','%s','%s');";
    String addUserInfo = "CALL addUserInfo('%s','%s','%s','%s');";
    String[] roles = dto.getRoles();
    addUser = String.format(addUser, dto.getCpr(), dto.getUserName(), dto.getEmail());
    addUserInfo = String.format(addUserInfo, dto.getFirstName(), dto.getLastName(), dto.getIni(), dto.getCpr());

    try {
      conn.doUpdate(addUserInfo);
      ResultSet rs = conn.doQuery(addUser);
      rs.next();
      int ID = rs.getInt("ID");
      for (int i = 0; i < roles.length; i++) {
        String addUserRoles = "CALL addUserRole('%s','%d');";

        addUserRoles = String.format(addUserRoles, roles[i], ID);
        try {
          conn.doUpdate(addUserRoles);
        } catch (SQLException e) {
          throw new DALException(e);
        }
      }
      passwordDAO.createPassword(dto.getId(), password);
      return ID;
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
   * Updates a userDTO
   * 
   * @param dto
   * @return
   */

  public static boolean update(UserDTO dto, String old_cpr) throws DALException {
    Connector conn = new Connector();
    String updateUser = "CALL updateUser('%d','%s','%d','%s');";
    String updateUserInfo = "CALL updateUserInfo('%s','%s','%s','%s','%s');";
    String deleteExistingRoles = "CALL deleteUserRoles('%d');";
    updateUser = String.format(updateUser, dto.getId(), dto.getUserName(), dto.getStatus(), dto.getEmail());
    updateUserInfo = String.format(updateUserInfo, dto.getFirstName(), dto.getLastName(), dto.getIni(), dto.getCpr(),
        old_cpr);
    deleteExistingRoles = String.format(deleteExistingRoles, dto.getId());

    try {
      conn.doUpdate(updateUserInfo);
      conn.doUpdate(updateUser);
      conn.doUpdate(deleteExistingRoles);
      String[] roles = dto.getRoles();
      int ID = dto.getId();
      for (int i = 0; i < roles.length; i++) {
        String addUserRoles = "CALL addUserRole('%s','%d');";

        addUserRoles = String.format(addUserRoles, roles[i], ID);
        try {
          conn.doUpdate(addUserRoles);
        } catch (SQLException e) {
          throw new DALException(e);
        }
      }
      return true;
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
   * Checks the database to see if the username is in use.
   * 
   * @return returns true if username exists <br>
   *         returns false if username doesn't exists <br>
   *         returns true if ResultSet == null
   */

  public boolean userExists(String name) throws DALException {
    Connector conn = new Connector();
    String cmd = "CALL userExists('%s');";
    cmd = String.format(cmd, name);
    try {
      ResultSet rs = conn.doQuery(cmd);
      if (!rs.next()) {
        return false;
      }
      int result = rs.getInt("result");
      if (result == 1) {
        return true;
      } else
        return false;
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
   * Returns a list of the deactivated users <br>
   * returns null if function failed.
   */

  public UserDTO[] getDeactiveUsers() throws DALException {
    Connector conn = new Connector();
    String cmd = "CALL getDeactivatedUserList();";
    List<UserDTO> list = new ArrayList<UserDTO>();
    try {
      ResultSet rs = conn.doQuery(cmd);
      while (rs.next()) {
        String cpr = rs.getString("cpr");
        int opr_ID = rs.getInt("user_ID");
        String username = rs.getString("username");
        int active = rs.getInt("active");
        String email = rs.getString("email");
        String firstname = rs.getString("user_firstname");
        String lastname = rs.getString("user_lastname");
        String ini = rs.getString("ini");

        String[] roles = getRoles(opr_ID);
        list.add(new UserDTO(opr_ID, username, firstname, lastname, ini, cpr, email, roles, active));

      }
      if (list.isEmpty()) {
        throw new EmptyResultSetException();
      }
      return (UserDTO[]) list.toArray(new UserDTO[list.size()]);
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
   * Returns a list of activated users
   * 
   * @return
   * @throws DALException
   */

  public UserDTO[] getActivatedUsers() throws DALException {
    Connector conn = new Connector();
    String cmd = "CALL getActivatedUserList();";
    List<UserDTO> list = new ArrayList<UserDTO>();
    try {
      ResultSet rs = conn.doQuery(cmd);
      while (rs.next()) {
        String cpr = rs.getString("cpr");
        int opr_ID = rs.getInt("user_ID");
        String username = rs.getString("username");
        int active = rs.getInt("active");
        String email = rs.getString("email");
        String firstname = rs.getString("user_firstname");
        String lastname = rs.getString("user_lastname");
        String ini = rs.getString("ini");

        String[] roles = getRoles(opr_ID);
        list.add(new UserDTO(opr_ID, username, firstname, lastname, ini, cpr, email, roles, active));

      }
      if (list.isEmpty()) {
        throw new EmptyResultSetException();
      }
      return (UserDTO[]) list.toArray(new UserDTO[list.size()]);
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
   * Returns every user regardless of their status
   * 
   * @return {@code List<UserDTO>}
   * @throws DALException
   */

  public static UserDTO[] getList() throws DALException {
    Connector conn = new Connector();
    String cmd = "CALL getUserList();";
    List<UserDTO> list = new ArrayList<UserDTO>();
    try {
      ResultSet rs = conn.doQuery(cmd);
      while (rs.next()) {
        String cpr = rs.getString("cpr");
        int opr_ID = rs.getInt("user_ID");
        String username = rs.getString("username");
        int active = rs.getInt("active");
        String email = rs.getString("email");
        String firstname = rs.getString("user_firstname");
        String lastname = rs.getString("user_lastname");
        String ini = rs.getString("ini");

        String[] roles = getRoles(opr_ID);
        list.add(new UserDTO(opr_ID, username, firstname, lastname, ini, cpr, email, roles, active));

      }
      if (list.isEmpty()) {
        throw new EmptyResultSetException();
      }
      return (UserDTO[]) list.toArray(new UserDTO[list.size()]);
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

  public static UserDTO getUserFromUserName(String uName) throws DALException {
    Connector conn = new Connector();
    String cmd = "CALL getUserFromUserName('%s');";
    cmd = String.format(cmd, uName);

    try {
      ResultSet rs = conn.doQuery(cmd);
      while (rs.next()) {
        String cpr = rs.getString("cpr");
        int opr_ID = rs.getInt("user_ID");
        String username = rs.getString("username");
        int active = rs.getInt("active");
        String email = rs.getString("email");
        String firstname = rs.getString("user_firstname");
        String lastname = rs.getString("user_lastname");
        String ini = rs.getString("ini");
        rs.close();
        String[] roles = getRoles(opr_ID);
        return new UserDTO(opr_ID, username, firstname, lastname, ini, cpr, email, roles, active);
      }
    } catch (SQLException e) {
      throw new DALException(e);
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new DALException(e);
      }
    }
    throw new DALException("Unexpected Error");
  }
}
