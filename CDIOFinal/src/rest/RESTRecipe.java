package rest;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.RecipeDAO;
import dto.RecipeDTO;
import logic.RuleSetInterface;
import logic.RuleSet;

@Path("recipe")
public class RESTRecipe {
	
	 static final RuleSetInterface ruleset = new RuleSet();
	  static final RecipeDAO dao = new RecipeDAO();
	  
	  @PUT
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response createRecipe(RecipeDTO dto) {
		  try{
			  dao.create(dto);
			  return Response.status(Status.CREATED).build();
			  
		  } catch (Exception e) {
			  return Response.status(Status.UNAUTHORIZED).build();
		  } 
		  
	  }
	  
	  @POST
	  @Path("update/old_cpr={old_cpr : [0-9+]}")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response updateRecipe(RecipeDTO dto){
		  try {
			  dao.update(dto);
			  return Response.status(Status.ACCEPTED).build();
			  
		  } catch (Exception e) {
			  return Response.status(Status.NOT_FOUND).build(); 
		  }
	  }
	  
	  @GET
	  @Path("view/id={id : [0-9+]}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getRecipe (@PathParam("id") int id) {
		  try {
			  dao.get(id);
			  return Response.status(Status.OK).entity(dao.get(id)).build();
			  
		  } catch (Exception e) {
			  return Response.status(Status.NOT_FOUND).build();
		  }
	  }

}
