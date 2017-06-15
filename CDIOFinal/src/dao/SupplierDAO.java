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
    Connector conn = new Connector();
		String cmd = "CALL addSupplier('%s');";
		cmd = String.format(cmd, dto.getName());

		try {
			ResultSet rs = conn.doQuery(cmd);
			rs.next();
			return rs.getInt("ID");
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new DALException(e);
      }
    }
	}

	/**
	 * Returns a supplier
	 */
	
	public static SupplierDTO get(Integer id) throws DALException {
	  return getSupplier(id);
	}
	
	public static SupplierDTO getSupplier(int ID) throws DALException{
    Connector conn = new Connector();
		String cmd = "CALL getSupplier('%d');";
		cmd = String.format(cmd, ID);

		try {
			ResultSet rs = conn.doQuery(cmd);
			rs.next();

			int supplier_ID = rs.getInt("supplier_ID");
			String supplier_Name = rs.getString("supplier_Name");

			return new SupplierDTO(supplier_ID,supplier_Name);
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new DALException(e);
      }
    }
	}


	/**
	 * Returns every existing supplier in the database
	 */
	
	public static SupplierDTO[] getList() throws DALException{
    Connector conn = new Connector();
		String cmd = "CALL getSupplierList();";
		List<SupplierDTO> list = new ArrayList<SupplierDTO>();
		try {
			ResultSet rs = conn.doQuery(cmd);
			while(rs.next()) {
				int supplier_ID = rs.getInt("supplier_ID");
				String supplier_Name = rs.getString("supplier_Name");
				list.add(new SupplierDTO(supplier_ID,supplier_Name));			
			}

			return (SupplierDTO[]) list.toArray(new SupplierDTO[list.size()]);
		} catch (SQLException e) {			
			throw new DALException(e);
		} finally {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new DALException(e);
      }
    }
	}
}
