package dto;

public class CommodityDTO implements IDTO {
	private int commodityID = -1;
	private String name = "";
	private int supplierID = -1;

public CommodityDTO(int commodityID, String name, int supplierID){
		this.commodityID = commodityID;
		this.name = name;
		this.supplierID = supplierID;
	}

public CommodityDTO(CommodityDTO commodity){
	this.commodityID = commodity.getId();
	this.name = commodity.getName();
	this.supplierID = commodity.getSupplierID();
}

public CommodityDTO() {}

public int getId() {
	return commodityID;
}

public void setId(int commodityID) {
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