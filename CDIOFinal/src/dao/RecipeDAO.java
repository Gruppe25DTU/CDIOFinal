package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.Connector;
import daoInterface.RecipeInterfaceDAO;
import dto.RecipeDTO;

public class RecipeDAO implements RecipeInterfaceDAO{

	/**
	 * Creates a recipe. <br>
	 * Returns 0 if unable to do so.
	 * @param dto
	 * @return
	 */
	public int create(RecipeDTO dto) {
		String cmd = "CALL addRecipe('','')";
		cmd = String.format(cmd, dto.getID(),dto.getName());
		try {
			return Connector.doUpdate(cmd);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
			}
	}
	/**
	 * Updates a recipe name <br>
	 * Returns true if it succeeds <br>
	 * Returns false if not <br>
	 * @param dto
	 * @return
	 */
	public boolean update(RecipeDTO dto) {
		String cmd = "CALL updateRecipe('','');";
		cmd = String.format(cmd, dto.getID(),dto.getName());
		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Returns a recipe with parameter ID:
	 * @param id
	 * @return
	 */
	@Override
	public RecipeDTO getRecipe(int id) {
		String cmd = "getRecipe('');";
		cmd = String.format(cmd, id);
		
		try {
			ResultSet rs = Connector.doQuery(cmd);
			return new RecipeDTO(rs.getInt("recipe_ID"),rs.getString("recipe_Name"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	

}
