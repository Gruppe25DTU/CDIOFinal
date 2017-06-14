package logic.validation;

import java.security.Timestamp;

import dao.ProductBatchDAO;
import dto.ProductBatchDTO;
import logic.CDIOException.DALException;
import logic.validation.RuleSetInterface.RuleException;

public class ProductBatchDataCheck {
  private static RuleSetInterface rules = new RuleSet();

	public static int create(ProductBatchDTO dto) throws RuleException, DALException{
		if(rules.getID().test(dto.getRecipeID()) == false ||
				String.valueOf(dto.getRecipeID()) == null){
			throw new RuleException("Invalid RecipeID!");
		}
		if(rules.getStatus().test(dto.getStatus()) == false ||
				String.valueOf(dto.getStatus()) == null){
			throw new RuleException("Invalid Status!");
		}
		if(dto.getStartDate() == null){
			throw new RuleException("Invalid StartDate");
		}
		return ProductBatchDAO.create(dto);
	}

	public static void recipeID(int id) throws RuleException{
		if(rules.getID().test(id) == false ||
				String.valueOf(id) == null){
			throw new RuleException("Invalid Recipe ID!");
		}
	}

	public static void status(int status) throws RuleException{
		if(rules.getStatus().test(status) == false ||
				String.valueOf(status) == null){
			throw new RuleException("Invalid Status!");
		}
	}
	
	public static void startDate(Timestamp date) throws RuleException{
		throw new RuleException("Invalid StartDate");
	}
	
	public static void endDate(Timestamp date) throws RuleException{
		throw new RuleException("Invalid EndDate");
	}
}
