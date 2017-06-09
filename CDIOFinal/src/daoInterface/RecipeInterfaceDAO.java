package daoInterface;

import java.util.List;

import dto.RecipeDTO;

public interface RecipeInterfaceDAO {
	int create(RecipeDTO dto);
	boolean update(RecipeDTO dto);
	RecipeDTO getRecipe(int id);
	List<RecipeDTO> getRecipeList();
	int findFreeRecipeID();
}
