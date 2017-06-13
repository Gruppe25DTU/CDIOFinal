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

public class RESTUser {

	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createDTO(UserDTO dto) {
		int id;
		try {
			id = BLL.createDTO(dto, "user");
		} catch (DALException | DTOException e) {

			return Response.status(Status.NOT_ACCEPTABLE).build();

		} catch (UnauthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();  
		}

		return Response.status(Status.CREATED).entity(id).build();
	}

	@PUT
	@Path("/user/cpr={old_cpr : [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDTO(@PathParam("old_cpr") String old_cpr, UserDTO dto) {
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
}
