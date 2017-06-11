package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import daoInterface.ProductBatchInterfaceDAO;
import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;
import dto.RecipeCompDTO;

public class ProductBatchDAO implements ProductBatchInterfaceDAO{


	/**
	 * Creates a ProductBatch. <br>
	 * Please note that it does NOT add the components
	 * @param dto
	 * @return 
	 * 
	 */
	@Override
	public int create(ProductBatchDTO dto) {
		String cmd = "CALL addProductBatch('%d',%d');";

		cmd = String.format(cmd, dto.getId(),dto.getRecipeID());
		int result;
		try {
			result = Connector.doUpdate(cmd);
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Updates the startdate of a productbatch. <br>
	 * @param dto
	 * @return true if functions succeeds. false if not
	 */
	@Override
	public boolean setStartdate(ProductBatchDTO dto) {
		String cmd = "CALL setProductBatchStartDate('%s','%d');";
		cmd = String.format(cmd, dto.getStartDate(),dto.getId());
		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Updates the stopdate of a productbatch. <br>
	 * @param dto
	 * @return true if functions succeeds. false if not
	 */
	@Override
	public boolean setStopdate(ProductBatchDTO dto) {
		String cmd = "CALL setProductBatchStopDate('%s','%d');";
		cmd = String.format(cmd, dto.getStartDate(),dto.getId());
		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
	@Override
	public boolean changeStatus(int id, int status) {
		String cmd = "CALL updateProductBatchStatus('%d','%d');";
		cmd = String.format(cmd, status,id);
		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Return format for pdf print
	 */
	@Override
	public void print(int id) {
		// TODO create function

	}

	/**
	 * returns a productBatchDTO
	 * @param id
	 * @return productBatchDTO
	 * @throws SQLException
	 */
	@Override
	public ProductBatchDTO get(int id) {
		String cmd = "CALL getProductBatch('%d');";
		cmd = String.format(cmd, id);

		ResultSet rs;
		try {
			rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			int ID = rs.getInt("productBatch_ID");
			int status = rs.getInt("status");
			int recipe_ID = rs.getInt("recipe_ID");
			String startdate = rs.getString("startdate");
			String stopdate = rs.getString("stopdate");
			ProductBatchDTO pbDTO = new ProductBatchDTO(ID,status,recipe_ID,startdate,stopdate);
			return pbDTO;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Returns a list over all existing productbatches
	 * @return List< ProductBatchDTO >
	 */
	@Override
	public List<ProductBatchDTO> getList() {
		String cmd = "CALL getProductBatchList();";
		List<ProductBatchDTO> list = new ArrayList<ProductBatchDTO>();

		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while(rs.next()) {
				int ID = rs.getInt("productBatch_ID");
				int status = rs.getInt("status");
				int recipe_ID = rs.getInt("recipe_ID");
				String startdate = rs.getString("startdate");
				String stopdate = rs.getString("stopdate");
				list.add(new ProductBatchDTO(ID,status,recipe_ID,startdate,stopdate));
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Adds a productBatchComponent to a specific productbatch
	 * @return 
	 * true if function succeeds <br>
	 * false if function fails
	 */
	@Override
	public boolean addComponent(ProductBatchCompDTO component) {
		String cmd = "CALL addProductBatchComponent('%d','%d','%d','%d','%d');";
		cmd = String.format(cmd, component.getproductBatchID(),component.getcommodityBatchID(),component.getTara(),component.getNet(),component.getuserID());
		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns a recipeComponent that has yet to weighed. 
	 * @param pbid
	 * @return
	 */
	@Override
	public RecipeCompDTO getNonWeighedComp(int pbid) {
		String cmd = "CALL getProductBatchComponentNotWeighed('%d');";
		cmd  = String.format(cmd, pbid);

		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			RecipeCompDTO recipeComp = new RecipeCompDTO(rs.getInt("recipe_ID"),rs.getInt("commodity_ID"),rs.getInt("nom_net_weight"),rs.getInt("tolerance"));
			
			return recipeComp;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}
	}



	/**
	 * Finds a free ProductBatchID that is not used. <br>
	 * It's possible to use the ID returned as a new ID.
	 * @return returns 0 if function fails <br>
	 * A number in the interval 1-99999999 if functions succeeds
	 */

	@Override
	public int findFreeProductBatchID() {
		String cmd = "CALL findFreeProductBatchID();";
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return 0;
			}
			return rs.getInt("max");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;

		}

	}
}
