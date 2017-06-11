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
import logic.validation.RuleSet;
import logic.validation.RuleSetInterface;

@Path("/recipe")
public class RESTRecipe {

	static final RecipeDAO dao = new RecipeDAO();

	@PUT
	@Path("/recipe/create}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createRecipe(RecipeDTO dto) {
		try{
			dao.create(dto);
			return Response.status(Status.CREATED).build();

		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		} 

	}
}
