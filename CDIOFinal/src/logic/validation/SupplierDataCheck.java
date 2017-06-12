package logic.validation;

import dto.SupplierDTO;
import logic.validation.RuleSetInterface.RuleException;

public class SupplierDataCheck {

  private static RuleSetInterface rules = new RuleSet();

	public static void CreateSupplierDC(SupplierDTO dto) throws RuleException{

		if(rules.getID().test(dto.getId()) == false){
			throw new RuleException("Invalid ID!");
		}

		if(rules.getName().test(dto.getName()) == false || dto.getName() == null){
			throw new RuleException("Invalid Supplier Name!");
		}

	}

  public static void id(int id) throws RuleException{
    if(rules.getID().test(id) == false ||
        String.valueOf(id) == null){
      throw new RuleException("Invalid ID!");
    }
  }
	
	public static void name(String name) throws RuleException{
		if(rules.getName().test(name) == false || name == null){
			throw new RuleException("Invalid Supplier Name!");
		}
	}
}
