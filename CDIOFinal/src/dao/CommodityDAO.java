package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dto.CommodityDTO;
import logic.CDIOException.*;

public class CommodityDAO {


	/**
	 * 
	 * @param dto
	 * @return {@code int commodityID}
	 * @throws DALException
	 */
	public static int create(CommodityDTO dto) throws DALException{
		String cmd = "CALL addCommodity('%s','%d');";
		cmd = String.format(cmd,dto.getName(),dto.getSupplierID());
		int ID;
		try {

			ResultSet rs = Connector.doQuery(cmd);
			ID = rs.getInt("ID");
			return ID;
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	/**
	 * Returns a list of all existing commodities
	 * @return CommodityDTO []
	 */

	public static CommodityDTO[] getList() throws DALException{
		String cmd = "CALL getCommodityList();";
		List<CommodityDTO> list = new ArrayList<CommodityDTO>();

		try
		{
			ResultSet rs = Connector.doQuery(cmd);
			while (rs.next()) 
			{
				list.add(new CommodityDTO(rs.getInt("commodity_ID"), rs.getString("commodity_Name"), rs.getInt("supplier_ID")));
			}
			if (list.isEmpty()) {
			  throw new EmptyResultSetException();
			}
			return (CommodityDTO[]) list.toArray(new CommodityDTO[list.size()]);
		}
		catch (SQLException e) { 
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
	 * Returns a commodityDTO
	 * @param commodity_ID
	 * @return commodityDTO
	 */

	public static CommodityDTO get(Integer id) throws DALException{
		String cmd = "CALL getCommodity('%d');";
		cmd = String.format(cmd, id);

		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(!rs.next()) {
			  throw new EmptyResultSetException();
			}
			return new CommodityDTO(rs.getInt("commodity_ID"), rs.getString("commodity_Name"), rs.getInt("supplier_ID"));
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
