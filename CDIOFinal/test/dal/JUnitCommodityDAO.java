package dal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dao.CommodityDAO;
import dao.SupplierDAO;
import daoInterface.CommodityInterfaceDAO;
import daoInterface.SupplierInterfaceDAO;
import dto.CommodityDTO;
import dto.SupplierDTO;

public class JUnitCommodityDAO {

	@Test
	public void testCreate() {
		Connector.changeTestMode(true);
		CommodityInterfaceDAO commodity = new CommodityDAO();
		SupplierInterfaceDAO supplier = new SupplierDAO();
		CommodityDTO commodityDTO = new CommodityDTO(1, "navn", 1);
		SupplierDTO supplierDTO = new SupplierDTO(1,"supplier");	
		supplier.create(supplierDTO);
		commodity.create(commodityDTO);
		
		CommodityDTO actualCommodityDTO = commodity.get(1);
		SupplierDTO actualSupplierDTO = supplier.getSupplier(1);
		
		compareCommodityDTO(actualCommodityDTO, commodityDTO);
		compareSupplierDTO(actualSupplierDTO, supplierDTO);
	}
		
	
	@Test
	public void testGetCommodityList() {
		
	}
	
	@Test
	public void getCommodity() {
		
	}
	
	@Test
	public void findFreeCommodityId() {
		
	}
	
	private static void compareSupplierDTO(SupplierDTO dto1, SupplierDTO dto2) {
		assertEquals(dto1.getSupplierID(),dto2.getSupplierID()); 
		assertEquals(dto1.getName(),dto2.getName());
	}
	
	private static void compareCommodityDTO(CommodityDTO dto1,CommodityDTO dto2) {
		assertEquals(dto1.getcommodityID(),dto2.getcommodityID());
		assertEquals(dto1.getName(),dto2.getName());
		assertEquals(dto1.getSupplierID(),dto2.getSupplierID());
	}

}
