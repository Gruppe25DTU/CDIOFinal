package dto;

public class ProductBatchCompDTO implements IDTO {
	private int productBatchID = -1;
	private int commodityBatchID = -1;
	private double tara = -1;
	private double net = -1;
	private int userID =-1;

	public ProductBatchCompDTO(int productBatchID, int commodityBatchID, double tara, double net, int userID){
		this.productBatchID = productBatchID;
		this.commodityBatchID = commodityBatchID;
		this.tara = tara;
		this.net = net;
		this.userID = userID;
	}

	public ProductBatchCompDTO(ProductBatchCompDTO productBatchComp){
		this.productBatchID = productBatchComp.getProductBatchID();
		this.commodityBatchID = productBatchComp.getcommodityBatchID();
		this.tara = productBatchComp.getTara();
		this.net = productBatchComp.getNet();
		this.userID = productBatchComp.getuserID();
	}
	
	public ProductBatchCompDTO() {}


	public int getProductBatchID() {
		return productBatchID;
	}

	public void setproductBatchID(int productBatchID) {
		this.productBatchID = productBatchID;
	}

	public int getcommodityBatchID() {
		return commodityBatchID;
	}

	public void setcommodityBatchID(int commodityBatchID) {
		this.commodityBatchID = commodityBatchID;
	}

	public double getTara() {
		return tara;
	}

	public void setTara(double tara) {
		this.tara = tara;
	}

	public double getNet() {
		return net;
	}

	public void setNet(double net) {
		this.net = net;
	}

	public int getuserID() {
		return userID;
	}

	public void setuserID(int userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "ProductBatchCompDTO [productBatchID=" + productBatchID + ", commodityBatchID=" + commodityBatchID + ", tara="
				+ tara + ", net=" + net + ", userID=" + userID + "]";
	}



}