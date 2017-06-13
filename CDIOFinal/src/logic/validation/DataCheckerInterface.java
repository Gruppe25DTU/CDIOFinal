package logic.validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import dto.IDTO;
import dto.UserDTO;
import logic.CDIOException.*;

public class DataCheckerInterface {
  
  private static final HashMap<String, HashMap<String, Method>> checklist = new HashMap<String, HashMap<String, Method>>() {{
    this.put("commodityBatch", mapMaker("CommodityBatchDataCheck"));
    this.put("commodity", mapMaker("CommodityDataCheck"));
    this.put("productBatchComponent", mapMaker("ProductBatchCompDataCheck"));
    this.put("productBatch", mapMaker("ProductBatchDataCheck"));
    this.put("recipeComponent", mapMaker("RecipeCompDataCheck"));
    this.put("recipe", mapMaker("RecipeDataCheck"));
    this.put("supplier", mapMaker("SupplierDataCheck"));
    this.put("user", mapMaker("UserDataCheck"));
  }};
  
  private static HashMap<String, Method> mapMaker(String path) {
    HashMap<String, Method> map = new HashMap<String, Method>();
    try {
      Class user = Class.forName("logic.validation." + path);
      Method[] methods = user.getDeclaredMethods();
      for (Method method : methods) {
        map.put(method.getName(), method);
      }
    } catch (ClassNotFoundException e1) {
      e1.printStackTrace();
    }
    return map;
  }
  
  public static void checkField(String dtoType, String field, String value) throws DALException, DTOException, UnauthorizedException
  {
    HashMap<String, Method> map = checklist.get(dtoType);
    if (map == null) {
      throw new UnknownDTOException();
    }
    Method method = map.get(field);
    if (method == null) {
      throw new UnexpectedDataException();
    }
    try {
      method.invoke(method.getDeclaringClass(), value);
    } catch (IllegalAccessException e) {
      throw new UnauthorizedException(e);
    } catch (IllegalArgumentException e) {
      throw new UnexpectedDataException(e);
    } catch (InvocationTargetException e) {
      throw new DTOException(e.getTargetException());
    }
  }
  
  public static boolean checkDTO(IDTO dto, String dtoType) throws DALException, DTOException
  {
    return true;
  }
  
  private static boolean checkId(String dtoType, int id) throws IDTakenException, InvalidDataException
  {
    return true;
  }
  
  private static boolean checkUsername(UserDTO user) throws UsernameTakenException, InvalidDataException, UnexpectedDataException
  {
    return true;
  }

}
