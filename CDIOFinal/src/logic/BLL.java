package logic;

import dto.IDTO;
import logic.CDIOException.*;

public class BLL {

  public int createDTO(IDTO dto) throws DTOException, DALException, UnauthorizedException
  {
    return dto.getId();
  }
  
  public int updateDTO(IDTO dto) throws DTOException, DALException, UnauthorizedException
  {
    return dto.getId();
  } 
  
}
