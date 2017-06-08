package dao;

import java.sql.SQLException;
import java.util.List;

import dal.Connector;
import dal.DALException;
import dto.CommodityDTO;

public class CommodityDAO {


	/**
	 * Creates a commodity
	 * @param dto
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws DALException
	 * @throws SQLException
	 */
	public int create(CommodityDTO dto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, DALException, SQLException {
		String cmd = "CALL addCommodity('%d','%s','%d');";
		cmd = String.format(cmd, dto.getID(),dto.getName(),dto.getSupplierID());
		return Connector.doUpdate(cmd);
	}

	public boolean delete(CommodityDTO dto) {
		String cmd = "CALL deleteCommdity('%s');";
		cmd = String.format(cmd, dto.getID());
		return true;
	}

	public List<CommodityDTO> getList() {
		String cmd = "";
		return null;
	}

	public CommodityDTO get(int id) {
		return null;
	}

}
