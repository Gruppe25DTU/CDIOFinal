package logic;

import dto.UserDTO;
import logic.RuleSetInterface.RuleException;

public class UserDataCheck {
	private RuleSetInterface rules;
	private EmailValidator EmailVal;


	public void CreateUserDC(UserDTO dto) throws RuleException{

		if(rules.getID().test(dto.getId()) == false){
			throw new RuleException("Invalid ID!");
		}

		if(rules.getName().test(dto.getUserName()) == false || dto.getUserName() == null){
			throw new RuleException("Invalid Username!");
		}

		if(rules.getName().test(dto.getFirstName()) == false || dto.getFirstName() == null){
			throw new RuleException("Invalid Firstname!");
		}
		
		if(rules.getName().test(dto.getLastName()) == false || dto.getLastName() == null){
			throw new RuleException("Invalid Lastname!");
		}
		
		if(rules.getCpr().test(dto.getCpr()) == false || dto.getCpr() == null){
			throw new RuleException("Invalid CPR!");
		};

		if(rules.getIni().test(dto.getIni()) == false || dto.getIni() == null ){
			throw new RuleException("Invalid Initiale!");
		};

		dto.setPassword(PasswordGenerator.makePassword(8));	

	}
	
	public void UsernameDC(String name) throws RuleException{
		if(rules.getName().test(name) == false || name == null){
			throw new RuleException("Invalid Username!");
		}
	}
	
	public void FirstNameDC(String name) throws RuleException{
		if(rules.getName().test(name) == false || name == null){
			throw new RuleException("Invalid Fistname!");
		}
	}
	
	public void LastNameDC(String name) throws RuleException{
		if(rules.getName().test(name) == false || name == null){
			throw new RuleException("Invalid Lastname!");
		}
	}
	
	public void CprDC(String cpr) throws RuleException{
		if(rules.getCpr().test(cpr) == false || cpr == null){
			throw new RuleException("Invalid CPR!");
		};
	}

	public void IniDC(String ini) throws RuleException{
		if(rules.getIni().test(ini) == false || ini == null ){
			throw new RuleException("Invalid Initiale!");
		};
	}
	
	public void passDC(String pwd) throws RuleException{
		if(rules.getPwd().test(pwd) == false || pwd == null){
			throw new RuleException("Invalid Password");
		}
	}
	
	public void emailDC(String email) throws RuleException{
		if(EmailVal.validEmail(email) == false){
			throw new RuleException("Invalid Email form!");
			
		}
	}
	public void RoleDC(String role) throws RuleException{
		if(role == null ){
			throw new RuleException("No Role chosen");
		
	}
	}
	
}