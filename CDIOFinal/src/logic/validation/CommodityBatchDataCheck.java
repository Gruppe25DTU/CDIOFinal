package logic.validation;

import dto.CommodityBatchDTO;
import logic.validation.RuleSetInterface.RuleException;

public class CommodityBatchDataCheck {
  
  private static RuleSetInterface rules = new RuleSet();

public static void CommodityBatchDC(CommodityBatchDTO dto) throws RuleException{
	if(rules.getID().test(dto.getId())== false ||
			String.valueOf(dto.getId()) == null){
		throw new RuleException("Invalid CommodityBatch ID");
	}
	if(rules.getID().test(dto.getCommodityID()) == false || 
			String.valueOf(dto.getCommodityID()) == null){
		throw new RuleException("Invalid Commodity ID!");
	}
	if(String.valueOf(dto.getQuantity()) == null ||  
			rules.getQuantity().test(dto.getQuantity()) == false){
		throw new RuleException("Invalid Quantity!");
	}
}

public static void id(int id) throws RuleException{
	if(rules.getID().test(id)== false ||
			String.valueOf(id) == null){
		throw new RuleException("Invalid CommodityBatch ID");
	}
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
