package dto;

public class CommodityBatchDTO {
	private int commoditybatchID = -1;
	private int commodityID = -1;
	private double quantity = -1;

	public CommodityBatchDTO(int commoditybatchID, int commodityID, double quantity){
		this.commoditybatchID = commoditybatchID;
		this.commoditybatchID = commodityID;
		this.quantity = quantity;

	}

	public CommodityBatchDTO(CommodityBatchDTO commodityBatch){
		this.commoditybatchID = commodityBatch.getCommoditybatchID();
		this.commoditybatchID = commodityBatch.getCommodityID();
		this.quantity = commodityBatch.getQuantity();
	}

	public int getCommoditybatchID() {
		return commoditybatchID;
	}

	public void setCommoditybatchID(int commoditybatchID) {
		this.commoditybatchID = commoditybatchID;
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
		return "CommodityBatchDTO [commoditybatchID=" + commoditybatchID + ", commodityID=" + commodityID
				+ ", quantity=" + quantity + "]";
	}






}
