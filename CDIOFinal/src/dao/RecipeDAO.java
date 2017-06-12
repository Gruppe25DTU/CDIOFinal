package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dto.RecipeCompDTO;
import dto.RecipeDTO;

public class RecipeDAO {

	/**
	 * Creates a recipe. <br>
	 * Returns 0 if unable to do so.
	 * @param dto
	 * @return
	 */
	
	public static int create(RecipeDTO dto) {
		String cmd = "CALL addRecipe('%d','%s')";
		cmd = String.format(cmd, dto.getId(),dto.getName());
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
	
	public static int createRecipeComponent(List<RecipeCompDTO> components) {

		for(RecipeCompDTO dto : components) {
			String cmd = "CALL addRecipeComponent('%d','%d','%s','%s');";

			int recipeID = dto.getRecipeID();
			int commodityID = dto.getCommodityID();
			String nom_net_weight = Double.toString(dto.getNomNetWeight());
			String tolerance = Double.toString(dto.getTolerance());

			cmd = String.format(cmd, recipeID,commodityID,nom_net_weight,tolerance);
			try {
				Connector.doUpdate(cmd);
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;

			}		
			finally {
				try {
					Connector.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return 1;
	}



	/**
	 * Returns a recipe with parameter ID:
	 * @param id
	 * @return
	 */
	
	public RecipeDTO getRecipe(int id) {
		String cmd = "CALL getRecipe('%d');";
		cmd = String.format(cmd, id);

		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			rs.next();
			int recipe_ID = rs.getInt("recipe_ID");
			String recipe_Name = rs.getString("recipe_Name");
			List<RecipeCompDTO> components = getRecipeComponent(recipe_ID);
			return new RecipeDTO(recipe_ID,recipe_Name,components);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
	 * Returns a list of recipecomponents
	 */
	
	public List<RecipeCompDTO> getRecipeComponent(int ID) {
		String cmd = "CALL getRecipeComponent('%d');";
		List<RecipeCompDTO> list = new ArrayList<>();
		cmd = String.format(cmd, ID);

		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
			while(rs.next()) {
				list.add(new RecipeCompDTO(rs.getInt("recipe_ID"),rs.getInt("commodity_ID"),rs.getDouble("nom_net_weight"),rs.getDouble("tolerance")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
	 * Returns a list over every existing recipes
	 */
	
	public List<RecipeDTO> getRecipeList() {
		String cmd = "CALL getRecipeList();";
		List<RecipeDTO> list = new ArrayList<RecipeDTO>();

		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return null;
			}
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
		finally {
			try {
				Connector.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}


	/**
	 * Finds a free recipeID that is not used. <br>
	 * It's possible to use the ID returned as a new ID.
	 * @return returns 0 if function fails <br>
	 * A number in the interval 1-99999999 if functions succeeds
	 */
	
	public int findFreeRecipeID() {
		String cmd = "CALL findFreeRecipeID();";
		try {
			ResultSet rs = Connector.doQuery(cmd);
			if(rs == null) {
				return 0;
			}
			rs.next();
			return rs.getInt("max");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;

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
