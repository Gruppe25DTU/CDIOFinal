package dao;

import java.util.List;

import dto.UserDTO;

public class SessionDAO {
  
  public boolean login(String name, String pwd) {
    return true;
  }
  
  public void logout(int ID) {
    
  }
  
  public UserDTO getUser(int ID) {
    return null;
  }
  
  public List<String> getRoles(int ID) {
    return null;
  }

}
