package logic;

import dto.CommodityBatchDTO;
import logic.RuleSetInterface.RuleException;

public class CommodityBatchDataCheck {
private RuleSet rules;

public void CommodityBatchDC(CommodityBatchDTO dto) throws RuleException{
	if(rules.getID().test(dto.getCommoditybatchID())== false ||
			String.valueOf(dto.getCommoditybatchID()) == null){
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

public void CommodityBatchIDDC(int id) throws RuleException{
	if(rules.getID().test(id)== false ||
			String.valueOf(id) == null){
		throw new RuleException("Invalid CommodityBatch ID");
	}
}
public void CommodityBatchComIDDC(int id) throws RuleException{
	if(rules.getID().test(id) == false || 
			String.valueOf(id) == null){
		throw new RuleException("Invalid Commodity ID!");
	}
}
public void CommodityBatchQuan(double quan) throws RuleException{
	if(String.valueOf(quan) == null ||  
			rules.getQuantity().test(quan) == false){
		throw new RuleException("Invalid Quantity!");
	}
}

}
