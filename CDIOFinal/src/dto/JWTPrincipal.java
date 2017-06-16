package dto;

import java.security.Principal;

public class JWTPrincipal implements Principal {
  private String name;
  private String email;
  private String firstName;
  private String lastName;
  private String[] roles;

  
  
  public JWTPrincipal(String name, String email, String firstName, String lastName) {
    this.name = name;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }



  @Override
  public String getName() {
    return name;
  }



  public String[] getRoles() {
    return roles;
  }
}
