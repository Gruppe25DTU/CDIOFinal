package rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.Priority;
import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import dao.SessionDAO;
import dao.UserDAO;
import dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import logic.CDIOException.DALException;
import logic.CDIOException.InvalidLoginException;

@Provider
@JWTTokenNeeded
public class RESTSession implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext requestContext) {
    System.out.println("Admin");
    try {
      String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
      if (authorizationHeader == null) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        return;
      }
      String token = authorizationHeader.substring("Bearer".length()).trim();
      Claims claims = Jwts.parser()
          .setSigningKey(RESTLogin.getKey())
          .parseClaimsJws(token).getBody();
      System.out.println("validated as user: " + claims.getId() + " with roles: " + claims.get("roles"));
      ArrayList<String> roles = (ArrayList<String>) claims.get("roles");
      if (roles == null || !Arrays.asList(roles).contains("Administrator")) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
      }
    } catch(ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
      e.printStackTrace();
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
  }
}
