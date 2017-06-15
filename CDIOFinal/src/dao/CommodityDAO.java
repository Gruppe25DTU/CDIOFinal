package dao;

import java.sql.Connection;
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
	  Connector conn = new Connector();
		String cmd = "CALL addCommodity('%s','%d');";
		cmd = String.format(cmd,dto.getName(),dto.getSupplierID());
		int ID;
		try {
			ResultSet rs = conn.doQuery(cmd);
      if (!rs.next()) {
        throw new EmptyResultSetException();
      }
			ID = rs.getInt("ID");
			return ID;
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
	 * Returns a list of all existing commodities
	 * @return CommodityDTO []
	 */

	public static CommodityDTO[] getList() throws DALException{
    Connector conn = new Connector();
		String cmd = "CALL getCommodityList();";
		List<CommodityDTO> list = new ArrayList<CommodityDTO>();

		try
		{
			ResultSet rs = conn.doQuery(cmd);
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
		} finally {
		  try {
        conn.close();
      } catch (SQLException e) {
        throw new DALException(e);
      }
		}
	}

	/**
	 * Returns a commodityDTO
	 * @param commodity_ID
	 * @return commodityDTO
	 */

	public static CommodityDTO get(Integer id) throws DALException{
    Connector conn = new Connector();
		String cmd = "CALL getCommodity('%d');";
		cmd = String.format(cmd, id);

		try {
			ResultSet rs = conn.doQuery(cmd);
			if(!rs.next()) {
			  throw new EmptyResultSetException();
			}
			return new CommodityDTO(rs.getInt("commodity_ID"), rs.getString("commodity_Name"), rs.getInt("supplier_ID"));
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
