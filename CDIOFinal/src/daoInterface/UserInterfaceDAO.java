package daoInterface;

import java.util.List;

import dto.UserDTO;

public interface UserInterfaceDAO {
	UserDTO getUser(int ID);
	List<UserDTO> getUserList();
	List<UserDTO> getDeactiveUsers();
	List<UserDTO> getActivatedUsers();
	boolean changeStatus(int ID, boolean active);
	boolean create(UserDTO dto);
	boolean update(UserDTO dto, String old_cpr);
	boolean userExists(String name);
	List<UserDTO> getUserWithRole(String roleName);
	int fintFreeUserID();
	
}
