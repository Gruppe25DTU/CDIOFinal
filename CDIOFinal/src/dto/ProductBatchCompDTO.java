package dto;

public class ProductBatchCompDTO {
	private int ID = -1;
	private int commodityBatchID = -1;
	private double tare = -1;
	private double net = -1;
	private int oprID =-1;

	public ProductBatchCompDTO(int ID, int commodity, double tare, double net, int oprID){
		this.ID = ID;
		this.commodityBatchID = commodity;
		this.tare = tare;
		this.net = net;
		this.oprID = oprID;

	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getCommodityBatchID() {
		return commodityBatchID;
	}

	public void setCommodityBatchID(int commodityID) {
		this.commodityBatchID = commodityID;
	}

	public double getTare() {
		return tare;
	}

	public void setTare(double tare) {
		this.tare = tare;
	}

	public double getNet() {
		return net;
	}

	public void setNet(double net) {
		this.net = net;
	}

	public int getOprID() {
		return oprID;
	}

	public void setOprID(int oprID) {
		this.oprID = oprID;
	}

}
