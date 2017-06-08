package dto;

import java.sql.Date;

public class ProductBatchDTO {
	private int ID = -1;
	private int status = -1;
	private int recipeID =-1;
	private Date startDate;
	private Date endDate;
	
	
	
	public ProductBatchDTO(int ID, int status, int recipeID, Date startDate, Date endDate) {
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



	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
}