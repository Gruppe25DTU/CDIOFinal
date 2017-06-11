package dal;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dao.ProductBatchDAO;
import dao.RecipeDAO;
import daoInterface.ProductBatchInterfaceDAO;
import daoInterface.RecipeInterfaceDAO;
import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;
import dto.RecipeCompDTO;
import dto.RecipeDTO;

public class JUnitProductBatchAndProductBatchComp {

	
	/*
	int create(ProductBatchDTO dto);
	boolean changeStatus(int id, int status);
	void print(int id);
	ProductBatchDTO get(int id);
	List<ProductBatchDTO> getList();
	boolean addComponent(ProductBatchCompDTO component);
	RecipeCompDTO getNonWeighedComp(int pbid);
	int findFreeProductBatchID();
	boolean setStartdate(ProductBatchDTO dto);
	boolean setStopdate(ProductBatchDTO dto); 
	int recipeID, String name, List<RecipeCompDTO> components
	 */
	@Test
	public void testCreate() {
		Connector.changeTestMode(true);
		ProductBatchInterfaceDAO productbatch = new ProductBatchDAO();
		RecipeInterfaceDAO recipe = new RecipeDAO();
		
		List<RecipeCompDTO> recipeCompList = new ArrayList<>();
		RecipeCompDTO recipeCompDTO1 = new RecipeCompDTO(4, 1, 0.5, 0.1);
		RecipeCompDTO recipeCompDTO2 = new RecipeCompDTO(4, 2, 0.5, 0.1);
		RecipeCompDTO recipeCompDTO3 = new RecipeCompDTO(4, 3, 0.5, 0.1);
		RecipeCompDTO recipeCompDTO4 = new RecipeCompDTO(4, 4, 0.5, 0.1);

		recipeCompList.add(recipeCompDTO1);
		recipeCompList.add(recipeCompDTO2);
		recipeCompList.add(recipeCompDTO3);
		recipeCompList.add(recipeCompDTO4);
		
		List<ProductBatchCompDTO> productBatchCompList = new ArrayList<>();
		ProductBatchCompDTO productbatchcompDTO1 = new ProductBatchCompDTO(4,1,30.01,20.01,1);
		productBatchCompList.add(productbatchcompDTO1);
		
		
		RecipeDTO recipeDTO = new RecipeDTO(4,"Dej",recipeCompList);
		Timestamp startdate = Timestamp.valueOf("2017-06-02 16:23:46");
		ProductBatchDTO productbatchDTO = new ProductBatchDTO(4, 0, 4, startdate,null,productBatchCompList);

		//Create the recipe. Both recipe and it's components
		recipe.create(recipeDTO);
		
		productbatch.create(productbatchDTO);
		for(ProductBatchCompDTO dto : productBatchCompList) {
			productbatch.addComponent(dto);

		}
		
		RecipeDTO expectedRecipe = recipe.getRecipe(4);
		ProductBatchDTO expectedProductBatch = productbatch.get(4);
		
		compareRecipe(recipeDTO, expectedRecipe);
		compareProductBatch(productbatchDTO, expectedProductBatch);
	}

	private static void compareRecipe(RecipeDTO dto1,RecipeDTO dto2) {
		assertEquals(dto1.getId(),dto2.getId());
		assertEquals(dto1.getName(),dto2.getName());
		
		for(int i = 0;i<dto1.getComponents().size();i++) {
			compareRecipeComp(dto1.getComponents().get(i),dto2.getComponents().get(i));
		}

	}
	
	private static void compareRecipeComp(RecipeCompDTO dto1, RecipeCompDTO dto2) {
		assertEquals(dto1.getCommodityID(),dto2.getCommodityID());
		assertEquals(Double.toString(dto1.getNomNetWeight()),Double.toString(dto2.getNomNetWeight()));
		assertEquals(Double.toString(dto1.getTolerance()),Double.toString(dto2.getTolerance()));
		assertEquals(dto1.getRecipeID(),dto2.getRecipeID());

	}
	
	private static void compareProductBatch(ProductBatchDTO dto1, ProductBatchDTO dto2) {
		assertEquals(dto1.getRecipeID(),dto2.getRecipeID());
		assertEquals(dto1.getId(),dto2.getId());
		assertEquals(dto1.getStatus(),dto2.getStatus());
		if(dto1.getStartDate() == null && dto2.getStartDate() == null) {
			assertEquals(1,1);
		}
		assertEquals(dto1.getStartDate(),dto2.getStartDate());
		if(dto1.getEndDate() == null && dto2.getEndDate() == null) {
			assertEquals(1,1);
		}
		assertEquals(dto1.getEndDate(),dto2.getEndDate());
		
		for(int i = 0;i<dto1.getComponents().size();i++) {
			compareProductBatchComp(dto1.getComponents().get(i), dto2.getComponents().get(i));
		}
		


	}
	
	private static void compareProductBatchComp(ProductBatchCompDTO dto1, ProductBatchCompDTO dto2) {
		assertEquals(dto1.getcommodityBatchID(),dto2.getcommodityBatchID());
		assertEquals(Double.toString(dto1.getNet()),Double.toString(dto2.getNet()));
		assertEquals(Double.toString(dto1.getTara()),Double.toString(dto2.getTara()));
		assertEquals(dto1.getProductBatchID(),dto2.getProductBatchID());
		assertEquals(dto1.getuserID(),dto2.getuserID());

	}
	
	
	
}
