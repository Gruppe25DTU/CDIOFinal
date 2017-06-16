package rest;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.SessionDAO;
import dao.UserDAO;
import dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import logic.CDIOException.DALException;
import logic.CDIOException.InvalidLoginException;

@Path("")
public class RESTLogin {
  
  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response getLogin(@FormParam("username") String username, @FormParam("password") String password)
  {
    System.out.println("Login attempt by :" + username);
    SessionDAO seDAO = new SessionDAO();
    try {
      int id = seDAO.login(username, password);
      UserDTO user = UserDAO.get(id);
      System.out.println(id);
      String token = Jwts.builder()
          .setSubject(username)
          .setIssuer("Gruppe25")
          .setId(Integer.toString(id))
          .setIssuedAt(new Date())
          .setExpiration(getExpiration())
          .claim("roles", user.getRoles())
          .signWith(SignatureAlgorithm.HS512, getKey())
          .compact();
      return Response.ok().header("Authorization", "Bearer " + token).build();
    } catch (DALException | InvalidLoginException e) {
        return Response.status(Status.NOT_ACCEPTABLE).build();
    }
  }
  
  private Date getExpiration()
  {
    Date d = new Date(System.currentTimeMillis()+7200000);
    return d;
  }
  
  protected static byte[] getKey() {
    try {
      return "CDIOFinal".getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
