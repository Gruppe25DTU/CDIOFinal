package dto;

import java.util.List;

public class RecipeDTO {
	private  int recipeID =-1;
	private String name = "";
	List<RecipeCompDTO> components;
	
	public RecipeDTO(int recipeID, String name, List<RecipeCompDTO> components){
		this.recipeID = recipeID;
		this.name = name;
		this.components = components;
	}
	
	public RecipeDTO(RecipeDTO recipe){
		this.recipeID = recipe.getRecipeID();
		this.name = recipe.getName();
	}

	public int getRecipeID() {
		return this.recipeID;
	}

	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RecipeCompDTO> getComponents() {
		return components;
	}

	public void setComponents(List<RecipeCompDTO> components) {
		this.components = components;
	}

	

}
