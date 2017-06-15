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
  
  private static HashMap<String, Method> creatorMap = new HashMap<String, Method>() {{
    try {
      this.put("commodityBatch", CommodityBatchDataCheck.class.getMethod("create", new Class[] {CommodityBatchDTO.class}));
      this.put("commodity", CommodityDataCheck.class.getMethod("create", new Class[] {CommodityDTO.class}));
      this.put("productBatchComponent", ProductBatchCompDataCheck.class.getMethod("create", new Class[] {ProductBatchCompDTO.class}));
      this.put("productBatch", ProductBatchDataCheck.class.getMethod("create", new Class[] {ProductBatchDTO.class}));
      this.put("recipe", RecipeDataCheck.class.getMethod("create", new Class[] {RecipeDTO.class}));
      this.put("supplier", SupplierDataCheck.class.getMethod("create", new Class[] {SupplierDTO.class}));
      this.put("user", UserDataCheck.class.getMethod("create", new Class[] {UserDTO.class}));
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
    System.out.println(dto);
    return UserDAO.update(dto, old_cpr);
  }
  
  public static void updateCommodity(CommodityDTO dto) throws DALException {
    CommodityDAO.updateCommodity(dto);
  }

  public static CommodityDTO[] getCommodity() throws DALException, SessionException {
    return CommodityDAO.getList();
  }

  public static CommodityDTO getCommodity(int id) throws DALException, SessionException {
    return CommodityDAO.get(id);
  }

  public static CommodityBatchDTO[] getCommodityBatch() throws DALException, SessionException {
    return CommodityBatchDAO.getList();
  }

  public static CommodityBatchDTO getCommodityBatch(int id) throws DALException, SessionException {
    return CommodityBatchDAO.get(id);
  }

  public static ProductBatchDTO[] getProductBatch() throws DALException, SessionException {
    return ProductBatchDAO.getList();
  }

  public static ProductBatchDTO getProductBatch(int id) throws DALException, SessionException {
    return ProductBatchDAO.get(id);
  }

  public static RecipeDTO[] getRecipe() throws DALException, SessionException {
    return RecipeDAO.getList();
  }

  public static RecipeDTO getRecipe(int id) throws DALException, SessionException {
    return RecipeDAO.get(id);
  }

  public static SupplierDTO[] getSupplier() throws DALException, SessionException {
    return SupplierDAO.getList();
  }

  public static SupplierDTO getSupplier(int id) throws DALException, SessionException {
    return SupplierDAO.get(id);
  }

  public static UserDTO[] getUser() throws DALException, SessionException {
    return UserDAO.getList();
  }

  public static UserDTO getUser(int id) throws DALException, SessionException {
    return UserDAO.get(id);
  }

  public static RecipeCompDTO[] getRecipeComponent(int id) throws DALException, SessionException {
    return RecipeDAO.getRecipeComponent(id);
  }

  public static ProductBatchCompDTO[] getProductBatchComponents(int id) throws DALException, SessionException {
    return ProductBatchDAO.getProductBatchComponents(id);
  }

  public static boolean changeStatus(int id, boolean active) throws DALException {
    return UserDAO.changeStatus(id, active);
  }
  
}