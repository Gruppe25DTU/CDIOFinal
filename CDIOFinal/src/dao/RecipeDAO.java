package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	@Override
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
	@Override
	public boolean update(RecipeDTO dto) {
		String cmd = "CALL updateRecipe('','');";
		cmd = String.format(cmd, dto.getID(),dto.getName());
		try {
			Connector.doUpdate(cmd);
			return true;
		} catch (SQLException e) {
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
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns a list over every existing recipes
	 */
	@Override
	public List<RecipeDTO> getRecipeList() {
		String cmd = "CALL getRecipeList();";
		List<RecipeDTO> list = new ArrayList<RecipeDTO>();
		
		try {
			ResultSet rs = Connector.doQuery(cmd);
			while (rs.next()) {
				list.add(new RecipeDTO(rs.getInt("recipe_ID"),rs.getString("recipe_Name")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	

}
