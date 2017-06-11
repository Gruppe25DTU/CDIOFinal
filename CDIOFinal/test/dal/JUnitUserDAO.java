package dal;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.UserDAO;
import daoInterface.UserInterfaceDAO;
import dto.UserDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitUserDAO {

	/**
	 * Before a user have been created
	 */
	@Test
	public void t1FindFreeUserIDBefore() {
		UserInterfaceDAO userDAO = new UserDAO();
		int freeID = userDAO.findFreeUserID();
		int expectedID = 1;
		assertEquals(freeID,expectedID);
		
	}
	@Test
	public void t2CreateUserAndGetUser() {
		Connector.changeTestMode(true);
		UserInterfaceDAO userDAO = new UserDAO();
		List<String> roles = new ArrayList<String>();
		roles.add("Administrator");
		UserDTO actualUser = new UserDTO(1,"Username1","firstname1","lastname1","ini1","0000000001","password1","email1",roles,1);
		userDAO.create(actualUser);
		UserDTO expectedUser = userDAO.getUser(1);

		equal(actualUser,expectedUser);
		
	}

	
	@Test
	public void t3UpdateUser() {
		List<String> roles = new ArrayList<String>();
		roles.add("Foreman");
		UserDTO actualUser = new UserDTO(1,"Username2","firstname2","lastname2","ini2","1111111111","password2","email2",roles,1);
		Connector.changeTestMode(true);
		UserInterfaceDAO userDAO = new UserDAO();
		userDAO.update(actualUser,"0000000001");
		UserDTO expectedUser = userDAO.getUser(1);

		equal(actualUser,expectedUser);

	}


	@Test
	public void t4setStatus() {
		UserInterfaceDAO userDAO = new UserDAO();
		List<String> roles = new ArrayList<String>();
		roles.add("Foreman");
		UserDTO actualUser = new UserDTO(1,"Username2","firstname2","lastname2","ini2","1111111111","password2","email2",roles,1);

		userDAO.changeStatus(actualUser.getId(), false);
		UserDTO dto1 = userDAO.getUser(1);
		
		assertEquals(dto1.getStatus(),0);
		
		userDAO.changeStatus(actualUser.getId(), true);
		UserDTO dto2 = userDAO.getUser(1);
		
		assertEquals(dto2.getStatus(),1);
	}
	/**
	 * After a user have been created
	 */
	@Test
	public void t5FindFreeUserIDAfter() {
		UserInterfaceDAO userDAO = new UserDAO();
		int freeID = userDAO.findFreeUserID();
		int expectedID = 2;
		assertEquals(freeID,expectedID);
		
	}
	
	@Test
	public void t6GetActivatedUsersList() {
		UserInterfaceDAO userDAO = new UserDAO();
		
		List<String> roles = new ArrayList<String>();
		roles.add("Foreman");
		UserDTO activatedUser = new UserDTO(2,"Username2","firstname2","lastname2","ini2","0000000002","password2","email2",roles,1);
		UserDTO deactivatedUser = new UserDTO(3,"Username3","firstname3","lastname3","ini2","00000000003","password3","email3",roles,0);
		UserDTO existingUser = new UserDTO(1,"Username2","firstname2","lastname2","ini2","1111111111","password2","email2",roles,1);

		userDAO.create(activatedUser);
		userDAO.create(deactivatedUser);
		userDAO.changeStatus(deactivatedUser.getId(), false);
		
		List<UserDTO> actual = new ArrayList<>();
		actual.add(existingUser);
		actual.add(activatedUser);
		List<UserDTO> expected = userDAO.getActivatedUsers();
		
		for(int i = 0;i<expected.size();i++) {
			equal(actual.get(i),expected.get(i));
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Compares 2 DTO's
	 * @param dto1
	 * @param dto2
	 */
	public void equal(UserDTO dto1, UserDTO dto2) {
		assertEquals(dto1.getCpr(),dto2.getCpr());
		assertEquals(dto1.getEmail(),dto2.getEmail());
		assertEquals(dto1.getFirstName(),dto2.getFirstName());
		assertEquals(dto1.getIni(),dto2.getIni());
		assertEquals(dto1.getLastName(),dto2.getLastName());
		assertEquals(dto1.getPassword(),dto2.getPassword());
		assertEquals(dto1.getRoles(),dto2.getRoles());
		assertEquals(dto1.getStatus(),dto2.getStatus());
		assertEquals(dto1.getId(),dto2.getId());
		assertEquals(dto1.getUserName(),dto2.getUserName());
	}
	
	

}
