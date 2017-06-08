package dto;

public class ProductBatchCompDTO {
private int ID = -1;
private int commodityID = -1;
private double tara = -1;
private double net = -1;
private int oprID =-1;

public ProductBatchCompDTO(int ID, int commodity, double tara, double net, int oprID){
	this.ID = ID;
	this.commodityID = commodity;
	this.tara = tara;
	this.net = net;
	this.oprID = oprID;
	
}

}
