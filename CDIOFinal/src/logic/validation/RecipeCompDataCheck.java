package logic.validation;

import dto.RecipeCompDTO;
import logic.validation.RuleSetInterface.RuleException;

public class RecipeCompDataCheck {

	private RuleSet rules;

	public void CreateRecipeCompDC(RecipeCompDTO dto) throws RuleException{
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

	public void RecipeCompCommodityIDDC(int id) throws RuleException{
		if(rules.getID().test(id) == false ||
				String.valueOf(id)== null){
			throw new RuleException("Invalid CommodityID!");
		}
	}

	public void RecipeCompNomNettoDC(double NN) throws RuleException{
		if(rules.getNomNetto().test(NN) == false ||
				String.valueOf(NN) == null){
			throw new RuleException("Invalid Nom Netto Weight!");
		}
	}
	public void RecipeCompTol(double tol) throws RuleException{
		if(rules.getTolerance().test(tol) == false ||
				String.valueOf(tol) == null){
			throw new RuleException("Invalid Tolerance");
		}
	}
}