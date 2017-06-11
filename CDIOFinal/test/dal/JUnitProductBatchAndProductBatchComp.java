<<<<<<< HEAD
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
	/**
	 * Test Create and get
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

	
	@Test
	public void testgetList() {
		Connector.changeTestMode(true);
		ProductBatchInterfaceDAO productbatch = new ProductBatchDAO();
		RecipeInterfaceDAO recipe = new RecipeDAO();
		
		
		List<RecipeCompDTO> list1 = new ArrayList<>();
		List<RecipeCompDTO> list2 = new ArrayList<>();
		List<RecipeCompDTO> list3 = new ArrayList<>();


		RecipeCompDTO recipeCompDTO11 = new RecipeCompDTO(1,1,0.1,1.0);
		RecipeCompDTO recipeCompDTO12 = new RecipeCompDTO(1,2,0.2,1.0);
		RecipeCompDTO recipeCompDTO13 = new RecipeCompDTO(1,3,0.3,1.0);
		RecipeCompDTO recipeCompDTO14 = new RecipeCompDTO(1,4,0.4,1.0);
		RecipeCompDTO recipeCompDTO15 = new RecipeCompDTO(1,6,0.5,1.0);
		RecipeCompDTO recipeCompDTO16 = new RecipeCompDTO(1,7,0.6,1.0);
		RecipeCompDTO recipeCompDTO17 = new RecipeCompDTO(1,10,19.0,1.0);
		list1.add(recipeCompDTO11);
		list1.add(recipeCompDTO12);
		list1.add(recipeCompDTO13);
		list1.add(recipeCompDTO14);
		list1.add(recipeCompDTO15);
		list1.add(recipeCompDTO16);
		list1.add(recipeCompDTO17);

		
		
		RecipeCompDTO recipeCompDTO21 = new RecipeCompDTO(2,1,1,1.0);
		RecipeCompDTO recipeCompDTO22 = new RecipeCompDTO(2,2,2,1.0);
		RecipeCompDTO recipeCompDTO23 = new RecipeCompDTO(2,3,3,1.0);
		RecipeCompDTO recipeCompDTO24 = new RecipeCompDTO(2,4,4,1.0);
		RecipeCompDTO recipeCompDTO25 = new RecipeCompDTO(2,7,5,1.0);
		RecipeCompDTO recipeCompDTO26 = new RecipeCompDTO(2,8,6,1.0);
		RecipeCompDTO recipeCompDTO27 = new RecipeCompDTO(2,9,0.1,1.0);

		list2.add(recipeCompDTO21);
		list2.add(recipeCompDTO22);
		list2.add(recipeCompDTO23);
		list2.add(recipeCompDTO24);
		list2.add(recipeCompDTO25);
		list2.add(recipeCompDTO26);
		list2.add(recipeCompDTO27);
		
		RecipeCompDTO recipeCompDTO31 = new RecipeCompDTO(3,1,0.12,1.0);
		RecipeCompDTO recipeCompDTO32 = new RecipeCompDTO(3,2,0.3,1.0);
		RecipeCompDTO recipeCompDTO33 = new RecipeCompDTO(3,3,0.23,1.0);
		RecipeCompDTO recipeCompDTO34 = new RecipeCompDTO(3,4,0.123,1.0);
		RecipeCompDTO recipeCompDTO35 = new RecipeCompDTO(3,5,0.0009,1.0);
		RecipeCompDTO recipeCompDTO36 = new RecipeCompDTO(3,6,77.3905,1.0);
		RecipeCompDTO recipeCompDTO37 = new RecipeCompDTO(3,7,23.3413,1.0);

		list3.add(recipeCompDTO31);
		list3.add(recipeCompDTO32);
		list3.add(recipeCompDTO33);
		list3.add(recipeCompDTO34);
		list3.add(recipeCompDTO35);
		list3.add(recipeCompDTO36);
		list3.add(recipeCompDTO37);

		
		RecipeDTO recipe1 = new RecipeDTO(1,"Ananas Pizza",list1);
		RecipeDTO recipe2 = new RecipeDTO(2,"Salat Pizza",list2);
		RecipeDTO recipe3 = new RecipeDTO(3,"Skinke Pizza",list3);

		List<RecipeDTO> expectedRecipeList = new ArrayList<>();
		expectedRecipeList.add(recipe1);
		expectedRecipeList.add(recipe2);
		expectedRecipeList.add(recipe3);

		List<RecipeDTO> actualRecipeList = recipe.getRecipeList();
		
		for(int i = 0;i<actualRecipeList.size();i++) {
			compareRecipe(actualRecipeList.get(i),expectedRecipeList.get(i));
			
		}
		
		
	}
	
	private static void compareRecipe(RecipeDTO dto1,RecipeDTO dto2) {
		assertEquals(dto1.getRecipeID(),dto2.getRecipeID());
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
		assertEquals(dto1.getProductBatchID(),dto2.getProductBatchID());
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
=======
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
>>>>>>> branch 'master' of https://github.com/Gruppe25DTU/CDIOFinal
