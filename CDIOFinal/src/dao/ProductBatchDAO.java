package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;
import dto.RecipeCompDTO;
import logic.CDIOException.*;

public class ProductBatchDAO {


	/**
	 * Creates a ProductBatch. <br>
	 * Please note that it does NOT add the components
	 * @param dto
	 * @return 
	 * 
	 */

	public static int create(ProductBatchDTO dto) throws DALException{
		String cmd = "CALL addProductBatch('%d','%d');";

		cmd = String.format(cmd, dto.getId(),dto.getRecipeID());

		int id;
		try {
			ResultSet rs = Connector.doQuery(cmd);
			rs.next();
			id = rs.getInt("ID");
			if(dto.getStartDate() != null) {
				setStartdate(dto);
			}
			if(dto.getEndDate() != null) {
				setStopdate(dto);
			}
			return id;
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	/**
	 * Updates the startdate of a productbatch. <br>
	 * @param dto
	 * @return true if functions succeeds. false if not
	 */

	public static void setStartdate(ProductBatchDTO dto) throws DALException{
		String cmd = "CALL setProductBatchStartDate('%s','%d');";
		cmd = String.format(cmd, dto.getStartDate().toString(),dto.getId());

		try {
			Connector.doUpdate(cmd);
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}
	/**
	 * Updates the stopdate of a productbatch. <br>
	 * @param dto
	 * @return 
	 * @return true if functions succeeds. false if not
	 */

	public static void setStopdate(ProductBatchDTO dto) throws DALException{
		String cmd = "CALL setProductBatchStopDate('%s','%d');";
		cmd = String.format(cmd, dto.getEndDate().toString(),dto.getId());

		try {
			Connector.doUpdate(cmd);
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	/**
	 * changes a productbatch status <br>
	 * 0: production not started <br>
	 * 1: production has started <br>
	 * 2: production has finished<br>
	 * @return true if function succeeds <br>
	 * false if function fails
	 *  
	 */

	public void changeStatus(int id, int status) throws DALException{
		String cmd = "CALL updateProductBatchStatus('%d','%d');";
		cmd = String.format(cmd, status,id);
		try {
			Connector.doUpdate(cmd);
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}


	/**
	 * returns a productBatchDTO
	 * @param id
	 * @return productBatchDTO
	 * @throws SQLException
	 */

	public static ProductBatchDTO get(Integer id) throws DALException{
		String cmd = "CALL getProductBatch('%d');";
		cmd = String.format(cmd, id);

		ResultSet rs;
		try {
			rs = Connector.doQuery(cmd);
			if(!rs.next()) {
			  throw new EmptyResultSetException();
			}
			else {
  			int ID = rs.getInt("productbatch_ID");
  			int status = rs.getInt("status");
  			int recipe_ID = rs.getInt("recipe_ID");
  			Timestamp startdate = rs.getTimestamp("startdate");
  			Timestamp stopdate = rs.getTimestamp("stopdate");
  			ProductBatchCompDTO[] components = getProductBatchComponents(ID);
  			ProductBatchDTO pbDTO = new ProductBatchDTO(ID,status,recipe_ID,startdate,stopdate,components);
  			return pbDTO;
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


	}

	/**
	 * Returns a list over all existing productbatches
	 * @return List< ProductBatchDTO >
	 */

	public static ProductBatchDTO[] getList() throws DALException{
		String cmd = "CALL getProductBatchList();";
		List<ProductBatchDTO> list = new ArrayList<ProductBatchDTO>();

		try {
			ResultSet rs = Connector.doQuery(cmd);
			while(rs.next()) {
				int ID = rs.getInt("productBatch_ID");
				int status = rs.getInt("status");
				int recipe_ID = rs.getInt("recipe_ID");
				Timestamp startdate = rs.getTimestamp("startdate");
				Timestamp stopdate = rs.getTimestamp("stopdate");
				try {
				ProductBatchCompDTO[] components = getProductBatchComponents(ID);
        list.add(new ProductBatchDTO(ID,status,recipe_ID,startdate,stopdate,components));
				} catch (EmptyResultSetException e) {
				  continue;
				}
			}
			if (list.isEmpty()) {
			  throw new EmptyResultSetException();
			}
			return (ProductBatchDTO[]) list.toArray(new ProductBatchDTO[list.size()]);
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

	/**
	 * Adds a productBatchComponent to a specific productbatch
	 * @return 
	 * true if function succeeds <br>
	 * false if function fails
	 */

	public void addComponent(ProductBatchCompDTO component) throws DALException{
		String cmd = "CALL addProductBatchComponent('%d','%d','%s','%s','%d');";
		String tare = Double.toString(component.getTara());
		String net = Double.toString(component.getNet());


		cmd = String.format(cmd, component.getProductBatchID(),component.getCommodityBatchID(),tare,net,component.getUserID());
		try {
			Connector.doUpdate(cmd);
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

	/**
	 * Returns a recipeComponent that has yet to weighed. 
	 * @param pbid
	 * @return
	 */

	public RecipeCompDTO getNonWeighedComp(int pbid) throws DALException{
		String cmd = "CALL getProductBatchComponentNotWeighed('%d');";
		cmd  = String.format(cmd, pbid);

		try {
			ResultSet rs = Connector.doQuery(cmd);
			while(rs.next()) {
				RecipeCompDTO recipeComp = new RecipeCompDTO(rs.getInt("recipe_ID"),rs.getInt("commodity_ID"),rs.getDouble("nom_net_weight"),rs.getDouble("tolerance"));
				return recipeComp;
			}
			throw new EmptyResultSetException();
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


	public static ProductBatchCompDTO[] getProductBatchComponents(Integer productBatchID) throws DALException{
		String cmd = "CALL getProductBatchComponent('%d')";
		cmd = String.format(cmd, productBatchID);
		List<ProductBatchCompDTO> list = new ArrayList<>();

		try {
			ResultSet rs = Connector.doQuery(cmd);
			while(rs.next()) {
				int commodityBatchID = rs.getInt("commodityBatch_ID");
				Double tare = rs.getDouble("tare");
				Double net = rs.getDouble("net");
				int userID = rs.getInt("user_ID");
				ProductBatchCompDTO dto = new ProductBatchCompDTO(productBatchID,commodityBatchID,tare,net,userID);
				list.add(dto);
			}
			return (ProductBatchCompDTO[]) list.toArray(new ProductBatchCompDTO[list.size()]);
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
