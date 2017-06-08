package daoInterface;

import dto.CommodityBatchDTO;

public interface CommodityBatchInterfaceDAO {
	boolean changeAmount(int id, int amount);
	int create(CommodityBatchDTO dto);
	boolean update(CommodityBatchDTO dto);
	CommodityBatchDTO get(int id);
}
