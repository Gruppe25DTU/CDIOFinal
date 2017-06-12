package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dto.SupplierDTO;

public class SupplierDAO {

	/**
	 * Creates a supplier <br>
	 * @return
	 * true if function succeeds <br>
	 * false if function fails
	 */
	
	public static boolean create(SupplierDTO dto){
		String cmd = "CALL addSupplier('%d','%s');";
		cmd = String.format(cmd, dto.getId(),dto.getName());

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
		String cmd = "CALL getSupplier('%d');";
		cmd = String.format(cmd, ID);

		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			rs.next();

			int supplier_ID = rs.getInt("supplier_ID");
			String supplier_Name = rs.getString("supplier_Name");

			return new SupplierDTO(supplier_ID,supplier_Name);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				Connector.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}


	/**
	 * Returns every existing supplier in the database
	 */
	
	public List<SupplierDTO> getList() {
		String cmd = "CALL getSupplierList();";
		List<SupplierDTO> list = new ArrayList<SupplierDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while(rs.next()) {
				int supplier_ID = rs.getInt("supplier_ID");
				String supplier_Name = rs.getString("supplier_Name");
				list.add(new SupplierDTO(supplier_ID,supplier_Name));			
			}

			return list;
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				Connector.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	
	/**
	 * Finds a free supplierID that is not used. <br>
	 * It's possible to use the ID returned as a new ID.
	 * @return returns 0 if function fails <br>
	 * A number in the interval 1-99999999 if functions succeeds
	 */
	
	public int findFreeSupplierID() {
		String cmd = "CALL findFreeSupplierID();";
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return 0;
			}
			rs.next();

			return rs.getInt("max");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		finally {
			try {
				Connector.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}
	/**
	 * Update supplier name. <br>
	 * @return Returns true if function succeeds
	 *<br> Returns false if function exception is thrown
	*/
	
	
	public boolean update(SupplierDTO dto) {
		String cmd = "CALL updateSupplier('%d','%s')";
		cmd = String.format(cmd, dto.getId(),dto.getName());
		
		try {
			int result = Connector.doUpdate(cmd);
			if(result != 0) {
				return true;
			}
			else 
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
