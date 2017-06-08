package dto;

public class CommodityBatchDTO {
	private int ID = -1;
	private int commodityID = -1;
	private double quantity = -1;

public CommodityBatchDTO(int ID, int commodityID, double quantity){
		this.ID = ID;
		this.commodityID = commodityID;
		this.quantity = quantity;

	}

public CommodityBatchDTO(CommodityBatchDTO commodityBatch){
	this.ID = commodityBatch.getID();
	this.commodityID = commodityBatch.getCommodityID();
	this.quantity = commodityBatch.getQuantity();
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

public double getQuantity() {
	return quantity;
}

public void setQuantity(double quantity) {
	this.quantity = quantity;
}

@Override
public String toString() {
	return "CommodityBatchDTO [ID=" + ID + ", commodityID=" + commodityID + ", quantity=" + quantity + "]";
}



}
