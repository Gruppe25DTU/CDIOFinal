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
@Path("users")
public class RESTUserDAO {

	@GET
	@Path("id/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUser(UserDTO data, @PathParam("id") int id) {
		try {

		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(200).build();
	}
  
  @GET
  public String get() {
    return "TEST";
  }


	@PUT
	@Path("{id}")
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
		return Response.status(200).build();

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
	@Path("name/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUser(UserDTO data, @PathParam("name") String name) {
		try {

		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(200).build();
	}



}
