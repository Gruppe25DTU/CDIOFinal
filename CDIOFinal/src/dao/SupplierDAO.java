package dao;
import java.sql.ResultSet;
import java.sql.SQLException;

import dal.Connector;
import daoInterface.SupplierInterfaceDAO;
import dto.SupplierDTO;

public class SupplierDAO implements SupplierInterfaceDAO{

	public boolean create(SupplierDTO dto){
		String cmd = "CALL addSupplier('%d','%s');";
		cmd = String.format(cmd, dto.getID(),dto.getName());

		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates a supplier <br>
	 * 
	 */
	public boolean update(int ID,String name){
		String cmd = "CALL updateSupplier('','')";
		cmd = String.format(cmd, ID,name);

		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	/**
	 * Deletes a supplier <br> 
	 * returns true if the function succeeds <br>
	 * and false if not
	 */
	public boolean delete(int ID){
		String cmd = "CALL deleteSupplier('');";
		cmd = String.format(cmd, ID);

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

}
