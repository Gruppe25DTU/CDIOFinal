package logic.validation;

import dto.ProductBatchCompDTO;
import logic.validation.RuleSetInterface.RuleException;

public class ProductBatchCompDataCheck {

  private static RuleSetInterface rules = new RuleSet();

	public static void ProductBatchCompDC(ProductBatchCompDTO dto) throws RuleException{
		if(rules.getID().test(dto.getProductBatchID()) == false ||
				String.valueOf(dto.getProductBatchID()) == null){
			throw new RuleException("Invalid ProductBatchComp ID!");
		}
		if(rules.getID().test(dto.getCommodityBatchID()) == false ||
				String.valueOf(dto.getCommodityBatchID())== null){
			throw new RuleException("Invalid CommodityBatch ID!");
		}
		if(rules.getID().test(dto.getUserID()) == false ||
				String.valueOf(dto.getUserID()) == null){
			throw new RuleException("Invalid UserID!");
		}
		if(String.valueOf(dto.getNet()) == null){
			throw new RuleException("Invalid Net!");
		}
		if(String.valueOf(dto.getTara()) == null){
			throw new RuleException("Invalid Tara!");
		}
	}

	public static void productBatchID(int id) throws RuleException{
		if(rules.getID().test(id) == false ||
				String.valueOf(id) == null){
			throw new RuleException("Invalid ProductBatchComp ID!");
		}
	}
	public static void commodityBatchID(int id) throws RuleException{
		if(rules.getID().test(id) == false ||
				String.valueOf(id) == null){
			throw new RuleException("Invalid CommodityBatch ID!");
		}
	}
	public static void userID(int id) throws RuleException{
		if(rules.getID().test(id) == false ||
				String.valueOf(id) == null){
			throw new RuleException("Invalid User ID!");
		}
	}
	public static void net(double net) throws RuleException{
		if(String.valueOf(net) == null){
			throw new RuleException("Invalid Net!");
		}
	}
	public static void tara(double tara) throws RuleException{
		if(String.valueOf(tara) == null){
			throw new RuleException("Invalid Tara!");
		}
	}
}
