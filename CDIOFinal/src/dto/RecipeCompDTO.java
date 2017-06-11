package dto;

public class RecipeCompDTO implements IDTO {
	private int recipeID = -1;
	private int commodityID = -1;
	private double nomNetWeight = -1;
	private double tolerance = -1;

	public RecipeCompDTO(int recipeID, int commodityID, double nomNetWeight, double tolerance){
		this.recipeID = recipeID;
		this.commodityID = commodityID;
		this.nomNetWeight = nomNetWeight;
		this.tolerance = tolerance;
	}

	public RecipeCompDTO(RecipeCompDTO recipeComp){
		this.recipeID = recipeComp.getRecipeID();
		this.commodityID = recipeComp.getCommodityID();
		this.nomNetWeight = recipeComp.getNomNetWeight();
		this.tolerance = recipeComp.getTolerance();
	}
	
	public RecipeCompDTO() {}

	public int getRecipeID() {
		return this.recipeID;
	}

	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}

	public int getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(int commodityID) {
		this.commodityID = commodityID;
	}

	public double getNomNetWeight() {
		return nomNetWeight;
	}

	public void setNomNetWeight(int nomNetWeight) {
		this.nomNetWeight = nomNetWeight;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

}


