package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import daoInterface.CommodityInterfaceDAO;
import dto.CommodityDTO;

public class CommodityDAO implements CommodityInterfaceDAO{


	/**
	 * Creates a commodity
	 * @param dto
	 * @return
	 *
	*/
	public int create(CommodityDTO dto) {
		String cmd = "CALL addCommodity('%d','%s','%d');";
		cmd = String.format(cmd, dto.getID(),dto.getName(),dto.getSupplierID());
		try {
			return Connector.doUpdate(cmd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;

		}
	}

	public boolean delete(CommodityDTO dto) {
		String cmd = "CALL deleteCommdity('%s');";
		cmd = String.format(cmd, dto.getID());
		return true;
	}

	public List<CommodityDTO> getList() {
		String cmd = "CALL getCommodityList();";
		List<CommodityDTO> list = new ArrayList<CommodityDTO>();

		try
		{
			ResultSet rs = Connector.doQuery(cmd);
			while (rs.next()) 
			{
				list.add(new CommodityDTO(rs.getInt("commodity_ID"), rs.getString("commodity_Name"), rs.getInt("supplier_ID")));
			}
		}
		catch (SQLException e) { 
			e.printStackTrace();}

		return null;
	}

	public CommodityDTO get(int id) {
		String cmd = "CALL getCommodity('');";
		cmd = String.format(cmd, id);
		
		try {
			ResultSet rs = Connector.doQuery(cmd);
			return new CommodityDTO(rs.getInt("commodity_ID"), rs.getString("commodity_Name"), rs.getInt("supplier_ID"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
