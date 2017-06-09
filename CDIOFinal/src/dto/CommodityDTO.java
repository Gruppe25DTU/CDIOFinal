package dto;

public class CommodityDTO{
	private int commodityID = -1;
	private String name = "";
	private int supplierID = -1;

public CommodityDTO(int commodityID, String name, int supplierID){
		this.commodityID = commodityID;
		this.name = name;
		this.supplierID = supplierID;
	}

public CommodityDTO(CommodityDTO commodity){
	this.commodityID = commodity.getcommodityID();
	this.name = commodity.getName();
	this.supplierID = commodity.getSupplierID();
}

public int getcommodityID() {
	return commodityID;
}

public void setcommodityID(int commodityID) {
	this.commodityID = commodityID;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getSupplierID() {
	return supplierID;
}

public void setSupplierID(int supplierID) {
	this.supplierID = supplierID;
}



}