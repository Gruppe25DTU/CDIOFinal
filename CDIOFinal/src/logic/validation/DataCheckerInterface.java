package logic.validation;

import dto.IDTO;
import dto.UserDTO;
import logic.CDIOException.*;

public class DataCheckerInterface {
  
  public static boolean checkId(String dtoType, int id) throws IDTakenException, InvalidDataException
  {
    return true;
  }
  
  public static boolean checkField(IDTO dto, String field) throws IDTakenException, InvalidDataException, UnexpectedDataException
  {
    return true;
  }
  
  public static boolean checkUsername(UserDTO user) throws UsernameTakenException, InvalidDataException, UnexpectedDataException
  {
    return true;
  }
  
  public static boolean checkDTO(IDTO dto) throws DALException, DTOException
  {
    return true;
  }

}
