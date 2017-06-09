package dto;

public class RecipeCompDTO {
private int recipeComponentID = -1;
private int commodityID = -1;
private double nomNetWeight = -1;
private double tolerance = -1;

public RecipeCompDTO(int recipeComponentID, int commodityID, double nomNetWeight, double tolerance){
	this.recipeComponentID = recipeComponentID;
	this.commodityID = commodityID;
	this.nomNetWeight = nomNetWeight;
	this.tolerance = tolerance;
}

public RecipeCompDTO(RecipeCompDTO recipeComp){
	this.recipeComponentID = recipeComp.getRecipeComponentID();
	this.commodityID = recipeComp.getCommodityID();
	this.nomNetWeight = recipeComp.getNomNetWeight();
	this.tolerance = recipeComp.getTolerance();
}

public int getRecipeComponentID() {
	return this.recipeComponentID;
}

public void setRecipeComponentID(int recipeComponentID) {
	this.recipeComponentID = recipeComponentID;
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


