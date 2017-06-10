package daoInterface;

import java.util.List;

import dto.RecipeCompDTO;
import dto.RecipeDTO;

public interface RecipeInterfaceDAO {
	int create(RecipeDTO dto);
	RecipeDTO getRecipe(int id);
	List<RecipeDTO> getRecipeList();
	int findFreeRecipeID();
	List<RecipeCompDTO> getRecipeComponent(int ID);
	int createRecipeComponent(List<RecipeCompDTO> components);
}
