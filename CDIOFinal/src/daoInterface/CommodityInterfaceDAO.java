package daoInterface;

import java.util.List;

import dto.CommodityDTO;

public interface CommodityInterfaceDAO {
	int create(CommodityDTO dto);
	boolean delete(CommodityDTO dto);
	List<CommodityDTO> getList();
	CommodityDTO get(int id);
	
	
}
