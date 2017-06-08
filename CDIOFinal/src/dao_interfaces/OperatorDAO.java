package dao_interfaces;

import java.util.List;

import dto.UserDTO;

public interface OperatorDAO {
	UserDTO get(int ID);
	boolean changeStatus(int ID, boolean active);
	boolean create(UserDTO dto);
	boolean userExists(String name);
	List<UserDTO> getDeactivatedUsers();
	List<UserDTO> getActiveUsers();
	List<UserDTO> getUsers();

}
