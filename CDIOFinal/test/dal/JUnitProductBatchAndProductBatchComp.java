package dal;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dao.ProductBatchDAO;
import dao.RecipeDAO;
import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;
import dto.RecipeCompDTO;
import dto.RecipeDTO;
import logic.CDIOException.DALException;

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
	/**
	 * Test Create and get
	 */
	@Test
	public void testCreate() throws DALException{
		Connector.changeTestMode(true);
		ProductBatchDAO productbatch = new ProductBatchDAO();
		RecipeDAO recipe = new RecipeDAO();
		
		RecipeCompDTO recipeCompDTO1 = new RecipeCompDTO(4, 1, 0.5, 0.1);
		RecipeCompDTO recipeCompDTO2 = new RecipeCompDTO(4, 2, 0.5, 0.1);
		RecipeCompDTO recipeCompDTO3 = new RecipeCompDTO(4, 3, 0.5, 0.1);
		RecipeCompDTO recipeCompDTO4 = new RecipeCompDTO(4, 4, 0.5, 0.1);

		RecipeCompDTO[] recipeCompList = { 
		    recipeCompDTO1,
		    recipeCompDTO2,
		    recipeCompDTO3,
		    recipeCompDTO4
		};
		
		ProductBatchCompDTO productbatchcompDTO1 = new ProductBatchCompDTO(4,1,30.01,20.01,1);		
    ProductBatchCompDTO[] productBatchCompList = {productbatchcompDTO1};
		
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

	
	@Test
	public void testgetList() throws DALException{
		Connector.changeTestMode(true);
		ProductBatchDAO productbatch = new ProductBatchDAO();
		RecipeDAO recipe = new RecipeDAO();


		RecipeCompDTO recipeCompDTO11 = new RecipeCompDTO(1,1,0.1,1.0);
		RecipeCompDTO recipeCompDTO12 = new RecipeCompDTO(1,2,0.2,1.0);
		RecipeCompDTO recipeCompDTO13 = new RecipeCompDTO(1,3,0.3,1.0);
		RecipeCompDTO recipeCompDTO14 = new RecipeCompDTO(1,4,0.4,1.0);
		RecipeCompDTO recipeCompDTO15 = new RecipeCompDTO(1,6,0.5,1.0);
		RecipeCompDTO recipeCompDTO16 = new RecipeCompDTO(1,7,0.6,1.0);
		RecipeCompDTO recipeCompDTO17 = new RecipeCompDTO(1,10,19.0,1.0);
		
		
		RecipeCompDTO[] list1 = {
		    recipeCompDTO11,
		    recipeCompDTO12,
		    recipeCompDTO13,
		    recipeCompDTO14,
		    recipeCompDTO15,
		    recipeCompDTO16,
		    recipeCompDTO17
		};
		
		
		RecipeCompDTO recipeCompDTO21 = new RecipeCompDTO(2,1,1,1.0);
		RecipeCompDTO recipeCompDTO22 = new RecipeCompDTO(2,2,2,1.0);
		RecipeCompDTO recipeCompDTO23 = new RecipeCompDTO(2,3,3,1.0);
		RecipeCompDTO recipeCompDTO24 = new RecipeCompDTO(2,4,4,1.0);
		RecipeCompDTO recipeCompDTO25 = new RecipeCompDTO(2,7,5,1.0);
		RecipeCompDTO recipeCompDTO26 = new RecipeCompDTO(2,8,6,1.0);
		RecipeCompDTO recipeCompDTO27 = new RecipeCompDTO(2,9,7,1.0);

    RecipeCompDTO[] list2 = {
        recipeCompDTO21,
        recipeCompDTO22,
        recipeCompDTO23,
        recipeCompDTO24,
        recipeCompDTO25,
        recipeCompDTO26,
        recipeCompDTO27
    };
		
		RecipeCompDTO recipeCompDTO31 = new RecipeCompDTO(3,1,0.12,1.0);
		RecipeCompDTO recipeCompDTO32 = new RecipeCompDTO(3,2,0.3,1.0);
		RecipeCompDTO recipeCompDTO33 = new RecipeCompDTO(3,3,0.23,1.0);
		RecipeCompDTO recipeCompDTO34 = new RecipeCompDTO(3,4,0.123,1.0);
		RecipeCompDTO recipeCompDTO35 = new RecipeCompDTO(3,5,0.0009,1.0);
		RecipeCompDTO recipeCompDTO36 = new RecipeCompDTO(3,6,77.3905,1.0);
		RecipeCompDTO recipeCompDTO37 = new RecipeCompDTO(3,7,23.3413,1.0);

    RecipeCompDTO[] list3 = {
        recipeCompDTO31,
        recipeCompDTO32,
        recipeCompDTO33,
        recipeCompDTO34,
        recipeCompDTO35,
        recipeCompDTO36,
        recipeCompDTO37
    };

		
		RecipeDTO recipe1 = new RecipeDTO(1,"Ananas Pizza",list1);
		RecipeDTO recipe2 = new RecipeDTO(2,"Salat Pizza",list2);
		RecipeDTO recipe3 = new RecipeDTO(3,"Skinke Pizza",list3);

		
		RecipeCompDTO recipeCompDTO1 = new RecipeCompDTO(4, 1, 0.5, 0.1);
		RecipeCompDTO recipeCompDTO2 = new RecipeCompDTO(4, 2, 0.5, 0.1);
		RecipeCompDTO recipeCompDTO3 = new RecipeCompDTO(4, 3, 0.5, 0.1);
		RecipeCompDTO recipeCompDTO4 = new RecipeCompDTO(4, 4, 0.5, 0.1);


		RecipeCompDTO[] recipeCompList = {
		    recipeCompDTO1,
		    recipeCompDTO2,
		    recipeCompDTO3,
		    recipeCompDTO4
		};
		RecipeDTO recipeDTO = new RecipeDTO(4,"Dej",recipeCompList);

		
		RecipeDTO[] expectedRecipeList = {
		    recipe1,
		    recipe2,
		    recipe3,
		    recipeDTO
		};
		RecipeDTO[] actualRecipeList = recipe.getRecipeList();
		
		for(int i = 0;i<actualRecipeList.length;i++) {
			compareRecipe(actualRecipeList[i],expectedRecipeList[i]);
			
		}
		
		
	}
	
	private static void compareRecipe(RecipeDTO dto1,RecipeDTO dto2) {
		assertEquals(dto1.getId(),dto2.getId());
		assertEquals(dto1.getRecipeName(),dto2.getRecipeName());
		
		for(int i = 0;i<dto1.getComponents().length;i++) {
			compareRecipeComp(dto1.getComponents()[i],dto2.getComponents()[i]);
		}

	}
	
	private static void compareRecipeComp(RecipeCompDTO dto1, RecipeCompDTO dto2) {
		assertEquals(dto1.getCommodityID(),dto2.getCommodityID());
		assertEquals(Double.toString(dto1.getNomNetWeight()),Double.toString(dto2.getNomNetWeight()));
		System.out.println(Double.toString(dto1.getNomNetWeight()) + " " + Double.toString(dto2.getNomNetWeight()));
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
		
		for(int i = 0;i<dto1.getComponents().length;i++) {
			compareProductBatchComp(dto1.getComponents()[i], dto2.getComponents()[i]);
		}
		


	}
	
	private static void compareProductBatchComp(ProductBatchCompDTO dto1, ProductBatchCompDTO dto2) {
		assertEquals(dto1.getCommodityBatchID(),dto2.getCommodityBatchID());
		assertEquals(Double.toString(dto1.getNet()),Double.toString(dto2.getNet()));
		assertEquals(Double.toString(dto1.getTara()),Double.toString(dto2.getTara()));
		assertEquals(dto1.getProductBatchID(),dto2.getProductBatchID());
		assertEquals(dto1.getUserID(),dto2.getUserID());

	}
	
	
	
}
