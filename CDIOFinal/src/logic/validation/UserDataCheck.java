package logic.validation;

import dao.UserDAO;
import dto.UserDTO;
import logic.CDIOException.DALException;
import logic.CDIOException.DTOException;
import logic.CDIOException.InvalidDataException;
import logic.CDIOException.UnauthorizedException;
import logic.CDIOException.UnexpectedDataException;
import logic.PasswordGenerator;
import logic.validation.RuleSetInterface.RuleException;

public class UserDataCheck {
	private static RuleSetInterface rules = new RuleSet();
	private static EmailValidator EmailVal = new EmailValidator();


	public static int create(UserDTO dto) throws RuleException, DALException{

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
		return UserDAO.create(dto);

	}
	
	public static void userName(String name) throws RuleException{
		if(rules.getName().test(name) == false || name == null){
			throw new RuleException("Invalid Username!");
		}
	}
	
	public static void firstName(String name) throws RuleException{
		if(rules.getName().test(name) == false || name == null){
			throw new RuleException("Invalid Fistname!");
		}
	}
	
	public static void lastName(String name) throws RuleException{
		if(rules.getName().test(name) == false || name == null){
			throw new RuleException("Invalid Lastname!");
		}
	}
	
	public static void cpr(String cpr) throws RuleException{
		if(rules.getCpr().test(cpr) == false || cpr == null){
			throw new RuleException("Invalid CPR!");
		}
	}

	public static void ini(String ini) throws RuleException{
		if(rules.getIni().test(ini) == false || ini == null ){
			throw new RuleException("Invalid Initiale!");
		}
	}
	
	public static void password(String pwd) throws RuleException{
		if(rules.getPwd().test(pwd) == false || pwd == null){
			throw new RuleException("Invalid Password");
		}
	}
	
	public static void email(String email) throws RuleException{
		if(EmailVal.validEmail(email) == false){
			throw new RuleException("Invalid Email form!");
		}
	}
	
	public static void roles(String role) throws RuleException{
		if(role == null ){
			throw new RuleException("No Role chosen");
		}
	}

}