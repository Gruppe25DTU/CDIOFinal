package dal;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dao.CommodityBatchDAO;
import dao.CommodityDAO;
import dao.SupplierDAO;
import daoInterface.CommodityBatchInterfaceDAO;
import daoInterface.CommodityInterfaceDAO;
import daoInterface.SupplierInterfaceDAO;
import dto.CommodityBatchDTO;
import dto.CommodityDTO;
import dto.SupplierDTO;

public class JUnitCommodityAndSupplierDAO {

	@Test
	public void testCreate() {
		Connector.changeTestMode(true);
		CommodityInterfaceDAO commodity = new CommodityDAO();
		SupplierInterfaceDAO supplier = new SupplierDAO();
		CommodityBatchInterfaceDAO commoditybatch = new CommodityBatchDAO();
		
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
	public void testGetCommodityList() {
		Connector.changeTestMode(true);
		CommodityInterfaceDAO commodity = new CommodityDAO();
		SupplierInterfaceDAO supplier = new SupplierDAO();
		CommodityBatchInterfaceDAO commoditybatch = new CommodityBatchDAO();

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
		
		List<CommodityDTO> expectedCommodityList = commodity.getList();
		List<SupplierDTO> expectedSupplierList = supplier.getList();
		List<CommodityBatchDTO> expectedCommodityBatchList = commoditybatch.getList();
		
		for(int i = 0; i<expectedCommodityList.size();i++) {
			compareCommodityDTO(actualCommodityList.get(i), expectedCommodityList.get(i));
		}
		for(int i = 0;i<expectedSupplierList.size();i++) {
			compareSupplierDTO(actualSupplierList.get(i),expectedSupplierList.get(i));

		}
		for(int i = 0;i<expectedCommodityBatchList.size();i++) {
			compareCommodityBatchDTO(actualCommodityBatchList.get(i),expectedCommodityBatchList.get(i));

		}
		
	}

	
	@Test
	public void findFreeCommodityId() {
		Connector.changeTestMode(true);
		CommodityInterfaceDAO commodity = new CommodityDAO();
		SupplierInterfaceDAO supplier = new SupplierDAO();
		CommodityBatchInterfaceDAO commoditybatch = new CommodityBatchDAO();

		int freeCommodityID = commodity.findFreeCommodityID();
		int freeSupplierID = supplier.findFreeSupplierID();
		int freeCommodityBatchID = commoditybatch.findFreeCommodityBatchID();
		assertEquals(freeCommodityID,3);
		assertEquals(freeSupplierID,3);
		assertEquals(freeCommodityBatchID,3);
	}
	
	private static void compareSupplierDTO(SupplierDTO dto1, SupplierDTO dto2) {
		assertEquals(dto1.getId(),dto2.getId()); 
		assertEquals(dto1.getName(),dto2.getName());
	}
	
	private static void compareCommodityDTO(CommodityDTO dto1,CommodityDTO dto2) {
		assertEquals(dto1.getId(),dto2.getId());
		assertEquals(dto1.getName(),dto2.getName());
		assertEquals(dto1.getSupplierID(),dto2.getSupplierID());
	}

	@SuppressWarnings("deprecation")
	private static void compareCommodityBatchDTO(CommodityBatchDTO dto1, CommodityBatchDTO dto2) {
		assertEquals(dto1.getId(),dto2.getId());
		assertEquals(dto1.getCommodityID(),dto2.getCommodityID());
		assertEquals(Double.toString(dto1.getQuantity()),Double.toString(dto2.getQuantity()));

	}
}
