package dto;

import java.util.List;

public class RecipeDTO implements IDTO {
	private  int recipeID =-1;
	private String name = "";
	RecipeCompDTO[] components;
	
	public RecipeDTO(int recipeID, String name, RecipeCompDTO[] components){
		this.recipeID = recipeID;
		this.name = name;
		this.components = components;
	}
	
	public RecipeDTO(RecipeDTO recipe){
		this.recipeID = recipe.getId();
		this.name = recipe.getName();
	}
	
	public RecipeDTO() {}

	public int getId() {
		return this.recipeID;
	}

	public void setId(int recipeID) {
		this.recipeID = recipeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RecipeCompDTO[] getComponents() {
		return components;
	}

	public void setComponents(RecipeCompDTO[] components) {
		this.components = components;
	}

}
