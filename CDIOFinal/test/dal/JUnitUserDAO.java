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
import logic.CDIOException.DALException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitUserDAO {

	
	@Test
	public void t2CreateUserAndGetUser() throws DALException{
		Connector.changeTestMode(true);
		UserDAO userDAO = new UserDAO();
		List<String> roles = new ArrayList<String>();
		roles.add("Administrator");
		UserDTO actualUser = new UserDTO(1,"Username1","firstname1","lastname1","ini1","0000000001","password1","email1",roles,1);
		userDAO.create(actualUser);
		UserDTO expectedUser = userDAO.get(1);

		equal(actualUser,expectedUser);
		
	}

	
	@Test
	public void t3UpdateUser() throws DALException{
		List<String> roles = new ArrayList<String>();
		roles.add("Foreman");
		UserDTO actualUser = new UserDTO(1,"Username2","firstname2","lastname2","ini2","1111111111","password2","email2",roles,1);
		Connector.changeTestMode(true);
		UserDAO userDAO = new UserDAO();
		userDAO.update(actualUser,"0000000001");
		UserDTO expectedUser = userDAO.get(1);

		equal(actualUser,expectedUser);

	}


	@Test
	public void t4setStatus() throws DALException{
		UserDAO userDAO = new UserDAO();
		List<String> roles = new ArrayList<String>();
		roles.add("Foreman");
		UserDTO actualUser = new UserDTO(1,"Username2","firstname2","lastname2","ini2","1111111111","password2","email2",roles,1);

		userDAO.changeStatus(actualUser.getId(), false);
		UserDTO dto1 = userDAO.get(1);
		
		assertEquals(dto1.getStatus(),0);
		
		userDAO.changeStatus(actualUser.getId(), true);
		UserDTO dto2 = userDAO.get(1);
		
		assertEquals(dto2.getStatus(),1);
	}
	
	
	@Test
	public void t6GetActivatedUsersList() throws DALException {
		UserDAO userDAO = new UserDAO();
		
		List<String> roles = new ArrayList<String>();
		roles.add("Foreman");
		int id1 = userDAO.findFreeUserID();
		UserDTO activatedUser = new UserDTO(2,"Username" +id1,"firstname"+id1,"lastname"+id1,"ini"+id1,"000000000"+id1,"password"+id1,"email+id1",roles,1);
		int id2 = userDAO.findFreeUserID();
		UserDTO deactivatedUser = new UserDTO(id2,"Username"+id2,"firstname"+id2,"lastname"+id2,"ini"+id2,"0000000000"+id2,"password"+id2,"email+id2",roles,0);
		
		userDAO.create(activatedUser);
		userDAO.create(deactivatedUser);
		userDAO.changeStatus(deactivatedUser.getId(), false);
		
		List<UserDTO> actual = new ArrayList<>();
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
