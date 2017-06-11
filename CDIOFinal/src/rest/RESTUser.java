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
import dto.UserDTO;
import logic.validation.RuleSet;
import logic.validation.RuleSetInterface;

@Path("/user")
public class RESTUser {

	static final UserDAO dao = new UserDAO();


	@GET
	@Path("/view/id={id : [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") int ID) {
		try {	
			dao.getUser(ID);
			return Response.status(Status.OK).entity(dao.getUser(ID)).build();

		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();

		} 

	}

	@POST
	@Path("/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeStatus(boolean active, @PathParam("id") int id) {
		try {
			dao.changeStatus(id, active);
			return Response.status(Status.ACCEPTED).build();
		} 	
		catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}

	}

	@GET
	@Path("/list/deactivatedUsers")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDeactivatedUsers() {
		try {
			dao.getDeactiveUsers();
			return Response.status(Status.OK).entity(dao.getDeactiveUsers()).build();

		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("/user/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(UserDTO dto) {
		try {
			dao.create(dto); 
			return Response.status(Status.CREATED).build();

		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}

	@POST
	@Path("/update/{old_cpr}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(UserDTO dto, @PathParam("old_cpr") String old_cpr) {
		try {
			dao.update(dto, old_cpr);
			return Response.status(Status.ACCEPTED).build();

		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/view/name={name : [a-zA-Z]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response UserExists(@PathParam("name") String name) {
		try {
			dao.userExists(name);
			return Response.status(Status.OK).entity(dao.userExists(name)).build();

		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
