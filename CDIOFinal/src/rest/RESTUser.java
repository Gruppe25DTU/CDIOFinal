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
import dto.CommodityDTO;
import dto.IDTO;
import dto.UserDTO;
import logic.BLL;
import logic.CDIOException.*;
import logic.validation.RuleSet;
import logic.validation.RuleSetInterface;

@Path("user")
public class RESTUser {

  @GET
  @Path("/list")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getListDTO() {
    try {
      UserDTO[] dto = BLL.getUser();
      return Response.status(Status.OK).entity(dto).build();
    } catch (DALException e) {
      e.printStackTrace();
      return Response.status(Status.NOT_ACCEPTABLE).build();
    } catch (SessionException e) {
      e.printStackTrace();
      return Response.status(Status.UNAUTHORIZED).build();  
    }
  }

  @GET
  @Path("/deactivate/id={id : \\d+}")
  public Response deactivateUser(@PathParam("id") int id) {
    try {
      BLL.changeStatus(id, false);
      return Response.status(Status.OK).build();
    } catch (DALException e) {
      e.printStackTrace();
      return Response.status(Status.NOT_ACCEPTABLE).build();
    }
  }
  
  @GET
  @Path("/activate/id={id : \\d+}")
  public Response activateUser(@PathParam("id") int id) {
    try {
      BLL.changeStatus(id, true);
      return Response.status(Status.OK).build();
    } catch (DALException e) {
      e.printStackTrace();
      return Response.status(Status.NOT_ACCEPTABLE).build();
    }
  }

  @GET
  @Path("/id={id : \\d+}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDTO(@PathParam("id") int id) {
    try {
      UserDTO dto = BLL.getUser(id);
      return Response.status(Status.OK).entity(dto).build();
    } catch (DALException e) {
      e.printStackTrace();
      return Response.status(Status.NOT_ACCEPTABLE).build();
    } catch (SessionException e) {
      e.printStackTrace();
      return Response.status(Status.UNAUTHORIZED).build();  
    }
  }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createDTO(UserDTO dto) {
		try {
			int id = BLL.createDTO(dto, "user");
			return Response.status(Status.CREATED).entity(id).build();
		} catch (DALException | DTOException e) {
      e.printStackTrace();
			return Response.status(Status.NOT_ACCEPTABLE).build();
		} catch (SessionException e) {
      e.printStackTrace();
			return Response.status(Status.UNAUTHORIZED).build();  
		}
	}

	@PUT
	@Path("/cpr={old_cpr : [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDTO(@PathParam("old_cpr") String old_cpr, UserDTO dto) {
		try {
			boolean updated = BLL.updateUser(dto, old_cpr);
			return Response.status(Status.CREATED).entity(updated).build();
		} catch (DALException | DTOException e) {
      e.printStackTrace();
			return Response.status(Status.NOT_ACCEPTABLE).build();
		} catch (SessionException e) {
      e.printStackTrace();
			return Response.status(Status.UNAUTHORIZED).build();  
		}
	}
}
