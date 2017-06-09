package daoInterface;

import java.util.List;

import dto.CommodityDTO;
import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;
import dto.RecipeCompDTO;

public interface ProductBatchInterfaceDAO {
	int create(ProductBatchDTO dto);
	boolean update(ProductBatchDTO dto);
	boolean changeStatus(int id, int status);
	void print(int id);
	ProductBatchDTO get(int id);
	List<ProductBatchDTO> getList();
	boolean addComponent(ProductBatchDTO productBatch, ProductBatchCompDTO component);
	RecipeCompDTO getNonWeighedComp(int pbid);



}
