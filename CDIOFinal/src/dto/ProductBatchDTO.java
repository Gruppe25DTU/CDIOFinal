package dto;

import java.sql.Timestamp;
import java.util.List;

public class ProductBatchDTO implements IDTO {
	private int productBatchID = -1;
	private int status = -1;
	private int recipeID =-1;
	private Timestamp startDate;
	private Timestamp endDate;
	private ProductBatchCompDTO[] components;
	
	
	
	public ProductBatchDTO(int productBatchID, int status, int recipeID, Timestamp startDate, Timestamp endDate, ProductBatchCompDTO[] components) {
		this.productBatchID = productBatchID;
		this.status = status;
		this.recipeID = recipeID; 
		this.startDate = startDate;
		this.endDate = endDate;
		this.components = components;
	}

public ProductBatchDTO(ProductBatchDTO productBatch){
	this.productBatchID = productBatch.getId();
	this.status = productBatch.getStatus();
	this.recipeID = productBatch.getRecipeID();
	this.startDate = productBatch.getStartDate();
	this.endDate = productBatch.getEndDate();
	this.components = productBatch.getComponents();
}

public ProductBatchDTO() {}

	public int getId() {
	  return getProductBatchID();
	}

	 public int getProductBatchID() {
	    return productBatchID;
	  }

	  public void setId(int id) {
	    setProductBatchID(id);
	  }

	public void setProductBatchID(int productBatchID) {
		this.productBatchID = productBatchID;
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



	public Timestamp getStartDate() {
		return startDate;
	}



	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}



	public Timestamp getEndDate() {
		return endDate;
	}



	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public ProductBatchCompDTO[] getComponents() {
		return components;
	}

	public void setComponents(ProductBatchCompDTO[] components) {
		this.components = components;
	}
	
	
	
}