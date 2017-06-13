package logic.validation;

import dto.CommodityDTO;
import logic.validation.RuleSetInterface.RuleException;

public class CommodityDataCheck {

  private static RuleSetInterface rules = new RuleSet();
  
	public static void create(CommodityDTO dto) throws RuleException{
		if(rules.getID().test(dto.getId())== false ||
				String.valueOf(dto.getId()) == null){
			throw new RuleException("Invalid Commodity ID");
		}
		if(rules.getID().test(dto.getSupplierID()) == false || 
				String.valueOf(dto.getSupplierID()) == null){
			throw new RuleException("Invalid Supplier ID!");
		}
		if(rules.getName().test(dto.getName())== false || 
				dto.getName() == null){
			throw new RuleException("Invalid name!");
		}
	}
	public static void id(int id) throws RuleException{
		if(rules.getID().test(id)== false ||
				String.valueOf(id) == null){
			throw new RuleException("Invalid Commodity ID");
		}
	}
	public static void supplierId(int id) throws RuleException{
		if(rules.getID().test(id)== false ||
				String.valueOf(id) == null){
			throw new RuleException("Invalid Supplier ID");
		}
	}
	public static void name(String name) throws RuleException{
		if(rules.getName().test(name)== false || 
				name == null){
			throw new RuleException("Invalid name!");
		}
	}

}


