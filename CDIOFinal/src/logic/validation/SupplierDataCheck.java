package logic.validation;

import dao.RecipeDAO;
import dao.SupplierDAO;
import dto.SupplierDTO;
import logic.CDIOException.DALException;
import logic.validation.RuleSetInterface.RuleException;

public class SupplierDataCheck {

  private static RuleSetInterface rules = new RuleSet();

	public static int create(SupplierDTO dto) throws RuleException, DALException{

		if(rules.getName().test(dto.getSupplierName()) == false || dto.getSupplierName() == null){
			throw new RuleException("Invalid Supplier Name!");
		}
		return SupplierDAO.create(dto);
	}
	
	public static void name(String name) throws RuleException{
		if(rules.getName().test(name) == false || name == null){
			throw new RuleException("Invalid Supplier Name!");
		}
	}
}
