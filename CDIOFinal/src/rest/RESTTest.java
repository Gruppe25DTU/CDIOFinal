package rest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mysql.fabric.xmlrpc.base.Array;

import dto.CommodityBatchDTO;
import dto.CommodityDTO;
import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;
import dto.RecipeCompDTO;
import dto.RecipeDTO;
import dto.SupplierDTO;
import dto.UserDTO;

@Path("/test")
public class RESTTest {

  public static ArrayList<CommodityBatchDTO> commodityBatches = new ArrayList<CommodityBatchDTO>() {{
    this.add(new CommodityBatchDTO(1, 1, 20));
    this.add(new CommodityBatchDTO(2, 2, 12));
    this.add(new CommodityBatchDTO(3, 3, 17));
    this.add(new CommodityBatchDTO(4, 4, 33));
    this.add(new CommodityBatchDTO(5, 5, 2));
    this.add(new CommodityBatchDTO(6, 1, 2));
    this.add(new CommodityBatchDTO(7, 3, 10));
  }};
  
  public static ArrayList<ProductBatchCompDTO> productBatchComponents1 = new ArrayList<ProductBatchCompDTO>(){{
    this.add(new ProductBatchCompDTO(1, 1, 1.3, 4.3, 1));
    this.add(new ProductBatchCompDTO(2, 6, 1.9, 21, 1));
    this.add(new ProductBatchCompDTO(3, 3, 1.3, 13.02, 1));
    this.add(new ProductBatchCompDTO(4, 4, 3.2, 8.3, 1));
  }};
  
  public static ArrayList<ProductBatchDTO> productBatches = new ArrayList<ProductBatchDTO>(){{
    this.add(new ProductBatchDTO(1, 2, 2, new Timestamp(Calendar.getInstance().getTime().getTime()), new Timestamp(Calendar.getInstance().getTime().getTime()+1000), productBatchComponents1));
  }};
  
  public static ArrayList<String> roles = new ArrayList<String>(){{this.add("admin");}};
  public static ArrayList<UserDTO> users = new ArrayList<UserDTO>() {{
    this.add(new UserDTO(1, "yamus", "yanki", "muslu", "ym", "123456-7890", "test", "ym@ym.dk", roles, 1));
    this.add(new UserDTO(2, "meme", "anders", "and", "aa", "098765-4321", "test", "aa@dd.dk", new ArrayList(), 1));
    this.add(new UserDTO(3, "water", "tight", "toast", "tt", "767656-9889", "test", "tt@tt.dk", roles, 1));
  }};

  public static ArrayList<CommodityDTO> commodities = new ArrayList<CommodityDTO>() {{
    this.add(new CommodityDTO(1, "Salt", 2));
    this.add(new CommodityDTO(2, "iWater", 4));
    this.add(new CommodityDTO(3, "Melon", 2));
    this.add(new CommodityDTO(4, "Oil", 1));
    this.add(new CommodityDTO(5, "Dirt", 1));
  }};

  public static ArrayList<SupplierDTO> suppliers = new ArrayList<SupplierDTO>() {{
    this.add(new SupplierDTO(1, "Mr. None"));
    this.add(new SupplierDTO(2, "Salty Fruits Inc."));
    this.add(new SupplierDTO(3, "Nills"));
    this.add(new SupplierDTO(4, "Apple"));
  }};
  
  static ArrayList<RecipeCompDTO> recipeList1 = new ArrayList<RecipeCompDTO>() {{
    this.add(new RecipeCompDTO(1, 2, 4.0, 0.1));
    this.add(new RecipeCompDTO(1, 3, 11.0, 0.21));
  }};

  static ArrayList<RecipeCompDTO> recipeList2 = new ArrayList<RecipeCompDTO>() {{
    this.add(new RecipeCompDTO(2, 3, 13.0, 0.01));
    this.add(new RecipeCompDTO(2, 1, 22.0, 0.44));
    this.add(new RecipeCompDTO(2, 4, 5.0, 0.25));
  }};
  
  public static ArrayList<RecipeDTO> recipes = new ArrayList<RecipeDTO>() {{
    this.add(new RecipeDTO(1, "iMelon", recipeList1));
    this.add(new RecipeDTO(2, "Melon Salt", recipeList2));
  }};

  @GET
  @Path("/tester")
  public String test() {
    return "WOP";
  }
  
  @GET
  @Path("/commodity/id/{id : \\d+}")
  @Produces(MediaType.APPLICATION_JSON)
  public CommodityDTO getCommodity(@PathParam("id") int id) {
    return commodities.get(id-1);
  }
  
  @GET
  @Path("/commoditybatch/id/{id : \\d+}")
  @Produces(MediaType.APPLICATION_JSON)
  public CommodityBatchDTO getCommodityBatch(@PathParam("id") int id) {
    return commodityBatches.get(id-1);
  }
  
  @GET
  @Path("/productbatch/id/{id : \\d+}")
  @Produces(MediaType.APPLICATION_JSON)
  public ProductBatchDTO getProductBatch(@PathParam("id") int id) {
    return productBatches.get(id-1);
  }
  
  @GET
  @Path("/recipe/id/{id : \\d+}")
  @Produces(MediaType.APPLICATION_JSON)
  public RecipeDTO getRecipe(@PathParam("id") int id) {
    return recipes.get(id-1);
  }
  
  @GET
  @Path("/supplier/id/{id : \\d+}")
  @Produces(MediaType.APPLICATION_JSON)
  public SupplierDTO getSupplier(@PathParam("id") int id) {
    return suppliers.get(id-1);
  }
  
  @GET
  @Path("/user/id/{id : \\d+}")
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO getUser(@PathParam("id") int id) {
    return users.get(id-1);
  }
  
  @GET
  @Path("/user/name/{name : [a-zA-Z0-9]+}")
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO getUserByName(@PathParam("name") String name) {
    for (UserDTO user : users) {
      if (user.getFirstName().contains(name)) {
        return user;
      }
    }
    return null;
  }
  
  @POST
  @Path("/user/update")
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateUser(UserDTO dto) {
    users.set(dto.getId()-1, dto);
  }
  
  @GET
  @Path("/user/request/getAvailableID")
  public int getAvailableID() {
    return users.size() + 1;
  }
  
  @GET
  @Path("/CU/validity/userName/{name : [a-zA-Z0-9]+}")
  public Response checkName(@PathParam("name") String name) {
    if (name.length() > 5) {
      return Response.status(Status.OK).build();
    }
    return Response.status(Status.NOT_FOUND).build();
  }
  
  @GET
  @Path("/CU/validity/{field : .+}/{input : [a-zA-Z0-9]+}")
  public Response checkInput(@PathParam("input") String input) {
    if (input.length() > 2) {
      return Response.status(Status.OK).build();
    }
    return Response.status(Status.NOT_FOUND).build();
  }
  
  @PUT
  @Path("/user")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response create(UserDTO dto) {
    if (users.add(dto)) {
      dto.setId(users.indexOf(dto) + 1);
      return Response.status(Status.OK).entity(dto.getId()).build();
    }
    return Response.status(Status.NOT_ACCEPTABLE).build();
  }
}
