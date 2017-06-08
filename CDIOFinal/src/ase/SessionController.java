package ase;

import java.io.IOException;

import dao.UserDAO;
import dto.CommodityBatchDTO;
import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;
import dto.UserDTO;

public class SessionController {

	private UserDTO user;
	private ProductBatchDTO prod;
	private ProductBatchCompDTO currentPrBatchComp;
	private Connection conn;
	private int wStep;
	private double tara;
	private double netto;
	private MessageType expectedType;
	private CommodityBatchDTO cBatch;
	private String lastMessageSent; // The last message sent is stored, so we can repeat it in case of errors
	private int errorsInARow;  // We track how many errors we get in a row and when we get to 10 we sleep for a second-
							   // so we won't spam the weight. 

	public SessionController(Connection conn) {
		user = null;
		prod = null;
		currentPrBatchComp  = null;
		this.conn = conn;
		wStep = 0;
		tara = 0;
		netto = 0;
		cBatch = null;
		errorsInARow = 0;
		try 
		{
			// Setting the key controls so we can see key codes when buttons on weight are pressed-
			//- but their functions aren't run on the weight
			lastMessageSent = "K 3";
			expectedType = MessageType.K_A;
			conn.outputMsg(lastMessageSent);
			
		} 
		catch (IOException e) 
		{

		}
	}

	public void processInput(SocketInMessage msg) throws IOException
	{
		if(msg.getReplyType().equals(expectedType))
		{
			switch(wStep)
			{
			case 0 :
				//First Labworker types his id
				lastMessageSent = "RM20 8 \"Indtast laborant nr\" \"\" \"&3\"";
				expectedType = MessageType.RM20_B;
				wStep++;
				conn.outputMsg(lastMessageSent);
				break;
			case 1 :
				//Message Is recieved
				wStep++;
				expectedType = MessageType.RM20_A;
				break;
			case 2 :
				//Labworker has sent his id, here we check if it's valid or not
				if(msg.getMsg().length() > 0)
				{
					
					int labId = Integer.parseInt(msg.getMsg());
					System.out.println("here now" + labId);
					UserDAO uDao = new UserDAO();
					user = uDao.get(labId);
					if(user != null)
					{
						//We send a message to confirm that the name is correct (There is no other choice but to confirm)
						//
						String name = user.getFirstName() + " " + user.getLastName();
						if(name.length() > 26 )
							name = user.getFirstName() + " "+user.getLastName().charAt(0);
						expectedType = MessageType.P111_A;
						wStep++;
						lastMessageSent = "P111 \""+name+"? [->\"";
						conn.outputMsg(lastMessageSent);
					}
					else
					{
						//ID was invalid and we resend the message and go back to step 1
						lastMessageSent = "RM20 8 \"Indtast laborant nr\" \"\" \"&3\"";
						conn.outputMsg(lastMessageSent);
						expectedType = MessageType.RM20_B;
						wStep = 1;
					}
				}
				else
				{
					//ID was invalid and we resend the message and go back to step 1
					lastMessageSent = "RM20 8 \"Indtast laborant nr\" \"\" \"&3\"";
					conn.outputMsg(lastMessageSent);
					expectedType = MessageType.RM20_B;
					wStep = 1;
				}
				break;
			case 3 :
				//Labworker pressed the [-> key
				expectedType = MessageType.K_C_4;
				wStep++;
				break;
			case 4 :
				//Labworker now has to type in the number for the productbatch
				lastMessageSent = "RM20 8 \"Indtast Batch nr\" \"\" \"&3\"";
				expectedType = MessageType.RM20_B;
				wStep++;
				conn.outputMsg(lastMessageSent);
				break;
			case 5 :
				expectedType = MessageType.RM20_A;
				wStep++;
				break;
			case 6 : 
				//Labworker sent productbatch id number, now we check whether it's valid
				//And whether there is anything to weigh
//				if(msg.getMsg().length() > 0)
//				{
//					int prodId = Integer.parseInt(msg.getMsg());
//					prod = ProductBatchDAO.getProductBatch(prodId);
//					if(prod != null)
//					{
//						currentPrBatchComp = ProductBatchCompDAO.getRemainingComp(prod.getId);
//						if(currentPrBatchComp != null)
//						{
//							// Now that we have something to weigh we need to remove everything from the weight
//							// And the labworker has to press [-> when it is done
//							
//							lastMessageSent = "P111 \"Fjern alt fra vægten [->\"";
//							expectedType = MessageType.P111_A;
//							weighingStep++;
//							conn.outputMsg(lastMessageSent);
//							
//							
//						}
//						else
//						{
//							weighingStep = 5;
//							conn.outputMsg(lastMessageSent);
//						}
//					}
//					else
//					{
//						weighingStep = 5;
//						conn.outputMsg(lastMessageSent);
//					}
//				}
//				else
//				{
//					weighingStep = 5;
//					conn.outputMsg(lastMessageSent);
//				}
				break;
			case 7 :
				expectedType = MessageType.K_C_4;
				wStep++;
				break;
			case 8 :
				//Labworker pressed [-> and we tare the weight
				lastMessageSent = "T";
				expectedType = MessageType.TARA;
				wStep++;
				conn.outputMsg(lastMessageSent);
				break;
			case 9 :
				if(Double.parseDouble(msg.getMsg()) > 0.01)
				{
					lastMessageSent = "P111 \"For meget vægt [->\"";
					conn.outputMsg(lastMessageSent);
					expectedType = MessageType.P111_A;
					wStep = 7;
				}
				else
				{
					lastMessageSent = "P111 \"Placer tara på vægten [->\"";
					expectedType = MessageType.P111_A;
					wStep++;
					conn.outputMsg(lastMessageSent);
				}
				break;
			case 10 :
				expectedType = MessageType.K_C_4;
				wStep++;
				break;
			case 11 :
				lastMessageSent = "S";
				wStep++;
				expectedType = MessageType.WEIGHT;
				conn.outputMsg(lastMessageSent);
				break;
			case 12 :
				tara = Double.parseDouble(msg.getMsg());
				lastMessageSent = "T";
				wStep++;
				expectedType = MessageType.TARA;
				conn.outputMsg(lastMessageSent);
				break;
			case 13 :
				
				break;
			case 14 :
				break;
			default: 
				break;
			}
		}
		else if(msg.getReplyType().equals(MessageType.ERROR))
		{
			if(errorsInARow < 10)
			{
				conn.outputMsg(lastMessageSent);
				errorsInARow++;
			}
			else
			{
				try {
					Thread.sleep(1000);
					errorsInARow = 0;
					conn.outputMsg(lastMessageSent);
				} catch (InterruptedException e) {
					
				}
				
			}
		}
	}

	public UserDTO getUser() {
		return user;
	}

	public ProductBatchDTO getProd() {
		return prod;
	}

	public ProductBatchCompDTO getCurrentPrBatchComp() {
		return currentPrBatchComp;
	}

	public Connection getConn() {
		return conn;
	}

	public int getWeighingStep() {
		return wStep;
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

	public CommodityBatchDTO getcBatch() {
		return cBatch;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public void setProd(ProductBatchDTO prod) {
		this.prod = prod;
	}

	public void setCurrentPrBatchComp(ProductBatchCompDTO currentPrBatchComp) {
		this.currentPrBatchComp = currentPrBatchComp;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public void setWeighingStep(int weighingStep) {
		this.wStep = weighingStep;
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

	public void setcBatch(CommodityBatchDTO cBatch) {
		this.cBatch = cBatch;
	}

}
