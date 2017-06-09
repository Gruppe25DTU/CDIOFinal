package ase;

import java.io.IOException;
import java.sql.SQLException;

import dao.CommodityBatchDAO;
import dao.CommodityDAO;
import dao.ProductBatchDAO;
import dao.UserDAO;
import dto.CommodityBatchDTO;
import dto.CommodityDTO;
import dto.ProductBatchCompDTO;
import dto.ProductBatchDTO;
import dto.RecipeCompDTO;
import dto.UserDTO;

public class SessionController {

	private UserDTO user;
	private ProductBatchDTO prod;
	private RecipeCompDTO currentRecipeComp;
	private CommodityDTO comm;
	private Connection conn;
	private int wStep;
	private double tara;
	private double netto;
	private MessageType expectedType;
	private PhaseType phase;
	private CommodityBatchDTO cBatch;
	private boolean RM20Expecting;
	private int errorsInARow;  // We track how many errors we get in a row and when we get to 10 we sleep for a second-
	// so we won't spam the weight. 

	public SessionController(Connection conn) {
		user = null;
		prod = null;
		currentRecipeComp  = null;
		this.conn = conn;
		wStep = 0;
		tara = 0;
		netto = 0;
		cBatch = null;
		errorsInARow = 0;
		phase = PhaseType.LOGIN;
		RM20Expecting = false;
		try 
		{
			// Setting the key controls so we can see key codes when buttons on weight are pressed-
			//- but their functions aren't run on the weight
			conn.outputMsg("K 3");
			conn.outputMsg("RM20 8 \"Enter lab ID\" \"\" \"&3\"");

		} 
		catch (IOException e) 
		{

		}
	}

