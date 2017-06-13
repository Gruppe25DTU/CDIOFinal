//package rest;
//
//import java.security.Key;
//import java.util.Date;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.core.MediaType;
//
//import dao.SessionDAO;
//import dao.UserDAO;
//import dto.UserDTO;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.impl.crypto.MacProvider;
//import logic.CDIOException.DALException;
//
//@Path("/login")
//public class RESTSession {
//	
//	private static Key key = MacProvider.generateKey(SignatureAlgorithm.HS512);
//	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String getLogin(UserDTO user)
//	{
//		SessionDAO seDAO = new SessionDAO();
//		try {
//			if(seDAO.login(user.getUserName(), user.getPassword()))
//			{
//				UserDAO uDAO = new UserDAO();
//				return Jwts.builder()
//						.setIssuer("Gruppe25")
//						.claim("user", )
//						.setExpiration(getExpiration())
//						.signWith(SignatureAlgorithm.HS512, key)
//						.compact();
//			}
//		} catch (DALException e) {
//			throw new 
//		}
//	}
//	
//	private Date getExpiration()
//	{
//		Date d = new Date(System.currentTimeMillis()+7200000);
//		return d;
//	}
//	
//
//}
