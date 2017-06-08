package dto;

public class SupplierDTO {

	private int ID = -1;
	private String name = "";
	
	public SupplierDTO(int ID, String name){
		this.ID = ID;
		this.name = name;
	}

	public SupplierDTO(SupplierDTO supplier){
		this.ID = supplier.getID();
		this.name = supplier.getName();
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
	
	
	
}
