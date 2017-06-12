package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dto.CommodityDTO;
import logic.CDIOException.DALException;

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
	 * @return List< CommodityDTO >
	 */

	public List<CommodityDTO> getList() throws DALException{
		String cmd = "CALL getCommodityList();";
		List<CommodityDTO> list = new ArrayList<CommodityDTO>();

		try
		{
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while (rs.next()) 
			{
				list.add(new CommodityDTO(rs.getInt("commodity_ID"), rs.getString("commodity_Name"), rs.getInt("supplier_ID")));
			}
			return list;
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

	public CommodityDTO get(int id) throws DALException{
		String cmd = "CALL getCommodity('%d');";
		cmd = String.format(cmd, id);

		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while(rs.next()) {
				return new CommodityDTO(rs.getInt("commodity_ID"), rs.getString("commodity_Name"), rs.getInt("supplier_ID"));
			}
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

		return null;
	}


	/**
	 * Finds a free CommodityID that is not used. <br>
	 * It's possible to use the ID returned as a new ID.
	 * @return returns 0 if function fails <br>
	 * A number in the interval 1-99999999 if functions succeeds
	 */

	public int findFreeCommodityID() throws DALException{
		String cmd = "CALL findFreeCommodityID();";
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return 0;
			}
			while(rs.next()) {
				return rs.getInt("max");

			}
			return 0;
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
