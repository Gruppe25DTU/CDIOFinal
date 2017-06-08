package dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dto01917.OperatoerDTO;

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
	package dto01917;

	/**
	 * Operatoer Data Access Objekt
	 * 
	 * @author mn/tb
	 * @version 1.2
	 */

	public class OperatoerDTO
	{
		/** Operatoer-identifikationsnummer (opr_id) i omraadet 1-99999999. Vaelges af brugerne */
		int oprId;                     
		/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
		String oprNavn;                
		/** Operatoer-initialer min. 2 max. 3 karakterer */
		String ini;                 
		/** Operatoer cpr-nr 10 karakterer */
		String cpr;                 
		/** Operatoer password min. 7 max. 8 karakterer */
		String password;            

		public OperatoerDTO(int oprId, String oprNavn, String ini, String cpr, String password)
		{
			this.oprId = oprId;
			this.oprNavn = oprNavn;
			this.ini = ini;
			this.cpr = cpr;
			this.password = password;
		}
		
	    public OperatoerDTO(OperatoerDTO opr)
	    {
	    	this.oprId = opr.getOprId();
	    	this.oprNavn = opr.getOprNavn();
	    	this.ini = opr.getIni();
	    	this.cpr = opr.getCpr();
	    	this.password = opr.getPassword();
	    }
	    
	    public int getOprId() { return oprId; }
		public void setOprId(int oprId) { this.oprId = oprId; }
		public String getOprNavn() { return oprNavn; }
		public void setOprNavn(String oprNavn) { this.oprNavn = oprNavn; }
		public String getIni() { return ini; }
		public void setIni(String ini) { this.ini = ini; }
		public String getCpr() { return cpr; }
		public void setCpr(String cpr) { this.cpr = cpr; }
		public String getPassword() { return password; }
		public void setPassword(String password) { this.password = password; }
		public String toString() { return "oprID: " + oprId + "\t oprNavn: " + oprNavn + "\t ini:" + ini + "\t cpr: " + cpr + "\t password: " + password; }
	}

}
