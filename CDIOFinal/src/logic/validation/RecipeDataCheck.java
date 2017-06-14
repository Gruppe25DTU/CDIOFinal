package logic.validation;

import java.awt.List;

import dao.RecipeDAO;
import dto.RecipeDTO;
import logic.CDIOException.DALException;
import logic.validation.RuleSetInterface.RuleException;

public class RecipeDataCheck {

  private static RuleSetInterface rules = new RuleSet();

	public static int create(RecipeDTO dto) throws RuleException, DALException{
		if(rules.getName().test(dto.getName()) == false || dto.getName() == null){
			throw new RuleException("Invalid Recipe name!");
		}
		if(dto.getComponents() == null){
			throw new RuleException("Invalid number of components!");
		}
		return RecipeDAO.create(dto);
	}

	public static void name(String name) throws RuleException{
		if(rules.getName().test(name) == false || name == null){
			throw new RuleException("Invalid Recipe name!");
		}
	}

	public static void components(List components)  throws RuleException{
		if(components == null){
			throw new RuleException("Invalid number of components!");
		}

	}

}
