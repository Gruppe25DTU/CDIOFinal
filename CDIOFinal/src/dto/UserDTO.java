package dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 45458645875555L;
	private String firstName = "";
	private String lastName = "";
	private int userID = -1;
	private String userName = "";
	private String ini = "";
	private String cpr = "";
	private String password = "";
	private Set<String> roles = new HashSet<>();
	private String email = "";
	private int status = -1;

	public UserDTO(int userID, String userName, String firstName, String lastName, String ini, String cpr, String password, String email, List<String> roles, int status) {
		this.userID = userID;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
		this.email = email;
		this.roles = new HashSet<>(roles);
		this.status = status;
	}

	public UserDTO(UserDTO user)
	{
		this.userID = user.getUserID();
		this.userName = user.getUserName();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.ini = user.getIni();
		this.cpr = user.getCpr();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.roles = user.getRoles();
		this.status = user.getStatus();
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
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

	

}
