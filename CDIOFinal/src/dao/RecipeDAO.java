package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dto.RecipeCompDTO;
import dto.RecipeDTO;
import logic.CDIOException.*;

public class RecipeDAO {

	/**
	 * Creates a recipe. <br>
	 * Returns 0 if unable to do so.
	 * @param dto
	 * @return
	 */

	public static int create(RecipeDTO dto) throws DALException{
    Connector conn = new Connector();
		String cmd = "CALL addRecipe('%s')";
		cmd = String.format(cmd, dto.getRecipeName());
		int ID;
		try {
			ResultSet rs  = conn.doQuery(cmd);
			if (!rs.next()) {
			  throw new EmptyResultSetException();
			}
			ID = rs.getInt("ID");
			if(dto.getComponents() != null) {
				for(RecipeCompDTO component : dto.getComponents()) {
					component.setRecipeID(ID);
				}
			}
			createRecipeComponent(dto.getComponents());
			return ID;
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new DALException(e);
      }
    }
	}

	/**
	 * Adds list of recipeComponents
	 */

	public static void createRecipeComponent(RecipeCompDTO[] components) throws DALException{
    Connector conn = new Connector();
		for(RecipeCompDTO dto : components) {
			String cmd = "CALL addRecipeComponent('%d','%d','%s','%s');";

			int recipeID = dto.getRecipeID();
			int commodityID = dto.getCommodityID();
			String nom_net_weight = Double.toString(dto.getNomNetWeight());
			String tolerance = Double.toString(dto.getTolerance());

			cmd = String.format(cmd, recipeID,commodityID,nom_net_weight,tolerance);
			try {
				conn.doUpdate(cmd);
			} catch (SQLException e) {
				throw new DALException(e);
			}
		} try {
      conn.close();
    } catch (SQLException e) {
      throw new DALException(e);
    }
	}


	public static RecipeDTO get(Integer id) throws DALException {
	  return getRecipe(id);
	}

	/**
	 * Returns a recipe with parameter ID:
	 * @param id
	 * @return
	 */
	
	public static RecipeDTO getRecipe(int id) throws DALException{
    Connector conn = new Connector();
		String cmd = "CALL getRecipe('%d');";
		cmd = String.format(cmd, id);

		try {
			ResultSet rs = conn.doQuery(cmd);
			if(!rs.next()) {
			  throw new EmptyResultSetException();
			}
			int recipe_ID = rs.getInt("recipe_ID");
			String recipe_Name = rs.getString("recipe_Name");
			RecipeCompDTO[] components = getRecipeComponent(recipe_ID);
			return new RecipeDTO(recipe_ID,recipe_Name,components);
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new DALException(e);
      }
    }
	}

	/**
	 * Returns a list of recipecomponents
	 */

	public static RecipeCompDTO[] getRecipeComponent(Integer ID) throws DALException{
    Connector conn = new Connector();
		String cmd = "CALL getRecipeComponent('%d');";
		List<RecipeCompDTO> list = new ArrayList<>();
		cmd = String.format(cmd, ID);

		try {
			ResultSet rs = conn.doQuery(cmd);
			if (rs == null) {
			  return (RecipeCompDTO[]) list.toArray(new RecipeCompDTO[list.size()]);
			}
			while(rs.next()) {
				list.add(new RecipeCompDTO(rs.getInt("recipe_ID"),rs.getInt("commodity_ID"),rs.getDouble("nom_net_weight"),rs.getDouble("tolerance")));
			}
			if (list.isEmpty()) {
			  throw new EmptyResultSetException();
			}
			return (RecipeCompDTO[]) list.toArray(new RecipeCompDTO[list.size()]);
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new DALException(e);
      }
    }
	}
	
	public static RecipeDTO[] getList() throws DALException {
	  return getRecipeList();
	}
	
	/**
	 * Returns a list over every existing recipes
	 */

	public static RecipeDTO[] getRecipeList() throws DALException{
    Connector conn = new Connector();
		String cmd = "CALL getRecipeList();";
		List<RecipeDTO> list = new ArrayList<RecipeDTO>();
		try {
			ResultSet rs = conn.doQuery(cmd);
			while (rs.next()) {
				int recipe_ID = rs.getInt("recipe_ID");
				String recipe_Name = rs.getString("recipe_Name");
				try {
				  RecipeCompDTO[] components = getRecipeComponent(recipe_ID);
				  list.add(new RecipeDTO(recipe_ID,recipe_Name,components));
				} catch (EmptyResultSetException e) {
				  continue;
				}
			}
			if (list.isEmpty()) {
			  throw new EmptyResultSetException();
			}
			return (RecipeDTO[]) list.toArray(new RecipeDTO[list.size()]);
		} catch (SQLException e) {
			throw new DALException(e);
		} finally {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new DALException(e);
      }
    }
	}
}
