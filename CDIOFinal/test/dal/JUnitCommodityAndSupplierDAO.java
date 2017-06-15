package dal;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dao.CommodityBatchDAO;
import dao.CommodityDAO;
import dao.SupplierDAO;
import dto.CommodityBatchDTO;
import dto.CommodityDTO;
import dto.SupplierDTO;
import logic.CDIOException.DALException;

public class JUnitCommodityAndSupplierDAO {

	@Test
	public void testCreate() throws DALException{
		Connector.changeTestMode(true);
		CommodityDAO commodity = new CommodityDAO();
		SupplierDAO supplier = new SupplierDAO();
		CommodityBatchDAO commoditybatch = new CommodityBatchDAO();
		
		CommodityDTO commodityDTO = new CommodityDTO(1, "navn", 1);
		SupplierDTO supplierDTO = new SupplierDTO(1,"supplier");	
		CommodityBatchDTO commodityBatchDTO = new CommodityBatchDTO(1,1,10.1);
		
		supplier.create(supplierDTO);
		commodity.create(commodityDTO);
		commoditybatch.create(commodityBatchDTO);
		
		
		CommodityDTO actualCommodityDTO = commodity.get(1);
		SupplierDTO actualSupplierDTO = supplier.getSupplier(1);
		CommodityBatchDTO actualCommodityBatchDTO = commoditybatch.get(1);
		
		compareCommodityDTO(actualCommodityDTO, commodityDTO);
		compareSupplierDTO(actualSupplierDTO, supplierDTO);
		compareCommodityBatchDTO(actualCommodityBatchDTO,commodityBatchDTO);
	}
		
	
	@Test
	public void testGetCommodityList() throws DALException{
		Connector.changeTestMode(true);
		CommodityDAO commodity = new CommodityDAO();
		SupplierDAO supplier = new SupplierDAO();
		CommodityBatchDAO commoditybatch = new CommodityBatchDAO();
	
		CommodityDTO commodityDTO1 = new CommodityDTO(1, "navn", 1);
		SupplierDTO supplierDTO1 = new SupplierDTO(1,"supplier");
		CommodityDTO commodityDTO2 = new CommodityDTO(2, "navn2", 2);
		SupplierDTO supplierDTO2 = new SupplierDTO(2,"supplier2");	
		CommodityBatchDTO commodityBatchDTO1 = new CommodityBatchDTO(1,1,10.1);
		CommodityBatchDTO commodityBatchDTO2 = new CommodityBatchDTO(2,2,20.1);

		
		List<CommodityDTO> actualCommodityList = new ArrayList<>();
		List<SupplierDTO> actualSupplierList = new ArrayList<>();
		List<CommodityBatchDTO> actualCommodityBatchList = new ArrayList<>();
		
		actualCommodityList.add(commodityDTO1);
		actualCommodityList.add(commodityDTO2);
		actualSupplierList.add(supplierDTO1);
		actualSupplierList.add(supplierDTO2);
		actualCommodityBatchList.add(commodityBatchDTO1);
		actualCommodityBatchList.add(commodityBatchDTO2);
		
		supplier.create(supplierDTO2);
		commodity.create(commodityDTO2);
		commoditybatch.create(commodityBatchDTO2);
		
		CommodityDTO[] expectedCommodityList = commodity.getList();
		SupplierDTO[] expectedSupplierList = supplier.getList();
		CommodityBatchDTO[] expectedCommodityBatchList = commoditybatch.getList();
		
		for(int i = 0; i<expectedCommodityList.length;i++) {
			compareCommodityDTO(actualCommodityList.get(i), expectedCommodityList[i]);
		}
		for(int i = 0;i<expectedSupplierList.length;i++) {
			compareSupplierDTO(actualSupplierList.get(i),expectedSupplierList[i]);

		}
		for(int i = 0;i<expectedCommodityBatchList.length;i++) {
			compareCommodityBatchDTO(actualCommodityBatchList.get(i),expectedCommodityBatchList[i]);

		}
		
	}

	
	
	
	private static void compareSupplierDTO(SupplierDTO dto1, SupplierDTO dto2) {
		assertEquals(dto1.getId(),dto2.getId()); 
		assertEquals(dto1.getSupplierName(),dto2.getSupplierName());
	}
	
	private static void compareCommodityDTO(CommodityDTO dto1,CommodityDTO dto2) {
		assertEquals(dto1.getId(),dto2.getId());
		assertEquals(dto1.getCommodityName(),dto2.getCommodityName());
		assertEquals(dto1.getSupplierID(),dto2.getSupplierID());
	}

	@SuppressWarnings("deprecation")
	private static void compareCommodityBatchDTO(CommodityBatchDTO dto1, CommodityBatchDTO dto2) {
		assertEquals(dto1.getId(),dto2.getId());
		assertEquals(dto1.getCommodityID(),dto2.getCommodityID());
		assertEquals(Double.toString(dto1.getQuantity()),Double.toString(dto2.getQuantity()));

	}
}
