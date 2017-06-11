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

import dao.CommodityDAO;
import dto.CommodityDTO;
import logic.validation.RuleSet;
import logic.validation.RuleSetInterface;

@Path("/commodity")
public class RESTCommodity {
	
	  static final CommodityDAO dao = new CommodityDAO();
	  
	  @PUT
	  @Path("/commodity/create}")
	  @Consumes(MediaType.APPLICATION_JSON)
		public Response createCommodity(CommodityDTO dto) {
			try {
				dao.create(dto);
				 return Response.status(Status.CREATED).build();

			} catch (Exception e) {
				return Response.status(Status.UNAUTHORIZED).build();
			}

		}
	  
	  @GET
	  @Path("/list/commodity")
      @Consumes(MediaType.APPLICATION_JSON)
		public Response getCommodityList() {
			try {
				dao.getList();
				 return Response.status(Status.OK).entity(dao.getList()).build();

			} catch (Exception e) {
				return Response.status(Status.NOT_FOUND).build();
			}
		}
	  
	  @GET
	  @Path("/view/id={id : [0-9+]}")
	  @Produces(MediaType.APPLICATION_JSON)
		public Response getCommodity(@PathParam("id") int id) {
			try {	
				dao.get(id);
			 return Response.status(Status.OK).entity(dao.get(id)).build();
			 
			} catch (Exception e) {
				return Response.status(Status.NOT_FOUND).build();
				
			} 
			
		}

}
