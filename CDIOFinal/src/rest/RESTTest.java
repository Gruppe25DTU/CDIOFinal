package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class RESTTest {
  
  @GET
  public String test(){
    return "Yay";
  }

}
