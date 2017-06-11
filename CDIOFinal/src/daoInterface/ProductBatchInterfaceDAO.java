package daoInterface;

import java.util.List;

import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;
import dto.RecipeCompDTO;

public interface ProductBatchInterfaceDAO {
	int create(ProductBatchDTO dto);
	boolean changeStatus(int id, int status);
	void print(int id);
	ProductBatchDTO get(int id);
	List<ProductBatchDTO> getList();
	boolean addComponent(ProductBatchCompDTO component);
	RecipeCompDTO getNonWeighedComp(int pbid);
	int findFreeProductBatchID();
	boolean setStartdate(ProductBatchDTO dto);
	boolean setStopdate(ProductBatchDTO dto);
	List<ProductBatchCompDTO> getProductBatchComponents(int productBatchID);


}
