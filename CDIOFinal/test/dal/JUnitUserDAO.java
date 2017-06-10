package dal;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dao.UserDAO;
import daoInterface.UserInterfaceDAO;
import dto.UserDTO;
 
public class JUnitUserDAO {

	@Test
	public void testCreateUser() {
		Connector.changeTestMode(true);
		UserInterfaceDAO userDAO = new UserDAO();
		List<String> roles = new ArrayList<String>();
		roles.add("Administrator");
		UserDTO actualUser = new UserDTO(1,"Username1","firstname1","lastname1","ini1","0000000001","password1","email1",roles,1);
		userDAO.create(actualUser);
		UserDTO expectedUser = userDAO.getUser(1);
		
	
		assertEquals(actualUser.getCpr(),expectedUser.getCpr());
		assertEquals(actualUser.getEmail(),expectedUser.getEmail());
		assertEquals(actualUser.getFirstName(),expectedUser.getFirstName());
		assertEquals(actualUser.getIni(),expectedUser.getIni());
		assertEquals(actualUser.getLastName(),expectedUser.getLastName());
		assertEquals(actualUser.getPassword(),expectedUser.getPassword());
		assertEquals(actualUser.getRoles(),expectedUser.getRoles());
		assertEquals(actualUser.getStatus(),expectedUser.getStatus());
		assertEquals(actualUser.getUserID(),expectedUser.getUserID());
		assertEquals(actualUser.getUserName(),expectedUser.getUserName());


		
	}

}
