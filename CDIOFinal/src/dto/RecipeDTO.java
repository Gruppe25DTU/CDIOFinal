package dto;

public class RecipeDTO {
	private  int ID =-1;
	private String name = "";
	
	public RecipeDTO(int ID, String name){
		this.ID = ID;
		this.name = name;
	}
	
	public RecipeDTO(RecipeDTO recipe){
		this.ID = recipe.getID();
		this.name = recipe.getName();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
