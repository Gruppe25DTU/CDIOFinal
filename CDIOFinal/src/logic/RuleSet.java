package logic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class RuleSet implements RuleSetInterface {

	private static final String ID_String = "id";
	private static final String Name_String = "name";
	private static final String Ini_String = "Initials";
//	private static final String Roles_String = "roles";
	private static final String Cpr_String = "cpr";
	private static final String Pwd_String = "pwd";
	private static final String Status_String = "Status";
	private static final String NonNetto_String = "Nom Netto Weight";
	private static final String Tolerance_String = "Tolerance";
	
    @SuppressWarnings("rawtypes")
	Map<String, Rule> ruleList = new HashMap<>();


	public RuleSet() throws RuleException{

		//Rules for all ID's
		Rule<Integer> idRule = new Rule<>
		("ID must be between " + minID + " and " + maxID, t -> t > minID && t < maxID);

		//Rules for all Names
		Rule<String> nameRule = new Rule<>
		("Name must be between " + minName + " and " + maxName + " characters"
				, t -> t.length() >= minName && t.length() <= maxName);

		//Initials Rule
		Rule<String> iniRule = new Rule<>
		("Initials must be between " + minIni + " and " + maxIni + " characters"
				, t -> t.length() >= minIni && t.length() <= maxIni);

		//Cpr Rule
		Rule<String> cprRule = new Rule<>
		("CPR must be entered as 'xxxxxx-yyyy' or 'xxxxxxyyyy'"
				, t -> Pattern.matches("[0-9]{6}-?[0-9]{4}", t));
		
		//Status Rule
		Rule<Integer> statusRule = new Rule<>
		("Stauts must be either " + minStatus + "If the process isn't started yet. "
				+ midStatus +"If the process is started. Or " 
				+ maxStatus + "If the process is finished.", t -> t > minStatus && t <= maxStatus);

		//Nom Netto Weight Rule
		Rule<Double> nomNettoRule = new Rule<>
		("Nom Netto Weight must be between " + minNomNetto + " and " + maxNomNetto,
				t -> t > minNomNetto && t < maxNomNetto);

		//Tolerance Rule
		Rule<Double> toleranceRule = new Rule<>
		("Tolerance must be between " + minTolerance + " and " + maxTolerance,
				t -> t > minTolerance && t < maxTolerance);

		//Password Rule
		Rule<String> pwdRule = new Rule<>
		("Password must between " + minPwd + "-"+maxPwd+" characters long and contain at least "
				+ minPwdSymbols + " of these categories:\n" +
				"* Lowercase letters\n" +
				"* Uppercase letters\n" +
				"* Numbers\n" +
				"* Special characters (Use only\". - _ + ! ? =\")"
				, t -> {
					int hasLowerCase = t.matches(".*[a-z]+.*") ? 1 : 0;
					int hasUpper = t.matches(".*[A-Z]+.*") ? 1 : 0;
					int hasNumber = t.matches(".*[0-9]+.*") ? 1 : 0;
					int hasSpecial = t.matches(".*[.-_+!?=]+.*") ? 1 : 0;
					boolean hasIllegal = !t.matches("[a-zA-Z0-9.-_+!?=]*");

					return (!hasIllegal &&
							((hasLowerCase + hasUpper + hasNumber + hasSpecial) >= minPwdSymbols) 
							&& t.length()>=minPwd && t.length()<=maxPwd);
				});

		//put Rules in hashmap
		ruleList.put(ID_String, idRule);
		ruleList.put(Name_String, nameRule);
		ruleList.put(Ini_String, iniRule);
//		ruleList.put(Roles_String, roleRule);
		ruleList.put(Cpr_String, cprRule);
		ruleList.put(Pwd_String, pwdRule);
		ruleList.put(Status_String, statusRule);
		ruleList.put(NonNetto_String, nomNettoRule);
		ruleList.put(Tolerance_String, toleranceRule);
	}

	@Override
	public Rule<Integer> getID() {
		return ruleList.get(ID_String);
	}

	@Override
	public Rule<String> getName() {
		return ruleList.get(Name_String);
	}

	@Override
	public Rule<String> getIni() {
		return ruleList.get(Ini_String);
	}

	@Override
	public Rule<String> getCpr() {
		return ruleList.get(Cpr_String);
	}
	
	@Override
	public Rule<Integer> getStatus() {
		return ruleList.get(Status_String);
	}
	
	@Override
	public Rule<Double> getNomNetto() {
		return ruleList.get(NonNetto_String);
	}
	
	@Override
	public Rule<Double> getTolerance() {
		return ruleList.get(Tolerance_String);
	}

	@Override
	public Rule<String> getPwd() {
		return ruleList.get(Pwd_String);
	}
	
//	@Override
//	public Rule<Set<String>> getRole() {
//		return ruleList.get(Roles_String);
//	}




}
