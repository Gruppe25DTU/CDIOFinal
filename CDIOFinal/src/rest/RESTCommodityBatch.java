package rest;

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

import dao.CommodityBatchDAO;
import dto.CommodityBatchDTO;
import dto.CommodityDTO;
import dto.IDTO;
import logic.BLL;
import logic.CDIOException.DALException;
import logic.CDIOException.DTOException;
import logic.CDIOException.SessionException;
import logic.CDIOException.UnauthorizedException;
import logic.validation.RuleSet;
import logic.validation.RuleSetInterface;

@Path("commodityBatch")
public class RESTCommodityBatch {

  @GET
  @Path("/list")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getListDTO() {
    try {
      CommodityBatchDTO[] dto = BLL.getCommodityBatch();
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
      CommodityBatchDTO dto = BLL.getCommodityBatch(id);
      return Response.status(Status.OK).entity(dto).build();
    } catch (DALException e) {
      e.printStackTrace();
      return Response.status(Status.NOT_ACCEPTABLE).build();
    } catch (SessionException e) {
      return Response.status(Status.UNAUTHORIZED).build();  
    }
  }

	@POST
	@Path("/commoditybatch/id={id : \\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response commoditybatchDTO(CommodityBatchDTO dto) {
		try {
			int id = BLL.createDTO(dto, "commodityBatch");
			return Response.status(Status.CREATED).entity(id).build();
		} catch (DALException | DTOException e) {
			return Response.status(Status.NOT_ACCEPTABLE).build();
		} catch (UnauthorizedException e) {
			return Response.status(Status.UNAUTHORIZED).build();  
		}
	}
}
