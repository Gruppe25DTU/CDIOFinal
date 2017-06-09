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
	@Override
	public int create(CommodityDTO dto) {
		String cmd = "CALL addCommodity('%d','%s','%d');";
		cmd = String.format(cmd, dto.getID(),dto.getName(),dto.getSupplierID());
		try {
			return Connector.doUpdate(cmd);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;

		}
	}

	/**
	 * Returns a list of all existing commodities
	 * @return List< CommodityDTO >
	 */
	@Override
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

	/**
	 * Returns a commodityDTO
	 * @param commodity_ID
	 * @return commodityDTO
	 */
	@Override
	public CommodityDTO get(int id) {
		String cmd = "CALL getCommodity('');";
		cmd = String.format(cmd, id);
		
		try {
			ResultSet rs = Connector.doQuery(cmd);
			return new CommodityDTO(rs.getInt("commodity_ID"), rs.getString("commodity_Name"), rs.getInt("supplier_ID"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
