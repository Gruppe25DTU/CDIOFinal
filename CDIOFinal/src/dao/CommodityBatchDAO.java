package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.Connector;
import dal.DALException;
import daoInterface.CommodityBatchInterfaceDAO;
import dto.CommodityBatchDTO;

public class CommodityBatchDAO implements CommodityBatchInterfaceDAO {

	/**
	 *Changes the quantity
	 */
	public boolean changeAmount(int id, int amount) {
		String cmd = "CALL changeQuantity('%d','%d');";
		cmd = String.format(cmd, id,amount);
		boolean returnvalue;
		try {
			Connector.doUpdate(cmd);
			returnvalue = true;
		} catch (SQLException e) {
			returnvalue = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnvalue;
	}

	public int create(CommodityBatchDTO dto) {
		String cmd = "CALL addCommodityBatch('%d','%d','%d');";
		cmd = String.format(cmd, dto.getID(),dto.getCommodityID(),dto.getQuantity());
		
		int returnValue;
		try {
			returnValue = Connector.doUpdate(cmd);
		} catch (SQLException e1) {
			returnValue = 0;
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return returnValue;
	}
	
	public CommodityBatchDTO get(int id) {
		String cmd = "CALL getCommodityBatch('%d');";
		cmd = String.format(cmd, id);
		
		try {
			ResultSet rs = Connector.doQuery(cmd);
			int ID = rs.getInt("commodityBatch_ID");
			int commodity_ID = rs.getInt("commodity_ID");
			int quantity = rs.getInt("quantity");
			return new CommodityBatchDTO(ID,commodity_ID,quantity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
