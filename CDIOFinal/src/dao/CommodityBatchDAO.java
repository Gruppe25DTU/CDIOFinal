package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dto.CommodityBatchDTO;
import logic.CDIOException.DALException;

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

	public CommodityBatchDTO get(int id) throws DALException{
		String cmd = "CALL getCommodityBatch('%d');";
		cmd = String.format(cmd, id);

		try {
			ResultSet rs = Connector.doQuery(cmd);
			rs.next();
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
	 * @return {@code List<CommodityBatchDTO>}
	 * @throws DALException
	 */

	public List<CommodityBatchDTO> getList() throws DALException{
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



	/**
	 * Finds a free CommodityBatchID that is not used. <br>
	 * It's possible to use the ID returned as a new ID.
	 * @return returns 0 if function fails <br>
	 * A number in the interval 1-99999999 if functions succeeds
	 */

	public int findFreeCommodityBatchID() throws DALException{
		String cmd = "CALL findFreeCommodityBatchID();";

		try {
			ResultSet rs = Connector.doQuery(cmd);
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

}
