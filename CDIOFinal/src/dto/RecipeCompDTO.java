package dto;

public class RecipeCompDTO {
private int ID = -1;
private int commodityID = -1;
private int nomNetWeight = -1;
private double tolerance = -1;

public RecipeCompDTO(int ID, int commodityID, int nomNetWeight, double tolerance){
	this.ID = ID;
	this.commodityID = commodityID;
	this.nomNetWeight = nomNetWeight;
	this.tolerance = tolerance;
}

public RecipeCompDTO(RecipeCompDTO recipeComp){
	this.ID = recipeComp.getID();
	this.commodityID = recipeComp.getCommodityID();
	this.nomNetWeight = recipeComp.getNomNetWeight();
	this.tolerance = recipeComp.getTolerance();
}

public int getID() {
	return ID;
}

public void setID(int iD) {
	ID = iD;
}

public int getCommodityID() {
	return commodityID;
}

public void setCommodityID(int commodityID) {
	this.commodityID = commodityID;
}

public int getNomNetWeight() {
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


