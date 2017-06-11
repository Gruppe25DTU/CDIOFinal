package logic;

import dto.SupplierDTO;
import logic.RuleSetInterface.RuleException;

public class SupplierDataCheck {
	private RuleSet rules;

	public void CreateSupplierDC(SupplierDTO dto) throws RuleException{

		if(rules.getID().test(dto.getId()) == false){
			throw new RuleException("Invalid ID!");
		}

		if(rules.getName().test(dto.getName()) == false || dto.getName() == null){
			throw new RuleException("Invalid Supplier Name!");
		}

	}
	
	public void SupplierNameDC(String name) throws RuleException{
		if(rules.getName().test(name) == false || name == null){
			throw new RuleException("Invalid Supplier Name!");
		}
	}
}
