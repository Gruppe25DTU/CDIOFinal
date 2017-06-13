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
import dto.CommodityDTO;
import dto.IDTO;
import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;
import logic.BLL;
import logic.CDIOException.DALException;
import logic.CDIOException.DTOException;
import logic.CDIOException.SessionException;
import logic.CDIOException.UnauthorizedException;
import logic.validation.RuleSet;
import logic.validation.RuleSetInterface;

@Path("productBatch")
public class RESTProductBatch {

  @GET
  @Path("/list")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getListDTO() {
    try {
      ProductBatchDTO[] dto = BLL.getProductBatch();
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
      ProductBatchDTO dto = BLL.getProductBatch(id);
      return Response.status(Status.OK).entity(dto).build();
    } catch (DALException e) {
      return Response.status(Status.NOT_ACCEPTABLE).build();
    } catch (SessionException e) {
      return Response.status(Status.UNAUTHORIZED).build();  
    }
  }

  @GET
  @Path("/components/id={id : \\d+}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDTOComponents(@PathParam("id") int id) {
    try {
      ProductBatchCompDTO[] dto = BLL.getProductBatchComponents(id);
      return Response.status(Status.OK).entity(dto).build();
    } catch (DALException e) {
      return Response.status(Status.NOT_ACCEPTABLE).build();
    } catch (SessionException e) {
      return Response.status(Status.UNAUTHORIZED).build();  
    }
  }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response productbatchDTO(ProductBatchDTO dto) {
		try {
			int dtoID = BLL.createDTO(dto, "productBatch");
			return Response.status(Status.CREATED).entity(dtoID).build();
		} catch (DALException | DTOException e) {
			return Response.status(Status.NOT_ACCEPTABLE).build();
		} catch (UnauthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();  
		}
	}
}
