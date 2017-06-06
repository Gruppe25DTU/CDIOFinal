package ase;

import dto.ProductBatchDTO;
import dto.UserDTO;

public class Session {

	private UserDTO user;
	private ProductBatchDTO prod;
	private ProductBatchComponentDTO currentPrBatchComp;
	private Connection conn;
	private int weighingStep:
		private double tara;
	private double netto;
	private MessageType expectedType;
	private CommodityBatch cBatch;

	public Session() {
		// TODO Auto-generated constructor stub
	}

	public String processInput(SocketInMessage msg)
	{
		if(msg.getReplyType().equals(expectedType))
		{
			switch(weighingStep)
			{
			case 0 :
				break;
			default: 
				break;
			}
		}
	}

	public UserDTO getUser() {
		return user;
	}

	public ProductBatchDTO getProd() {
		return prod;
	}

	public ProductBatchComponentDTO getCurrentPrBatchComp() {
		return currentPrBatchComp;
	}

	public Connection getConn() {
		return conn;
	}

	public int getWeighingStep() {
		return weighingStep;
	}

	public double getTara() {
		return tara;
	}

	public double getNetto() {
		return netto;
	}

	public MessageType getExpectedType() {
		return expectedType;
	}

	public CommodityBatch getcBatch() {
		return cBatch;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public void setProd(ProductBatchDTO prod) {
		this.prod = prod;
	}

	public void setCurrentPrBatchComp(ProductBatchComponentDTO currentPrBatchComp) {
		this.currentPrBatchComp = currentPrBatchComp;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public void setWeighingStep(int weighingStep) {
		this.weighingStep = weighingStep;
	}

	public void setTara(double tara) {
		this.tara = tara;
	}

	public void setNetto(double netto) {
		this.netto = netto;
	}

	public void setExpectedType(MessageType expectedType) {
		this.expectedType = expectedType;
	}

	public void setcBatch(CommodityBatch cBatch) {
		this.cBatch = cBatch;
	}

}
