package dao;

import dto.CommodityBatchDTO;

public class CommodityBatchDAO {
  
  public boolean changeAmount(int id, int amount) {
    return false;
  }
  
  public int create(CommodityBatchDTO dto) {
    return 1;
  }
  
  public boolean update(CommodityBatchDTO dto) {
    return true;
  }
  
  public CommodityBatchDTO get(int id) {
    return null;
  }

}
