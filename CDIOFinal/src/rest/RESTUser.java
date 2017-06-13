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

import dao.UserDAO;
import dto.IDTO;
import dto.UserDTO;
import logic.BLL;
import logic.CDIOException.DALException;
import logic.CDIOException.DTOException;
import logic.CDIOException.UnauthorizedException;
import logic.validation.RuleSet;
import logic.validation.RuleSetInterface;

@Path("/user")
public class RESTUser {

	@POST
	@Path("/{type : [a-zA-Z0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createDTO(@PathParam("type") String type, IDTO dto) {

		try {
			BLL.createDTO(dto);
		} catch (DALException | DTOException e) {

			return Response.status(Status.NOT_ACCEPTABLE).build();

		} catch (UnauthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();  
		}

		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("/{type : [a-zA-Z0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDTO(@PathParam("type") String type, IDTO dto) {

		try {
			BLL.updateDTO(dto);
		} catch (DALException | DTOException e) {

			return Response.status(Status.NOT_ACCEPTABLE).build();

		} catch (UnauthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();  
		}

		return Response.status(Status.CREATED).build();

	}

}
