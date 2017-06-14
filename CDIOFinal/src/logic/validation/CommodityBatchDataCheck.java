package logic.validation;

import dao.CommodityBatchDAO;
import dto.CommodityBatchDTO;
import logic.CDIOException.DALException;
import logic.validation.RuleSetInterface.RuleException;

public class CommodityBatchDataCheck {
  
  private static RuleSetInterface rules = new RuleSet();

public static int create(CommodityBatchDTO dto) throws RuleException, DALException{
	if(rules.getID().test(dto.getCommodityID()) == false || 
			String.valueOf(dto.getCommodityID()) == null){
		throw new RuleException("Invalid Commodity ID!");
	}
	if(String.valueOf(dto.getQuantity()) == null ||  
			rules.getQuantity().test(dto.getQuantity()) == false){
		throw new RuleException("Invalid Quantity!");
	}
	return CommodityBatchDAO.create(dto);
}

public static void commodityID(int id) throws RuleException{
	if(rules.getID().test(id) == false || 
			String.valueOf(id) == null){
		throw new RuleException("Invalid Commodity ID!");
	}
}
public static void quantity(double quan) throws RuleException{
	if(String.valueOf(quan) == null ||  
			rules.getQuantity().test(quan) == false){
		throw new RuleException("Invalid Quantity!");
	}
}

}
