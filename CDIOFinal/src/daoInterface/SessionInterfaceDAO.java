package daoInterface;

import java.util.List;

import dto.UserDTO;

public interface SessionInterfaceDAO {
	void logout(int ID);
	boolean login(String name, String pwd);
	UserDTO getUser(int ID);
	List<String> getRoles(int ID);
}
