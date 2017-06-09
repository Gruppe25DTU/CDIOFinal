package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws DALException
	 * @throws SQLException
	 */
	public int create(ProductBatchDTO dto) {
		String cmd = "CALL addProductBatch('%d',%d');";

		cmd = String.format(cmd, dto.getID(),dto.getRecipeID());
		int result;
		try {
			result = Connector.doUpdate(cmd);
		} catch (SQLException e) {
			result = 0;
			// TODO Auto-generated catch block
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
	public boolean update(ProductBatchDTO dto) {
		String cmd = "CALL updateProductBatchTime('%s','%s','%d');";
		cmd = String.format(cmd, dto.getStartDate(),dto.getEndDate(),dto.getID());
		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
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

	public void print(int id) {
		
	}
	
	
	
	/**
	 * Date.. How?
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ProductBatchDTO get(int id) {
		String cmd = "CALL getProductBatch('%d');";
		cmd = String.format(cmd, id);
		
		ResultSet rs = Connector.doQuery(cmd);
		int ID = rs.getInt("productBatch_ID");
		int status = rs.getInt("status");
		int recipe_ID = rs.getInt("recipe_ID");
		Date startdate = new Date(Date.parse(rs.getString("startdate")));
		date.parse(s)
		date.
		ProductBatchDTO pbDTO = new ProductBatchDTO();
		return null;
	}
	// Date.. how?
	
	public List<ProductBatchDTO> getList() {
		return null;
	}
	
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
	public RecipeCompDTO getNonWeightedComp(int pbid) {
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

			return recipeComp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
	}

}
