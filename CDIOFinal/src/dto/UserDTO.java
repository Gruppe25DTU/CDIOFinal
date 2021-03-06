package dto;

import java.util.ArrayList;
import java.util.List;

public class UserDTO implements IDTO  {

	private String firstName = "";
	private String lastName = "";
	private int userID = -1;
	private String userName = "";
	private String ini = "";
	private String cpr = "";
	private String[] roles;
	

	private String email = "";
	private int status = -1;

	public UserDTO(int userID, String userName, String firstName, String lastName, String ini, String cpr, String email, String[] roles, int status) {
		this.userID = userID;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.ini = ini;
		this.cpr = cpr;
		this.email = email;
		if(roles == null)
			this.roles = new String[0];
		else
			this.roles = roles;
		this.status = status;
	}

	public UserDTO(UserDTO user)
	{
		this.userID = user.getId();
		this.userName = user.getUserName();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.ini = user.getIni();
		this.cpr = user.getCpr();
		this.email = user.getEmail();
		this.roles = user.getRoles();
		this.status = user.getStatus();
	}

	public UserDTO() {}
	
	
	public int getId() {
		return userID;
	}

	public void setId(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIni() {
		return ini;
	}

	public void setIni(String ini) {
		this.ini = ini;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}


	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserDTO [firstName=" + firstName + ", lastName=" + lastName + ", userID=" + userID + ", userName="
				+ userName + ", ini=" + ini + ", cpr=" + cpr + ", roles=" + roles
				+ ", email=" + email + ", status=" + status + "]";
	}

}
