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

	@Override
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
	
	@Override
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

	@Override
	public List<CommodityBatchDTO> getList() {
		String cmd = "call getCommodityBatchList();";
		List<CommodityBatchDTO> list = new ArrayList<CommodityBatchDTO>();
		try {
			ResultSet rs = Connector.doQuery(cmd);
			while(rs.next()) {
				int ID = rs.getInt("commodityBatch_ID");
				int commodity_ID = rs.getInt("commodity_ID");
				int quantity = rs.getInt("quantity");
				list.add(new CommodityBatchDTO(ID,commodity_ID,quantity));
				
			}
			return list;
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}
	}

}
