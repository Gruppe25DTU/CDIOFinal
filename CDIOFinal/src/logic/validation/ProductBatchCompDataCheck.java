package logic.validation;

import dto.ProductBatchCompDTO;
import logic.validation.RuleSetInterface.RuleException;

public class ProductBatchCompDataCheck {
	private RuleSet rules;

	public void ProductBatchCompDC(ProductBatchCompDTO dto) throws RuleException{
		if(rules.getID().test(dto.getProductBatchID()) == false ||
				String.valueOf(dto.getProductBatchID()) == null){
			throw new RuleException("Invalid ProductBatchComp ID!");
		}
		if(rules.getID().test(dto.getcommodityBatchID()) == false ||
				String.valueOf(dto.getcommodityBatchID())== null){
			throw new RuleException("Invalid CommodityBatch ID!");
		}
		if(rules.getID().test(dto.getuserID()) == false ||
				String.valueOf(dto.getuserID()) == null){
			throw new RuleException("Invalid UserID!");
		}
		if(String.valueOf(dto.getNet()) == null){
			throw new RuleException("Invalid Net!");
		}
		if(String.valueOf(dto.getTara()) == null){
			throw new RuleException("Invalid Tara!");
		}
	}

	public void ProductBatchCompIDDC(int id) throws RuleException{
		if(rules.getID().test(id) == false ||
				String.valueOf(id) == null){
			throw new RuleException("Invalid ProductBatchComp ID!");
		}
	}
	public void ProductBatchCompComBatchID(int id) throws RuleException{
		if(rules.getID().test(id) == false ||
				String.valueOf(id) == null){
			throw new RuleException("Invalid CommodityBatch ID!");
		}
	}
	public void ProductBatchCompUserID(int id) throws RuleException{
		if(rules.getID().test(id) == false ||
				String.valueOf(id) == null){
			throw new RuleException("Invalid User ID!");
		}
	}
	public void ProductBatchCompNet(double net) throws RuleException{
		if(String.valueOf(net) == null){
			throw new RuleException("Invalid Net!");
		}
	}
	public void ProductBatchCompTara(double tara) throws RuleException{
		if(String.valueOf(tara) == null){
			throw new RuleException("Invalid Tara!");
		}
	}
}
