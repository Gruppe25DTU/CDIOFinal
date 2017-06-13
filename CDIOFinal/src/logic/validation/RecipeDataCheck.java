package logic.validation;

import java.awt.List;

import dto.RecipeDTO;
import logic.validation.RuleSetInterface.RuleException;

public class RecipeDataCheck {

  private static RuleSetInterface rules = new RuleSet();

	public static void create(RecipeDTO dto) throws RuleException{
		if(rules.getID().test(dto.getId()) == false){
			throw new RuleException("Invalid ID!");
		}
		if(rules.getName().test(dto.getName()) == false || dto.getName() == null){
			throw new RuleException("Invalid Recipe name!");
		}
		if(dto.getComponents() == null){
			throw new RuleException("Invalid number of components!");
		}
	}

  public static void id(int id) throws RuleException{
    if(rules.getID().test(id) == false ||
        String.valueOf(id) == null){
      throw new RuleException("Invalid ID!");
    }
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
