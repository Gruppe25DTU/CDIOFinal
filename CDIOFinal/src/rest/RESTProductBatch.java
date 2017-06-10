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

import dao.ProductBatchDAO;
import dto.ProductBatchDTO;
import logic.RuleSetInterface;
import logic.RuleSet;

@Path("/productbatch")
public class RESTProductBatch {

	static final ProductBatchDAO dao = new ProductBatchDAO();

	@PUT
	@Path("/ProductBatch/create}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProductBatch(ProductBatchDTO dto) {
		try {
			dao.create(dto);
			return Response.status(Status.CREATED).build();
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}

	@PUT
	@Path("/ProductBatch/print}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response printProductBatch(int id) {
		try {
			dao.print(id);

		} catch(Exception e) {

		}
		return null;
	}

	@GET
	@Path("/view/id={id : [0-9+]}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductBatch(@PathParam("id") int id) {
		try {	
			dao.get(id);
			return Response.status(Status.OK).entity(dao.get(id)).build();

		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();

		} 

	}

	@GET
	@Path("/list/productbatch")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getProductBatchList() {
		try {
			dao.getList();
			return Response.status(Status.OK).entity(dao.getList()).build();

		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
