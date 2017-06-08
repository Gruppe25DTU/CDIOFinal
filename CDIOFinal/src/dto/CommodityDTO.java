package dto;

public class CommodityDTO{
	private int ID = -1;
	private String name = "";
	private int supplierID = -1;

public CommodityDTO(int ID, String name, int supplierID){
		this.ID = ID;
		this.name = name;
		this.supplierID = supplierID;

	}

public int getID() {
	return ID;
}

public void setID(int iD) {
	ID = iD;
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
