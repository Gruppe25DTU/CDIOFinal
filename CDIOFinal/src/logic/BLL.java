package logic;

import dto.IDTO;
import logic.CDIOException.*;

public class BLL {

  public static int createDTO(IDTO dto) throws DTOException, DALException, UnauthorizedException
  {
    return dto.getId();
  }
  
  public static int updateDTO(IDTO dto) throws DTOException, DALException, UnauthorizedException
  {
    return dto.getId();
  } 
  
}
