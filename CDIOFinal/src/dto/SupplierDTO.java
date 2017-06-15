package dto;

public class SupplierDTO implements IDTO {

	private int supplierID = -1;
	private String supplierName = "";
	
	public SupplierDTO(int supplierID, String name){
		this.supplierID = supplierID;
		this.supplierName = name;
	}

	public SupplierDTO(SupplierDTO supplier){
		this.supplierID = supplier.getId();
		this.supplierName = supplier.getSupplierName();
	}
	
	public SupplierDTO() {}
	
	public int getId() {
		return supplierID;
	}

	public void setId(int supplierID) {
		this.supplierID = supplierID;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String name) {
		this.supplierName = name;
	}
	
	
	
}
