package dto;

public class SupplierDTO {

	private int supplierID = -1;
	private String name = "";
	
	public SupplierDTO(int supplierID, String name){
		this.supplierID = supplierID;
		this.name = name;
	}

	public SupplierDTO(SupplierDTO supplier){
		this.supplierID = supplier.getSupplierID();
		this.name = supplier.getName();
	}
	
	public int getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
