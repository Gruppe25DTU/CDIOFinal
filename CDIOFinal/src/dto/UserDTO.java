package dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = 45458645875555L;
    private int userID = -1;
    private String userName = "";
    private String ini = "";
    private String cpr = "";
    private String password = "";
    private Set<String> roles = new HashSet<>();
    private String email = "";
    private int status = -1;

    public UserDTO(int userID, String userName, String ini, String cpr, String password, String email, List<String> roles, int status) {
        this.userID = userID;
        this.userName = userName;
        this.ini = ini;
        this.cpr = cpr;
        this.password = password;
        this.email = email;
        this.roles = new HashSet<>(roles);
        this.status = status;
    }

	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}
}
