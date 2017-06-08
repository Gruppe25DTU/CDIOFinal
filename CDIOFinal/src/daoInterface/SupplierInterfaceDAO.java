package daoInterface;

import dto.SupplierDTO;

public interface SupplierInterfaceDAO {
boolean create(SupplierDTO dto);
boolean update(int ID);
boolean delete(int ID);
SupplierDTO getSupplier(int ID);
}
