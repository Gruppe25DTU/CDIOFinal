package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dto.CommodityBatchDTO;
import logic.CDIOException.*;

public class CommodityBatchDAO {

	/**
	 * Changes the quantity of a commodityBatchDAO
	 * @param id
	 * @param amount
	 * @return
	 */
	public static void changeAmount(int id, double amount) throws DALException{
		String cmd = "CALL changeQuantity('%d','%s');";
		String quantity = Double.toString(amount);
		cmd = String.format(cmd, id,quantity);
		try {
			Connector.doUpdate(cmd);
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
	 * Creates a commodityBatch. <br>
	 * Returns ID generated
	 * @param dto
	 * @return {@code int ID}
	 * @throws DALException
	 */

	public static int create(CommodityBatchDTO dto) throws DALException{
		String cmd = "CALL addCommodityBatch('%d','%s');";
		int commodityID = dto.getCommodityID();
		double quantity = dto.getQuantity();
		String sQuantity = Double.toString(quantity);
		cmd = String.format(cmd,commodityID,sQuantity);

		int ID;
		try {
			ResultSet rs = Connector.doQuery(cmd);
			rs.next();
			ID = rs.getInt("ID");
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

		return ID;
	}

	/**
	 * Returns a commodityBatchDTO
	 */

	public static CommodityBatchDTO get(Integer id) throws DALException{
		String cmd = "CALL getCommodityBatch('%d');";
		cmd = String.format(cmd, id);

		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(!rs.next()) {
			  throw new EmptyResultSetException();
			}
			int ID = rs.getInt("commodityBatch_ID");
			int commodity_ID = rs.getInt("commodity_ID");
			double quantity = rs.getDouble("quantity");
			return new CommodityBatchDTO(ID,commodity_ID,quantity);
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
	 * Returns every commodity batch in the database.
	 * @return {@code CommodityBatchDTO[]}
	 * @throws DALException
	 */

	public static CommodityBatchDTO[] getList() throws DALException{
		String cmd = "call getCommodityBatchList();";
		List<CommodityBatchDTO> list = new ArrayList<CommodityBatchDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			while(rs.next()) {
				int ID = rs.getInt("commodityBatch_ID");
				int commodity_ID = rs.getInt("commodity_ID");
				Double quantity = rs.getDouble("quantity");
				list.add(new CommodityBatchDTO(ID,commodity_ID,quantity));

			}
			if (list.isEmpty()) {
			  throw new EmptyResultSetException();
			}
			return (CommodityBatchDTO[]) list.toArray(new CommodityBatchDTO[list.size()]);
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
