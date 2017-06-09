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

		cmd = String.format(cmd, dto.getID(),dto.getRecipeID());
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
	 * Changes the starttime and stop time. <br>
	 * If the stop time has yet to be determined please leave an empty string in its place <br>
	 * 
	 * @param dto
	 * @return true if functions succeeds. false if not
	 * 
	 */
	@Override
	public boolean update(ProductBatchDTO dto) {
		String cmd = "CALL updateProductBatchTime('%s','%s','%d');";
		cmd = String.format(cmd, dto.getStartDate(),dto.getEndDate(),dto.getID());
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
	public boolean addComponent(ProductBatchDTO productBatch, ProductBatchCompDTO component) {
		String cmd = "CALL addProductBatchComponent('%d','%d','%d','%d','%d');";
		cmd = String.format(cmd, component.getID(),component.getCommodityID(),component.getTara(),component.getNet(),component.getuserID());
		
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
		String cmd = "CALL getProductBatchComponentNotWeighed('');";
		cmd  = String.format(cmd, pbid);
		
		try {
			ResultSet rs = Connector.doQuery(cmd);
			int productbatch_ID = rs.getInt("productbatch_ID");
			int commodity_ID = rs.getInt("commodity_ID");
			int	recipe_ID = rs.getInt("recipe_ID");
			
			String cmd2 = "CALL getSpecificRecipeComponent('','');";
			cmd2 = String.format(cmd2, recipe_ID,commodity_ID);
			ResultSet recipeCompRS = Connector.doQuery(cmd2);
			
			RecipeCompDTO recipeComp = new RecipeCompDTO(recipeCompRS.getInt("recipe_ID"),recipeCompRS.getInt("commodity_ID"),recipeCompRS.getInt("nom_net_weight"),recipeCompRS.getInt("tolerance"));
			if(productbatch_ID != pbid) {
				return null;
			}
			return recipeComp;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}
	}

}
