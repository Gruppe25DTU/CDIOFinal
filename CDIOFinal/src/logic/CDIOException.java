package logic;

public interface CDIOException {

  public class DTOException extends Exception {
    public DTOException() {super();}
    public DTOException(Exception e) {super(e);}
    public DTOException(Throwable e) {super(e);}
    public DTOException(String e) {super(e);}
  }
  public class InvalidDataException extends DTOException {
    public InvalidDataException() {super();}
    public InvalidDataException(Exception e) {super(e);}
    public InvalidDataException(Throwable e) {super(e);}
    public InvalidDataException(String e) {super(e);}
  }
  public class UnexpectedDataException extends DTOException {
    public UnexpectedDataException() {super();}
    public UnexpectedDataException(Exception e) {super(e);}
    public UnexpectedDataException(Throwable e) {super(e);}
    public UnexpectedDataException(String e) {super(e);}
  }
  public class UnknownDTOException extends DTOException {
    public UnknownDTOException() {super();}
    public UnknownDTOException(Exception e) {super(e);}
    public UnknownDTOException(Throwable e) {super(e);}
    public UnknownDTOException(String e) {super(e);}
  }

  
  public class DALException extends Exception {
    public DALException() {super();}
    public DALException(Exception e) {super(e);}
    public DALException(Throwable e) {super(e);}
    public DALException(String e) {super(e);}
  }
    
  public class IDTakenException extends DALException {
    public IDTakenException() {super();}
    public IDTakenException(Exception e) {super(e);}
    public IDTakenException(Throwable e) {super(e);}
    public IDTakenException(String e) {super(e);}
  }
  public class UsernameTakenException extends DALException {
    public UsernameTakenException() {super();}
    public UsernameTakenException(Exception e) {super(e);}
    public UsernameTakenException(Throwable e) {super(e);}
    public UsernameTakenException(String e) {super(e);}
  }
  
  public class UnauthorizedException extends Exception {
    public UnauthorizedException() {super();}
    public UnauthorizedException(Exception e) {super(e);}
    public UnauthorizedException(Throwable e) {super(e);}
    public UnauthorizedException(String e) {super(e);}
  }

}