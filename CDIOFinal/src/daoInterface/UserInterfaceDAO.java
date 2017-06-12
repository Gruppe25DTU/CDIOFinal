package daoInterface;

import java.util.List;

import dto.UserDTO;
import logic.CDIOException.DALException;

public interface UserInterfaceDAO {
	UserDTO getUser(int ID);
	List<UserDTO> getUserList();
	List<UserDTO> getDeactiveUsers();
	List<UserDTO> getActivatedUsers();
	boolean changeStatus(int ID, boolean active);
	int create(UserDTO dto) throws DALException;
	boolean update(UserDTO dto, String old_cpr);
	boolean userExists(String name);
	List<UserDTO> getUserWithRole(String roleName);
	int findFreeUserID();
	
}
