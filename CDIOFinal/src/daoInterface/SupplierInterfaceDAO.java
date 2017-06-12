package daoInterface;

import java.util.List;

import dto.SupplierDTO;

public interface SupplierInterfaceDAO {
	boolean create(SupplierDTO dto);
	SupplierDTO getSupplier(int ID);
	List<SupplierDTO> getList();
	int findFreeSupplierID();
	boolean update(SupplierDTO dto);
}
