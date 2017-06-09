package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dal.Connector;
import daoInterface.SupplierInterfaceDAO;
import dto.SupplierDTO;

public class SupplierDAO implements SupplierInterfaceDAO{

	@Override
	public boolean create(SupplierDTO dto){
		String cmd = "CALL addSupplier('%d','%s');";
		cmd = String.format(cmd, dto.getID(),dto.getName());

		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns a supplier
	 */
	@Override
	public SupplierDTO getSupplier(int ID){
		String cmd = "CALL getSupplier('');";
		cmd = String.format(cmd, ID);
		
		try {
			ResultSet rs = Connector.doQuery(cmd);
			int supplier_ID = rs.getInt("supplier_I");
			String supplier_Name = rs.getString("supplier_Name");
			
			return new SupplierDTO(supplier_ID,supplier_Name);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SupplierDTO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

}
