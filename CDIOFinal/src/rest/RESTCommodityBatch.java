package rest;

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

import dao.CommodityBatchDAO;
import dto.CommodityBatchDTO;
import logic.RuleSetInterface;
import logic.RuleSet;

@Path("commodity")
public class RESTCommodityBatch {
	
	 static final RuleSetInterface ruleset = new RuleSet();
	  static final CommodityBatchDAO dao = new CommodityBatchDAO();
	  
	  @POST
	  @Path("update/id={id : [0-9+]}")
	  @Produces(MediaType.APPLICATION_JSON)
		public Response changeAmount(int amount, @PathParam("id") int id) {
			try {
				dao.changeAmount(id, amount);
				return Response.status(Status.ACCEPTED).build();
			} 	
			catch (Exception e) {
				return Response.status(Status.NOT_FOUND).build();
			}

		}
	  
	  @PUT
	  @Consumes(MediaType.APPLICATION_JSON)
		public Response createCommodity(CommodityBatchDTO dto) {
			try {
				dao.create(dto);
				 return Response.status(Status.CREATED).build();

			} catch (Exception e) {
				return Response.status(Status.UNAUTHORIZED).build();
			}

		}
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_JSON)
		public Response updateUser(CommodityBatchDTO dto) {
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
		public Response getUser(@PathParam("id") int id) {
			try {	
				dao.get(id);
			 return Response.status(Status.OK).entity(dao.get(id)).build();
			 
			} catch (Exception e) {
				return Response.status(Status.NOT_FOUND).build();
				
			} 
			
		}

}
