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
}

