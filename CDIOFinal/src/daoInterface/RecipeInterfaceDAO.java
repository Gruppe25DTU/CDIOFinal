package daoInterface;

import dto.RecipeDTO;

public interface RecipeInterfaceDAO {
	int create(RecipeDTO dto);
	boolean update(RecipeDTO dto);
	RecipeDTO getRecipe(int id);
}
