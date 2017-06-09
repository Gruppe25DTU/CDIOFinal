package daoInterface;

import java.util.List;

import dto.CommodityBatchDTO;

public interface CommodityBatchInterfaceDAO {
	boolean changeAmount(int id, double amount);
	int create(CommodityBatchDTO dto);
	CommodityBatchDTO get(int id);
	List<CommodityBatchDTO> getList();
	int findFreeCommodityBatchID();
}
