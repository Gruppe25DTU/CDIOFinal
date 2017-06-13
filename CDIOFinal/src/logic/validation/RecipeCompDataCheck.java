package logic.validation;

import dto.RecipeCompDTO;
import logic.validation.RuleSetInterface.RuleException;

public class RecipeCompDataCheck {

  private static RuleSetInterface rules = new RuleSet();

	public static void create(RecipeCompDTO dto) throws RuleException{
		if(rules.getID().test(dto.getRecipeID()) == false){
			throw new RuleException("Invalid RecipeID!");
		}
		if(rules.getID().test(dto.getCommodityID()) == false ||
				String.valueOf(dto.getCommodityID())== null){
			throw new RuleException("Invalid CommodityID!");
		}
		if(rules.getNomNetto().test(dto.getNomNetWeight()) == false ||
				String.valueOf(dto.getNomNetWeight()) == null){
			throw new RuleException("Invalid Nom Netto Weight!");
		}
		if(rules.getTolerance().test(dto.getTolerance()) == false ||
				String.valueOf(dto.getTolerance()) == null){
			throw new RuleException("Invalid Tolerance");
		}
	}

	public static void commodityID(int id) throws RuleException{
		if(rules.getID().test(id) == false ||
				String.valueOf(id)== null){
			throw new RuleException("Invalid CommodityID!");
		}
	}

	public static void nomNetWeight(double NN) throws RuleException{
		if(rules.getNomNetto().test(NN) == false ||
				String.valueOf(NN) == null){
			throw new RuleException("Invalid Nom Netto Weight!");
		}
	}
	public static void tolerance(double tol) throws RuleException{
		if(rules.getTolerance().test(tol) == false ||
				String.valueOf(tol) == null){
			throw new RuleException("Invalid Tolerance");
		}
	}
}