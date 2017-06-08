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
	
}
