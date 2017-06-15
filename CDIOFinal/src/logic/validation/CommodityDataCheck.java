package logic.validation;

import dao.CommodityDAO;
import dto.CommodityDTO;
import logic.CDIOException.DALException;
import logic.validation.RuleSetInterface.RuleException;

public class CommodityDataCheck {

  private static RuleSetInterface rules = new RuleSet();
  
	public static int create(CommodityDTO dto) throws RuleException, DALException{
		if(rules.getID().test(dto.getSupplierID()) == false || 
				String.valueOf(dto.getSupplierID()) == null){
			throw new RuleException("Invalid Supplier ID!");
		}
		if(rules.getName().test(dto.getCommodityName())== false || 
				dto.getCommodityName() == null){
			throw new RuleException("Invalid name!");
		}
		return CommodityDAO.create(dto);
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


