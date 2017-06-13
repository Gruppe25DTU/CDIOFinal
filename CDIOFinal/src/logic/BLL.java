package logic;

import dto.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import dao.*;
import dto.IDTO;
import logic.CDIOException.*;
import logic.validation.*;

public class BLL {

  private static Class[] argIDTO = {IDTO.class};
  private static Class[] argInt = {Integer.class};
  
  private static HashMap<String, Method> creatorMap = new HashMap<String, Method>() {{
    try {
      this.put("commodityBatch", CommodityBatchDataCheck.class.getMethod("create", argIDTO));
      this.put("commodity", CommodityDataCheck.class.getMethod("create", argIDTO));
      this.put("productBatchComp", ProductBatchCompDataCheck.class.getMethod("create", argIDTO));
      this.put("productBatch", ProductBatchDataCheck.class.getMethod("create", argIDTO));
      this.put("recipe", RecipeDataCheck.class.getMethod("create", argIDTO));
      this.put("recipeComp", RecipeCompDataCheck.class.getMethod("create", argIDTO));
      this.put("supplier", SupplierDataCheck.class.getMethod("create", argIDTO));
      this.put("user", UserDataCheck.class.getMethod("create", argIDTO));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }};
  
  private static HashMap<String, Method> getterMap = new HashMap<String, Method>() {{
    try {
      this.put("commodityBatch", CommodityBatchDataCheck.class.getMethod("get", argInt));
      this.put("commodity", CommodityDataCheck.class.getMethod("get", argInt));
      this.put("productBatchComp", ProductBatchCompDataCheck.class.getMethod("get", argInt));
      this.put("productBatch", ProductBatchDataCheck.class.getMethod("get", argInt));
      this.put("recipe", RecipeDataCheck.class.getMethod("get", argInt));
      this.put("recipeComp", RecipeCompDataCheck.class.getMethod("get", argInt));
      this.put("supplier", SupplierDataCheck.class.getMethod("get", argInt));
      this.put("user", UserDataCheck.class.getMethod("get", argInt));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }};
  
  private static HashMap<String, Method> getListMap = new HashMap<String, Method>() {{
    try {
      this.put("commodityBatch", CommodityBatchDataCheck.class.getMethod("getList", null));
      this.put("commodity", CommodityDataCheck.class.getMethod("getList", null));
      this.put("productBatchComp", ProductBatchCompDataCheck.class.getMethod("getList", null));
      this.put("productBatch", ProductBatchDataCheck.class.getMethod("getList", null));
      this.put("recipe", RecipeDataCheck.class.getMethod("getList", null));
      this.put("recipeComp", RecipeCompDataCheck.class.getMethod("getList", null));
      this.put("supplier", SupplierDataCheck.class.getMethod("getList", null));
      this.put("user", UserDataCheck.class.getMethod("getList", null));
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
    throw new DALException();
  }
  
  public static boolean updateUser(UserDTO dto, String old_cpr) throws DTOException, DALException, UnauthorizedException
  {
    DataCheckerInterface.checkDTO(dto, "user");
    return UserDAO.update(dto, old_cpr);
  }
  
  public static IDTO get(String dtoType, int id) throws DTOException, DALException {
    Method getter = getterMap.get(dtoType);
    if (getter == null) {
      throw new DTOException();
    }
    try {
      return (IDTO) getter.invoke(getter.getDeclaringClass(), id);
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
    throw new DALException();
  }
  
  public static IDTO[] getList(String dtoType) throws DTOException, DALException {
    Method getter = getListMap.get(dtoType);
    if (getter == null) {
      throw new DTOException();
    }
    try {
      return (IDTO[]) ((List<IDTO>) getter.invoke(getter.getDeclaringClass())).toArray();
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
    throw new DALException();
  }
  
}
