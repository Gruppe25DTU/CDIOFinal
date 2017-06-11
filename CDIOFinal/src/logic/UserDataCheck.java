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

		if(rules.getName().test(dto.getUserName()) == false){
			throw new RuleException("Invalid Username!");
		}

		if(rules.getCpr().test(dto.getCpr()) == false){
			throw new RuleException("Invalid CPR!");
		};

		if(rules.getIni().test(dto.getCpr()) == false ){
			throw new RuleException("Invalid Initiale!");
		};

		dto.setPassword(PasswordGenerator.makePassword(8));	

	}
	
	public void UsernameDC(String name) throws RuleException{
		if(rules.getName().test(name) == false || name == ""){
			throw new RuleException("Invalid Username!");
		}
	}
	
	public void CprDC(String cpr) throws RuleException{
		if(rules.getCpr().test(cpr) == false || cpr == ""){
			throw new RuleException("Invalid CPR!");
		};
	}

	public void IniDC(String ini) throws RuleException{
		if(rules.getIni().test(ini) == false || ini == "" ){
			throw new RuleException("Invalid Initiale!");
		};
	}
	
	public void passDC(String pwd) throws RuleException{
		if(rules.getPwd().test(pwd) == false || pwd == ""){
			throw new RuleException("Invalid Password");
		}
	}
	
	public void emailDC(String email) throws RuleException{
		if(EmailVal.validEmail(email) == false){
			throw new RuleException("Invalid Email form!");
			
		}
	}
	
}