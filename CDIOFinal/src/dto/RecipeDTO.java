package dto;

import java.util.List;

public class RecipeDTO implements IDTO {
	private  int recipeID =-1;
	private String recipeName = "";
	RecipeCompDTO[] components;
	
	public RecipeDTO(int recipeID, String name, RecipeCompDTO[] components){
		this.recipeID = recipeID;
		this.recipeName = name;
		this.components = components;
	}
	
	public RecipeDTO(RecipeDTO recipe){
		this.recipeID = recipe.getId();
		this.recipeName = recipe.getRecipeName();
	}
	
	public RecipeDTO() {}

	public int getId() {
		return this.recipeID;
	}

	public void setId(int recipeID) {
		this.recipeID = recipeID;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String name) {
		this.recipeName = name;
	}

	public RecipeCompDTO[] getComponents() {
		return components;
	}

	public void setComponents(RecipeCompDTO[] components) {
		this.components = components;
	}

}
