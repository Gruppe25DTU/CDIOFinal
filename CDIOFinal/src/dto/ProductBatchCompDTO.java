package dto;

public class ProductBatchCompDTO {
private int ID = -1;
private int commodityID = -1;
private double tara = -1;
private double net = -1;
private int userID =-1;

public ProductBatchCompDTO(int ID, int commodityID, double tara, double net, int userID){
	this.ID = ID;
	this.commodityID = commodityID;
	this.tara = tara;
	this.net = net;
	this.userID = userID;
}

public ProductBatchCompDTO(ProductBatchCompDTO productBatchComp){
	this.ID = productBatchComp.getID();
	this.commodityID = productBatchComp.getCommodityID();
	this.tara = productBatchComp.getTara();
	this.net = productBatchComp.getNet();
	this.userID = productBatchComp.getuserID();
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

public double getTara() {
	return tara;
}

public void setTara(double tara) {
	this.tara = tara;
}

public double getNet() {
	return net;
}

public void setNet(double net) {
	this.net = net;
}

public int getuserID() {
	return userID;
}

public void setuserID(int userID) {
	this.userID = userID;
}



}
