package daoInterface;

import java.util.List;

import dto.UserDTO;

public interface UserInterfaceDAO {
	UserDTO getUser(int ID);
	List<UserDTO> getUserList();
	List<UserDTO> getDeactiveUsers();
	boolean changeStatus(int ID, boolean active);
	boolean create(UserDTO dto);
	boolean update(UserDTO dto);
	boolean userExists(String name);
}
