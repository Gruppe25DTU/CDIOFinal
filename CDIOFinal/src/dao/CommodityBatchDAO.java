package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import daoInterface.CommodityBatchInterfaceDAO;
import dto.CommodityBatchDTO;

public class CommodityBatchDAO implements CommodityBatchInterfaceDAO {

	/**
	 *Changes the quantity
	 */
	@Override
	public boolean changeAmount(int id, double amount) {
		String cmd = "CALL changeQuantity('%d','%d');";
		cmd = String.format(cmd, id,amount);
		boolean returnvalue;
		try {
			Connector.doUpdate(cmd);
			returnvalue = true;
		} catch (SQLException e) {
			returnvalue = false;
			e.printStackTrace();
		}
		return returnvalue;
	}

	/**
	 * Creates a commodityBatch
	 */
	@Override
	public int create(CommodityBatchDTO dto) {
		String cmd = "CALL addCommodityBatch('%d','%d','%s');";
		int commodityBatchID = dto.getCommoditybatchID();
		int commodityID = dto.getCommodityID();
		double quantity = dto.getQuantity();
		String sQuantity = Double.toString(quantity);
		sQuantity = sQuantity.replace(",", ".");
		cmd = String.format(cmd,commodityBatchID,commodityID,quantity);
		
		int returnValue;
		try {
			returnValue = Connector.doUpdate(cmd);
		} catch (SQLException e1) {
			returnValue = 0;
			e1.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * Returns a commodityBatchDTO
	 */
	@Override
	public CommodityBatchDTO get(int id) {
		String cmd = "CALL getCommodityBatch('%d');";
		cmd = String.format(cmd, id);
		
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			rs.next();
			int ID = rs.getInt("commodityBatch_ID");
			int commodity_ID = rs.getInt("commodity_ID");
			double quantity = rs.getDouble("quantity");
			return new CommodityBatchDTO(ID,commodity_ID,quantity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a list over all existing CommodityBatches
	 * @return list < commodityBatchDTO >
	 */
	@Override
	public List<CommodityBatchDTO> getList() {
		String cmd = "call getCommodityBatchList();";
		List<CommodityBatchDTO> list = new ArrayList<CommodityBatchDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while(rs.next()) {
				int ID = rs.getInt("commodityBatch_ID");
				int commodity_ID = rs.getInt("commodity_ID");
				Double quantity = rs.getDouble("quantity");
				list.add(new CommodityBatchDTO(ID,commodity_ID,quantity));
				
			}
			return list;
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}
	}


	
	/**
	 * Finds a free CommodityBatchID that is not used. <br>
	 * It's possible to use the ID returned as a new ID.
	 * @return returns 0 if function fails <br>
	 * A number in the interval 1-99999999 if functions succeeds
	 */
	@Override
	public int findFreeCommodityBatchID() {
		String cmd = "CALL findFreeCommodityBatchID();";
		
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
	}

}
