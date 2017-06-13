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

import dto.IDTO;
import dto.UserDTO;
import logic.BLL;
import logic.CDIOException.*;
import logic.validation.*;

@Path("")
public class REST {
  @POST
  @Path("/{type : [a-zA-Z0-9]+}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createDTO(@PathParam("type") String dtoType, UserDTO dto) {
   int id;
   try {
   id = BLL.createDTO(dto, dtoType);
   } catch (DALException | DTOException e) {

    return Response.status(Status.NOT_ACCEPTABLE).build();

   } catch (UnauthorizedException e) {
    return Response.status(Status.UNAUTHORIZED).build();  
   }

   return Response.status(Status.CREATED).entity(id).build();
  }
  
  @PUT
  @Path("/{type : [a-zA-Z0-9]+}/cpr={old_cpr : [0-9]+}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateDTO(@PathParam("type") String dtoType, @PathParam("old_cpr") String old_cpr, UserDTO dto) {
   boolean updated;
   try {
   updated = BLL.updateUser(dto, old_cpr);
   } catch (DALException | DTOException e) {

    return Response.status(Status.NOT_ACCEPTABLE).build();

   } catch (UnauthorizedException e) {
    return Response.status(Status.UNAUTHORIZED).build();  
   }

   return Response.status(Status.CREATED).entity(updated).build();
  }
  
  
	@GET
	@Path("/validate/{type : [a-zA-Z0-9]+}/{field : [a-zA-Z0-9]+}/{value : [a-zA-Z0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response checkField(@PathParam("type")String dtoType,@PathParam("field") String field,@PathParam("value") String value) {

		try {
			DataCheckerInterface.checkField(dtoType, field, value);

		} catch (DALException | DTOException e) {

			return Response.status(Status.NOT_ACCEPTABLE).build();

		} catch (UnauthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();  
		}

		return Response.status(Status.OK).build();
	}

	@GET
	@Path("/{type : [a-zA-Z0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListDTO(@PathParam("type") String dtoType) {
	  System.out.println("GET - REST");
		try {
			BLL.getList(dtoType);
		} catch (DALException | DTOException e) {

			return Response.status(Status.NOT_ACCEPTABLE).build();
		}
		try {
			return Response.status(Status.OK).entity(BLL.getList(dtoType)).build();
		} catch (DTOException | DALException e) {
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}
	}

	@GET
	@Path("/{type : [a-zA-Z0-9]+}/{id : \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDTO(@PathParam("type") String dtoType, @PathParam("id") int id) {

		try {
			BLL.get(dtoType, id);
		} catch (DALException | DTOException e) {

			return Response.status(Status.NOT_ACCEPTABLE).build();
		}
		try {
			return Response.status(Status.OK).entity(BLL.get(dtoType, id)).build();
		} catch (DTOException | DALException e) {
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}
	}
}

