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

import dao.SupplierDAO;
import dto.SupplierDTO;
import logic.RuleSetInterface;
import logic.RuleSet;

@Path("supllier")
public class RESTSupllier {
	
	 static final RuleSetInterface ruleset = new RuleSet();
	  static final SupplierDAO dao = new SupplierDAO();
	  
	  
	  @PUT
	  @Consumes(MediaType.APPLICATION_JSON)
		public Response createSupllier(SupplierDTO dto) {
			try {
				dao.create(dto); 
				 return Response.status(Status.CREATED).build();

			} catch (Exception e) {
				return Response.status(Status.UNAUTHORIZED).build();
			}
			
		}
	  @POST
	  @Path("update/name={name : [a-zA-Z]+}")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response updateSupplier(int ID, @PathParam("name") String name) {
		  try { 
			  dao.update(ID, name);
			  return Response.status(Status.ACCEPTED).build();
			  		 
		  }catch (Exception e) {
				return Response.status(Status.UNAUTHORIZED).build();
			}
	  }
	  
	  @DELETE
	  public Response deleteSupplier(int ID) {
		  try {
			  dao.delete(ID);
			  return Response.status(Status.OK).build();
			  
		  } catch (Exception e) {
			  return Response.status(Status.NOT_FOUND).build();
		  }
		  
	  }
	  
	  @GET
	  @Path("view/id={id : [0-9+]}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response getSupplier(@PathParam("id") int ID) {
		  try {
			  dao.getSupplier(ID);
			  return Response.status(Status.OK).entity(dao.getSupplier(ID)).build();
			  
		  } catch (Exception e) {
			  return Response.status(Status.NOT_FOUND).build();
		  }
	  }
}
