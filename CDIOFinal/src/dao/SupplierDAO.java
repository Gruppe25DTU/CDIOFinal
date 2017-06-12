package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dto.SupplierDTO;
import logic.CDIOException.DALException;

public class SupplierDAO {

	/**
	 * Creates a supplier <br>
	 * @return
	 * true if function succeeds <br>
	 * false if function fails
	 */
	
	public static int create(SupplierDTO dto) throws DALException{
		String cmd = "CALL addSupplier('%s');";
		cmd = String.format(cmd, dto.getId(),dto.getName());

		try {
			ResultSet rs = Connector.doQuery(cmd);
			rs.next();
			return rs.getInt("ID");
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	/**
	 * Returns a supplier
	 */
	
	public SupplierDTO getSupplier(int ID) throws DALException{
		String cmd = "CALL getSupplier('%d');";
		cmd = String.format(cmd, ID);

		try {
			ResultSet rs = Connector.doQuery(cmd);
			rs.next();

			int supplier_ID = rs.getInt("supplier_ID");
			String supplier_Name = rs.getString("supplier_Name");

			return new SupplierDTO(supplier_ID,supplier_Name);
		} catch (SQLException e) {
			throw new DALException(e);
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
	
	public List<SupplierDTO> getList() throws DALException{
		String cmd = "CALL getSupplierList();";
		List<SupplierDTO> list = new ArrayList<SupplierDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			while(rs.next()) {
				int supplier_ID = rs.getInt("supplier_ID");
				String supplier_Name = rs.getString("supplier_Name");
				list.add(new SupplierDTO(supplier_ID,supplier_Name));			
			}

			return list;
		} catch (SQLException e) {			
			throw new DALException(e);

		}
		finally {
			try {
				Connector.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
