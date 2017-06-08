package rest;

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

import dto.UserDTO;
@Path("user")
public class RESTUserDAO {

	@GET
	@Path("view/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") int id) {
		UserDTO test = new UserDTO(2, "test", "name", "lname", "ini", "cpr", "pass", "mail", null, 1);
		try {

		} catch (Exception e) {
			return null;
		}
		return Response.status(200).entity(test).build();
	}

	@PUT
	@Path("update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeStatus(String active, @PathParam("id") int id) {
		try {

		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(200).build();

	}
	
	@GET
	@Path("list/deactivatedUsers")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDeactivatedUsers() {
		try {

		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(195).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(UserDTO data) {
		try {

		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(201
				).build();

	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(UserDTO data) {
		try {

		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(200).build();
	}
	
	@GET
	@Path("view/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response UserExists(@PathParam("name") String name) {
		try {

		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(200).build();
	}



}
