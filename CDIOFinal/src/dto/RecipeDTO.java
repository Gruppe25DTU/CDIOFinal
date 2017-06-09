package dto;

public class RecipeDTO {
	private  int recipeID =-1;
	private String name = "";
	
	public RecipeDTO(int recipeID, String name){
		this.recipeID = recipeID;
		this.name = name;
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

	

}
