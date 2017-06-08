package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import dal.Connector;
import dal.DALException;
import dto.CommodityDTO;
import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;

public class ProductBatchDAO {


	/**
	 * Creates a ProductBatch. <br>
	 * Please note that it does NOT add the components
	 * @param dto
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws DALException
	 * @throws SQLException
	 */
	public int create(ProductBatchDTO dto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, DALException, SQLException {
		String cmd = "CALL addProductBatch('%d',%d');";

		cmd = String.format(cmd, dto.getID(),dto.getRecipeID());
		int result = Connector.doUpdate(cmd);

		return result;
	}
	/**
	 * Changes the starttime and stop time. <br>
	 * If the stop time has yet to be determined please leave an empty string in its place <br>
	 * 
	 * @param dto
	 * @return
	 * @throws SQLException 
	 * @throws DALException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public boolean update(ProductBatchDTO dto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, DALException, SQLException {
		String cmd = "CALL updateProductBatchTime('%s','%s','%d');";
		cmd = String.format(cmd, dto.getStartDate(),dto.getEndDate(),dto.getID());
		Connector.doUpdate(cmd);
		return true;
	}
	/**
	 * Changes the status of the productBatch. <br>
	 * 0: Production has yet to be started <br>
	 * 1: Production has started <br>
	 * 2: Production has finished<br>
	 * @param id
	 * @param status
	 * @return
	 * either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws DALException
	 * @throws SQLException
	 */
	public int changeStatus(int id, int status) throws InstantiationException, IllegalAccessException, ClassNotFoundException, DALException, SQLException {
		String cmd = "CALL updateProductBatchStatus('%d','%d');";
		cmd = String.format(cmd, status,id);
		return Connector.doUpdate(cmd);
	}s

	public void print(int id) {
		
	}

	public ProductBatchDTO get(int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, DALException, SQLException {
		String cmd = "CALL getProductBatch('%d');";
		cmd = String.format(cmd, id);
		
		ResultSet rs = Connector.doQuery(cmd);
		int ID = rs.getInt("productBatch_ID");
		int status = rs.getInt("status");
		int recipe_ID = rs.getInt("recipe_ID");
		Date startdate = Date.parse(rs.getStirng("startdate")); 
		Date date = new Date();
		date.parse(s)
		ProductBatchDTO pbDTO = new ProductBatchDTO();
		return null;
	}

	public List<ProductBatchDTO> getList() {
		return null;
	}

	public boolean addComponent(ProductBatchDTO productBatch, ProductBatchCompDTO component) {
		return false;
	}

	public CommodityDTO getNonWeightedComp(int pbid) {
		return null;
	}

}