	public void processInput(SocketInMessage msg) throws IOException
	{
		try
		{
			switch(phase)
			{
			case LOGIN :
				break;
			case CLEAR_WEIGHT :
				break;
			case CHOOSE_PRODUCT :
				break;
			case PUT_TARE :
				break;
			case WEIGH_COMMODITY :
				break;
			case BRUTTO_CONTROL :
				break;
			case END_OF_WEIGHING :
				break;
			}
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
						user = uDao.getUser(labId);
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
					if(msg.getMsg().length() > 0)
					{
						int prodId = Integer.parseInt(msg.getMsg());
						ProductBatchDAO prBDAO = new ProductBatchDAO();
						prod = prBDAO.get(prodId);
						if(prod != null)
						{

							currentRecipeComp = prBDAO.getNonWeightedComp(prodId);
							if(currentRecipeComp != null)
							{
								// Now that we have something to weigh we need to remove everything from the weight
								// And the labworker has to press [-> when it is done

								lastMessageSent = "P111 \"Fjern alt fra vægten [->\"";
								expectedType = MessageType.P111_A;
								wStep++;
								conn.outputMsg(lastMessageSent);


							}
							else
							{
								wStep = 5;
								conn.outputMsg(lastMessageSent);
							}
						}
						else
						{
							wStep = 5;
							conn.outputMsg(lastMessageSent);
						}
					}
					else
					{
						wStep = 5;
						conn.outputMsg(lastMessageSent);
					}
					break;
				case 7 :
					expectedType = MessageType.K_C_4;
					wStep++;
					break;
				case 8 :
					//Labworker pressed [-> and we tare the weight
					lastMessageSent = "T";
					expectedType = MessageType.TARA_REPLY;
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
					expectedType = MessageType.WEIGHT_REPLY;
					conn.outputMsg(lastMessageSent);
					break;
				case 12 :
					tara = Double.parseDouble(msg.getMsg());
					lastMessageSent = "T";
					wStep++;
					expectedType = MessageType.TARA_REPLY;
					conn.outputMsg(lastMessageSent);
					break;
				case 13 :
					//TODO need commodity name and send it
					lastMessageSent = "P111 \"Hæld "+ " i skålen [->\"";
					expectedType = MessageType.P111_A;
					wStep++;
					conn.outputMsg(lastMessageSent);
					break;
				case 14 :
					wStep++;
					expectedType = MessageType.K_C_4;
					break;
				case 15 :
					lastMessageSent = "S";
					expectedType = MessageType.WEIGHT_REPLY;
					wStep++;
					conn.outputMsg(lastMessageSent);
					break;
				case 16 :
					//TODO needs to check that netto is within limits
					netto = Double.valueOf(msg.getMsg());
					if(netto <= currentRecipeComp.getNomNetWeight()*(1+currentRecipeComp.getTolerance()) 
							&& netto >= currentRecipeComp.getNomNetWeight()*(1-currentRecipeComp.getTolerance()))
					{
						lastMessageSent = "RM20 8 \"Hvilken batch kom råvaren fra?\" \"\" \"&3\"";
						expectedType = MessageType.RM20_B;
						wStep++;
						conn.outputMsg(lastMessageSent);
					}
					else
					{
						lastMessageSent = "P111 \"Forkert mængde, prøv igen [->\"";
						wStep = 14;
						expectedType = MessageType.P111_A;
						conn.outputMsg(lastMessageSent);
					}
					break;
				case 17 :
					expectedType = MessageType.RM20_B;
					wStep++;
					break;
				case 18 :
					int rb_id = Integer.parseInt(msg.getMsg());
					CommodityBatchDAO cBatchDAO = new CommodityBatchDAO();
					cBatch = cBatchDAO.get(rb_id);
					if(cBatch != null)
					{

					}
					else
					{

					}
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
		catch(SQLException e)
		{

		}

	}


	private void loginPhase(SocketInMessage message) throws IOException
	{
		if(RM20Expecting)
		{
			switch(message.getReplyType())
			{
			case RM20_A:
				if(message.getMsg().length()>0)
				{
					int labId = Integer.parseInt(message.getMsg());
					UserDAO uDAO = new UserDAO();
					user = uDAO.getUser(labId);
					if(user != null)
					{
						phase = PhaseType.CLEAR_WEIGHT;
						RM20Expecting = false;
						conn.outputMsg("P111 \"Clear the weight [->\"");
					}
					else
					{
						conn.outputMsg("P111 \"Invalid ID please try again\"");
						try 
						{
							Thread.sleep(2000);
						} 
						catch (InterruptedException e) 
						{
							conn.outputMsg("RM20 8 \"Enter Lab ID\" \"\" \"&3\"");
						}
					}
				}
				else
				{
					conn.outputMsg("P111 \"Invalid ID please try again\"");
					try 
					{
						Thread.sleep(2000);
					} 
					catch (InterruptedException e) 
					{
						conn.outputMsg("RM20 8 \"Enter Lab ID\" \"\" \"&3\"");
					}

				}
				break;
			case RM20_C:
				conn.outputMsg("P111 \"Shutdown? Y:[-> N:Exit\"");
				RM20Expecting = false;
				//TODO ask to close terminal
				break;
			}
		}
		else
		{
			switch(message.getReplyType())
			{
			case SEND :
				conn.disconnect();
				break;
			case EXIT : 	
				conn.outputMsg("RM20 8 \"Enter Lab ID\" \"\" \"&3\"");
				RM20Expecting = true;
				break;
			case TARE :
				break;
			case ZERO : 
				break;
			}
		}

	}

	private void clearWeightPhase(SocketInMessage message) throws IOException
	{
		switch(message.getReplyType())
		{
		case SEND :
			conn.outputMsg("T");
			break;
		case EXIT :
			conn.createNewSession();
			break;
		case TARE : 
			break;
		case ZERO :
			break;
		case TARA_REPLY :
			double t = Double.valueOf(message.getMsg());
			if(t > 0.002)
			{
				conn.outputMsg("Clear the weight [->");
			}
			else
			{
				RM20Expecting = true;
				phase = PhaseType.CHOOSE_PRODUCT;
				conn.outputMsg("RM20 8 \"Enter ProductBatch ID\" \"\" \"&3\"");
			}
			break;
		case WEIGHT_REPLY :
			break;
		}
	}

	private void chooseProductPhase(SocketInMessage message) throws IOException, SQLException
	{
		if(RM20Expecting)
		{
			switch(message.getReplyType())
			{
			case RM20_A :
				if(message.getMsg().length()>0)
				{
					int pb_id = Integer.parseInt(message.getMsg());
					ProductBatchDAO pbDAO = new ProductBatchDAO();
					prod = pbDAO.get(pb_id);
					if(prod != null)
					{
						if(prod.getStatus() < 2)
						{
							currentRecipeComp = pbDAO.getNonWeightedComp(pb_id);
							CommodityDAO cDAO = new CommodityDAO();
							comm = cDAO.get(currentRecipeComp.getCommodityID());
							phase = PhaseType.PUT_TARE;
							conn.outputMsg("P111 \"Place tare on weight [->\"");
							RM20Expecting = false;
						}
						else
						{
							conn.outputMsg("P111 \"This PrBatch is finished\"");
							try 
							{
								Thread.sleep(2000);
							} 
							catch (InterruptedException e) 
							{
								conn.outputMsg("RM20 8 \"Enter ProductBatch ID\" \"\" \"&3\"");
							}
						}
					}
					else
					{
						conn.outputMsg("P111 \"Invalid ID please try again\"");
						try 
						{
							Thread.sleep(2000);
						} 
						catch (InterruptedException e) 
						{
							conn.outputMsg("RM20 8 \"Enter ProductBatch ID\" \"\" \"&3\"");
						}
					}
				}
				else
				{
					conn.outputMsg("P111 \"Invalid ID please try again\"");
					try 
					{
						Thread.sleep(2000);
					} 
					catch (InterruptedException e) 
					{
						conn.outputMsg("RM20 8 \"Enter ProductBatch ID\" \"\" \"&3\"");
					}
				}
				break;
			case RM20_C :
				RM20Expecting = false;
				conn.createNewSession();
				break;
			}
		}
	}

	private void putTarePhase(SocketInMessage message) throws IOException
	{
		switch(message.getReplyType())
		{
		case SEND :
			conn.outputMsg("S");
			break;
		case EXIT :
			conn.createNewSession();
			break;
		case TARE : 
			break;
		case ZERO :
			break;
		case TARA_REPLY :
			double t = Double.valueOf(message.getMsg());
			//If the weight has changed less than 4% between the the two commands
			//Then we accept the result
			if(t >= tara*(1-0.04) && t<= tara*(1+0.04))
			{
				conn.outputMsg("P111 \""+comm.getName()+" [->\"");
				phase = PhaseType.WEIGH_COMMODITY;

			}
			else
			{
				conn.outputMsg("P111 \"Don't disrupt the weight\"");
				try 
				{
					Thread.sleep(2000);
				} 
				catch (InterruptedException e) 
				{
				}

				conn.outputMsg("P111 \"Place Tare on weight [->\"");
			}
			break;
		case WEIGHT_REPLY :
			tara = Double.valueOf(message.getMsg());
			if(tara > 0)
			{
				conn.outputMsg("T");
			}
			else
			{
				conn.outputMsg("P111 \"Tare can't be negative\"");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {

				}
				conn.outputMsg("P111 \"Place Tare on Weight [->\"");

			}
			break;
		}
	}

	private void weighCommodityPhase(SocketInMessage message)
	{
		if(RM20Expecting)
		{
			switch(message.getReplyType())
			{
			case RM20_A :
				break;
			case RM20_C :
				break;
			}
		}
		else
		{
			switch(message.getReplyType())
			{
			case SEND :
				break;
			case EXIT :
				break;
			case TARE : 
				break;
			case ZERO :
				break;
			case TARA_REPLY :
				break;
			case WEIGHT_REPLY :
				break;
			}
		}
	}

	private void bruttoControlPhase(SocketInMessage message)
	{
		switch(message.getReplyType())
		{
		case SEND :
			break;
		case EXIT :
			break;
		case TARE : 
			break;
		case ZERO :
			break;
		case TARA_REPLY :
			break;
		case WEIGHT_REPLY :
			break;
		}
	}

	private void acceptResult()
	{
		//TODO write to database
	}



	public UserDTO getUser() {
		return user;
	}

	public ProductBatchDTO getProd() {
		return prod;
	}

	public RecipeCompDTO getCurrentRecipeComp() {
		return currentRecipeComp;
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

	public void setCurrentPrBatchComp(RecipeCompDTO currentRecipeComp) {
		this.currentRecipeComp = currentRecipeComp;
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
