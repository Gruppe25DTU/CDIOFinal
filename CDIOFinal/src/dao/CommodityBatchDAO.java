package dao;

import dal.Connector;
import dto.CommodityBatchDTO;

public class CommodityBatchDAO {
  
  public boolean changeAmount(int id, int amount) {
	Connector.doUpdate("")
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
