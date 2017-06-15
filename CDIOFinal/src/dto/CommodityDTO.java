package dto;

public class CommodityDTO implements IDTO {
	private int commodityID = -1;
	private String commodityName = "";
	private int supplierID = -1;

public CommodityDTO(int commodityID, String name, int supplierID){
		this.commodityID = commodityID;
		this.commodityName = name;
		this.supplierID = supplierID;
	}

public CommodityDTO(CommodityDTO commodity){
	this.commodityID = commodity.getId();
	this.commodityName = commodity.getCommodityName();
	this.supplierID = commodity.getSupplierID();
}

public CommodityDTO() {}

public int getId() {
	return commodityID;
}

public void setId(int commodityID) {
	this.commodityID = commodityID;
}

public String getCommodityName() {
	return commodityName;
}

public void setCommodityName(String name) {
	this.commodityName = name;
}

public int getSupplierID() {
	return supplierID;
}

public void setSupplierID(int supplierID) {
	this.supplierID = supplierID;
}



}