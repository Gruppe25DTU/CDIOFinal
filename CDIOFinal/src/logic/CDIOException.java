package logic;

public interface CDIOException {

  public class DTOException extends Exception {}
  public class InvalidDataException extends DTOException {}
  public class UnexpectedDataException extends DTOException {}
  
  public class DALException extends Exception {}
  public class IDTakenException extends DALException {}
  public class UsernameTakenException extends DALException {}
  
  public class UnauthorizedException extends Exception {}

}