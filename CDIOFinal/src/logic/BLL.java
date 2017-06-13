package logic;

import dto.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import dao.*;
import logic.CDIOException.*;
import logic.validation.*;

public class BLL {
  
  //TEST MAIN
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line = "";
    while(!line.equals("quit")) {
      try {
        line = br.readLine();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      if (line == null) {
        continue;
      }
      String[] lines = line.split(" ");
      String type = lines[0];
      String field = lines[1];
      String value = "";
      if (lines.length == 3) {
      value = lines[2];
      }
      if (type.equals("get")) {
        if (!value.equals(""))
        {
          try { 
            int id = Integer.valueOf(value);
            System.out.println(BLL.get(field, id));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        else {
          try {
            System.out.println(BLL.getList(field));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  private static Class[] argIDTO = {IDTO.class};
  private static Class[] argInt = {Integer.class};
  
  private static HashMap<String, Method> creatorMap = new HashMap<String, Method>() {{
    try {
      this.put("commodityBatch", CommodityBatchDataCheck.class.getMethod("create", new Class[] {CommodityBatchDTO.class}));
      this.put("commodity", CommodityDataCheck.class.getMethod("create", new Class[] {CommodityDTO.class}));
      this.put("productBatchComp", ProductBatchCompDataCheck.class.getMethod("create", new Class[] {ProductBatchCompDTO.class}));
      this.put("productBatch", ProductBatchDataCheck.class.getMethod("create", new Class[] {ProductBatchDTO.class}));
      this.put("recipe", RecipeDataCheck.class.getMethod("create", new Class[] {RecipeDTO.class}));
      this.put("recipeComp", RecipeCompDataCheck.class.getMethod("create", new Class[] {RecipeCompDTO.class}));
      this.put("supplier", SupplierDataCheck.class.getMethod("create", new Class[] {SupplierDTO.class}));
      this.put("user", UserDataCheck.class.getMethod("create", new Class[] {UserDTO.class}));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }};
  
  private static HashMap<String, Method> getterMap = new HashMap<String, Method>() {{
    try {
      this.put("commodityBatch", CommodityBatchDAO.class.getMethod("get", argInt));
      this.put("commodity", CommodityDAO.class.getMethod("get", argInt));
      this.put("productBatchComp", ProductBatchDAO.class.getMethod("getProductBatchComponents", argInt));
      this.put("productBatch", ProductBatchDAO.class.getMethod("get", argInt));
      this.put("recipe", RecipeDAO.class.getMethod("get", argInt));
      this.put("recipeComp", RecipeDAO.class.getMethod("getRecipeComponent", argInt));
      this.put("supplier", SupplierDAO.class.getMethod("get", argInt));
      this.put("user", UserDAO.class.getMethod("get", argInt));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }};
  
  private static HashMap<String, Method> getListMap = new HashMap<String, Method>() {{
    try {
      this.put("commodityBatch", CommodityBatchDAO.class.getMethod("getList", null));
      this.put("commodity", CommodityDAO.class.getMethod("getList", null));
      this.put("productBatch", ProductBatchDAO.class.getMethod("getList", null));
      this.put("recipe", RecipeDAO.class.getMethod("getList", null));
      this.put("supplier", SupplierDAO.class.getMethod("getList", null));
      this.put("user", UserDAO.class.getMethod("getList", null));
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
      return (IDTO) getter.invoke(getter, id);
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
