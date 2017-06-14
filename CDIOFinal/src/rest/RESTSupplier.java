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

import dao.SupplierDAO;
import dto.CommodityDTO;
import dto.IDTO;
import dto.SupplierDTO;
import dto.UserDTO;
import logic.BLL;
import logic.CDIOException.*;
import logic.validation.RuleSet;
import logic.validation.RuleSetInterface;

@Path("supplier")
public class RESTSupplier {

  @GET
  @Path("/list")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getListDTO() {
    try {
      SupplierDTO[] dto = BLL.getSupplier();
      return Response.status(Status.OK).entity(dto).build();
    } catch (DALException e) {
      return Response.status(Status.NOT_ACCEPTABLE).build();
    } catch (SessionException e) {
      return Response.status(Status.UNAUTHORIZED).build();  
    }
  }

  @GET
  @Path("/id={id : \\d+}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDTO(@PathParam("id") int id) {
    try {
      SupplierDTO dto = BLL.getSupplier(id);
      return Response.status(Status.OK).entity(dto).build();
    } catch (DALException e) {
      return Response.status(Status.NOT_ACCEPTABLE).build();
    } catch (SessionException e) {
      return Response.status(Status.UNAUTHORIZED).build();  
    }
  }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response supplierDTO(SupplierDTO dto) {
		try {
			int id = BLL.createDTO(dto, "supplier");
			return Response.status(Status.CREATED).entity(id).build();
		} catch (DALException | DTOException e) {
			return Response.status(Status.NOT_ACCEPTABLE).build();
		} catch (SessionException e) {
			return Response.status(Status.UNAUTHORIZED).build();  
		}
	}
}