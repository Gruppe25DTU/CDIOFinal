package dtomanager;

import java.util.List;

import dto.CommodityDTO;
import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;

public class ProductBatchManager {
  
  public int create(ProductBatchDTO dto) {
    return 1;
  }
  
  public boolean update(ProductBatchDTO dto) {
    return true;
  }
  
  public boolean changeStatus(int id, int status) {
    return true;
  }
  
  public void print(int id) {
    
  }
  
  public ProductBatchDTO get(int id) {
    return null;
  }
  
  public List<ProductBatchDTO> getList() {
    return null;
  }
  
  public boolean addComponent(ProductBatchDTO productBatch, ProductBatchCompDTO component) {
    return false;
  }
  
  public CommodityDTO getNonWeightedComp(int pbid) {
    return null;
  }
  
}
