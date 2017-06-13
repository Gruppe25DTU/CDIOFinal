package logic;

import dto.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import dao.*;
import dto.IDTO;
import logic.CDIOException.*;
import logic.validation.*;

public class BLL {
  
  private static Class[] args = {IDTO.class};
  
  private static HashMap<String, Method> creatorMap = new HashMap<String, Method>() {{
    try {
      this.put("commodityBatch", CommodityBatchDataCheck.class.getMethod("create", args));
      this.put("commodity", CommodityDataCheck.class.getMethod("create", args));
      this.put("productBatchComp", ProductBatchCompDataCheck.class.getMethod("create", args));
      this.put("productBatch", ProductBatchDataCheck.class.getMethod("create", args));
      this.put("recipe", RecipeDataCheck.class.getMethod("create", args));
      this.put("recipeComp", RecipeCompDataCheck.class.getMethod("create", args));
      this.put("supplier", SupplierDataCheck.class.getMethod("create", args));
      this.put("user", UserDataCheck.class.getMethod("create", args));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }};
  
  public static int createDTO(IDTO dto, String dtoType) throws DTOException, DALException, UnauthorizedException
  {
    try {
      DataCheckerInterface.checkDTO(dto, dtoType);
    } catch (Exception e) {
      throw new DTOException();
    }
    Method createMethod = creatorMap.get(dtoType);
    if (createMethod == null) {
      throw new DTOException();
    }
    try {
      return (int) createMethod.invoke(createMethod.getDeclaringClass(), dto);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return -1;
  }
  
  public static boolean updateUser(UserDTO dto, String old_cpr) throws DTOException, DALException, UnauthorizedException
  {
    DataCheckerInterface.checkDTO(dto, "user");
    return UserDAO.update(dto, old_cpr);
  }
  
}
