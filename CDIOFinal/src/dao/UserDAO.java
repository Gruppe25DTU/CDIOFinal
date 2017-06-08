package dao;

import java.util.List;

import dto.UserDTO;

public class UserDAO {

	public UserDTO getUser(int ID){
		return null;
	}

	public boolean changeStatus(int ID, boolean active){
		return true;
	}

	public boolean create(UserDTO dto){
		return true;
	}

	public boolean update(UserDTO dto){
		return true;
	}

	public boolean userExists(String name){
		return true;
	}

	public List<UserDTO> getDeactiveUsers(){
		return null;
	}
	
	public List<UserDTO> getUserList(){
		return null;
	}
}
