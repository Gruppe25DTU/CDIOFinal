package dto;

public class ProductBatchDTO {
	private int ID = -1;
	private int status = -1;
	private int recipeID =-1;
	private String startDate;
	private String endDate;
	
	
	
	public ProductBatchDTO(int ID, int status, int recipeID, String startDate, String endDate) {
		this.ID = ID;
		this.status = status;
		this.recipeID = recipeID; 
		this.startDate = startDate;
		this.endDate = endDate;
	}

public ProductBatchDTO(ProductBatchDTO productBatch){
	this.ID = productBatch.getID();
	this.status = productBatch.getStatus();
	this.recipeID = productBatch.getRecipeID();
	this.startDate = productBatch.getStartDate();
	this.endDate = productBatch.getEndDate();
}

	public int getID() {
		return ID;
	}



	public void setID(int iD) {
		ID = iD;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public int getRecipeID() {
		return recipeID;
	}



	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}



	public String getStartDate() {
		return startDate;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
}