package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import daoInterface.RecipeInterfaceDAO;
import dto.RecipeCompDTO;
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
		String cmd = "CALL addRecipe('%d','%s')";
		cmd = String.format(cmd, dto.getRecipeID(),dto.getName());
		try {
			int result = Connector.doUpdate(cmd);
			createRecipeComponent(dto.getComponents());
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
			}
	}
	
	/**
	 * Adds list of recipeComponents
	 */
	@Override
	public int createRecipeComponent(List<RecipeCompDTO> components) {
		String cmd = "CALL addRecipeComponent('%d','%d','%d','%d');";
		for(int i = 0;i<components.size();i++) {
			cmd = String.format(cmd, components.get(i).getRecipeID(),components.get(i).getCommodityID(),components.get(i).getNomNetWeight(),components.get(i).getTolerance());
			try {
				Connector.doUpdate(cmd);
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;

			}			
		}
		return 1;
	}
	
	
	
	/**
	 * Returns a recipe with parameter ID:
	 * @param id
	 * @return
	 */
	@Override
	public RecipeDTO getRecipe(int id) {
		String cmd = "getRecipe('%d');";
		cmd = String.format(cmd, id);
		
		try {
			ResultSet rs = Connector.doQuery(cmd);
			int recipe_ID = rs.getInt("recipe_ID");
			String recipe_Name = rs.getString("recipe_Name");
			List<RecipeCompDTO> components = getRecipeComponent(recipe_ID);
			return new RecipeDTO(recipe_ID,recipe_Name,components);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns a list of recipecomponents
	 */
	@Override
	public List<RecipeCompDTO> getRecipeComponent(int ID) {
		String cmd = "CALL getRecipeComponent(%d);";
		List<RecipeCompDTO> list = new ArrayList<>();
		cmd = String.format(cmd, ID);
		
		try {
			ResultSet rs = Connector.doQuery(cmd);
			while(rs.next()) {
				list.add(new RecipeCompDTO(rs.getInt("recipe_ID"),rs.getInt("commodity_ID"),rs.getInt("nom_net_weight"),rs.getInt("tolerance")));
			}
			return list;
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
				int recipe_ID = rs.getInt("recipe_ID");
				String recipe_Name = rs.getString("recipe_Name");
				List<RecipeCompDTO> components = getRecipeComponent(recipe_ID);
				list.add(new RecipeDTO(recipe_ID,recipe_Name,components));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	
	/**
	 * Finds a free recipeID that is not used. <br>
	 * It's possible to use the ID returned as a new ID.
	 * @return returns 0 if function fails <br>
	 * A number in the interval 1-99999999 if functions succeeds
	 */
	@Override
	public int findFreeRecipeID() {
		String cmd = "CALL findFreeRecipeID();";
		try {
			ResultSet rs = Connector.doQuery(cmd);
			return rs.getInt("max");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;

		}

	}
	
	

}
