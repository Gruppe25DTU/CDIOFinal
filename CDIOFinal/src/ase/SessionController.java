
package ase;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

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
	private ASEConnection conn;
	private double tara;
	private double netto;
	private PhaseType phase;
	private CommodityBatchDTO cBatch;
	private boolean RM20Expecting;

	public SessionController(ASEConnection conn) {
		user = null;
		prod = null;
		currentRecipeComp  = null;
		this.conn = conn;
		tara = 0;
		netto = 0;
		cBatch = null;
		phase = PhaseType.LOGIN;
		
		try 
		{
			// Setting the key controls so we can see key codes when buttons on weight are pressed-
			//- but their functions aren't run on the weight
			conn.outputMsg("K 3");
			conn.outputMsg("DW");
			conn.outputMsg("P111 \"\"");
			RM20Expecting = true;
			conn.outputMsg("RM20 8 \"Enter lab ID\" \"\" \"&3\"");

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void processInput(SocketInMessage msg) throws IOException
	{
		System.out.println("Input: "+msg.getMsg() + " type : "+msg.getReplyType() + " at phase: "+phase);
		try
		{
			switch(phase)
			{
			case LOGIN :
				loginPhase(msg);
				break;
			case CONFIRM_NAME :
				confirmNamePhase(msg);
				break;
			case CHOOSE_PRODUCT :
				chooseProductPhase(msg);;
				break;
			case CLEAR_WEIGHT :
				clearWeightPhase(msg);
				break;
			case PUT_TARE :
				putTarePhase(msg);
				break;
			case WEIGH_COMMODITY :
				weighCommodityPhase(msg);
				break;
			case CHOOSE_COMM_BATCH :
				chooseCommBatchPhase(msg);
				break;
			case BRUTTO_CONTROL :
				bruttoControlPhase(msg);
				break;
			case END_OF_WEIGHING :
				endOfWeighing(msg);
				break;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
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
						if((user.getRoles().contains("LaboratoryTechnician") 
						|| user.getRoles().contains("Pharmacist")
						|| user.getRoles().contains("Foreman")) && user.getStatus()==1)
						{
							//Progress to next phase --Confirm the user name--
							String name = user.getFirstName()+" "+user.getLastName();
							if(name.length() > 20)
								name = user.getFirstName()+" "+user.getLastName().charAt(0);
							String output = "P111 \""+name+"? Y:[-> N:Exit\"";
							phase = PhaseType.CONFIRM_NAME;
							RM20Expecting = false;
							conn.outputMsg(output);
							
							
						}
						else
						{
							conn.outputMsg("P111 \"You can't use the weight\"");
							try 
							{
								Thread.sleep(2000);
							} 
							catch (InterruptedException e) 
							{
								
							}
							conn.outputMsg("P111 \"\"");
							conn.outputMsg("RM20 8 \"Enter Lab ID\" \"\" \"&3\"");
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
							
						}
						conn.outputMsg("P111 \"\"");
						conn.outputMsg("RM20 8 \"Enter Lab ID\" \"\" \"&3\"");
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
						
					}
					conn.outputMsg("P111 \"\"");
					conn.outputMsg("RM20 8 \"Enter Lab ID\" \"\" \"&3\"");

				}
				break;
			case RM20_C:
				conn.outputMsg("P111 \"Shutdown? Y:[-> N:Exit\"");
				RM20Expecting = false;
				break;
			default :
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
			default : 
				break;
			}
		}

	}
	
	private void confirmNamePhase(SocketInMessage message) throws IOException
	{
		switch(message.getReplyType())
		{
		case SEND :
			
			//Progress to next phase --Enter the ID of the product batch--
			RM20Expecting = true;
			phase = PhaseType.CHOOSE_PRODUCT;
			conn.outputMsg("RM20 8 \"Enter ProductBatch ID\" \"\" \"&3\"");
			break;
		case EXIT :
			conn.createNewSession();
			break;
		default :
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
							//Progress to the next phase -- Clear the weight--
							currentRecipeComp = pbDAO.getNonWeighedComp(pb_id);
							CommodityDAO cDAO = new CommodityDAO();
							comm = cDAO.get(currentRecipeComp.getCommodityID());
							phase = PhaseType.CLEAR_WEIGHT;
							conn.outputMsg("P111 \"Clear the weight [->\"");
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
								
							}
							conn.outputMsg("P111 \"\"");
							conn.outputMsg("RM20 8 \"Enter ProductBatch ID\" \"\" \"&3\"");
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
							
						}
						conn.outputMsg("P111 \"\"");
						conn.outputMsg("RM20 8 \"Enter ProductBatch ID\" \"\" \"&3\"");
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
						
					}
					conn.outputMsg("P111 \"\"");
					conn.outputMsg("RM20 8 \"Enter ProductBatch ID\" \"\" \"&3\"");
				}
				break;
			case RM20_C :
				RM20Expecting = false;
				conn.createNewSession();
				break;
			default : 
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
				conn.outputMsg("P111 \"Clear the weight [->\"");
			}
			else
			{
				//Progress to the next phase -- Put tare on the weight --
				phase = PhaseType.PUT_TARE;
				conn.outputMsg("P111 \"Place tare on weight [->\"");
			}
			break;
		case WEIGHT_REPLY :
			break;
		default : 
			break;
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
				//Progress to the next phase --Weighing the commodity--
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
				conn.outputMsg("P111 \"can't be negative or zero\"");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {

				}
				conn.outputMsg("P111 \"Place Tare on Weight [->\"");

			}
			break;
		default : 
			break;
		}
	}

	private void weighCommodityPhase(SocketInMessage message) throws IOException
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
			break;
		case WEIGHT_REPLY :
			netto = Double.valueOf(message.getMsg());
			double lowerBound = currentRecipeComp.getNomNetWeight()*(1-currentRecipeComp.getTolerance()/100.0);
			double upperBound = currentRecipeComp.getNomNetWeight()*(1+currentRecipeComp.getTolerance()/100.0);
			//First checks whether netto is lower than lowerbound then if it's higher than upper bound
			if(netto < lowerBound)
			{
				conn.outputMsg("P111 \"Minimum: "+lowerBound+" kg [->\"");

			}
			else if(netto > upperBound)
			{
				conn.outputMsg("P111 \"Maximum: "+upperBound+" kg [->\"");
			}
			else
			{
				//Weight is accepted and we progress the the next phase --Enter the ID of the commodity batch --
				RM20Expecting = true;
				phase = PhaseType.CHOOSE_COMM_BATCH;
				conn.outputMsg("RM20 8 \"Enter CommodityBatch ID\" \"\" \"&3\"");
			}
			break;
		default : 
			break;
		}
	}

	private void chooseCommBatchPhase(SocketInMessage message) throws IOException
	{
		if(RM20Expecting)
		{
			switch(message.getReplyType())
			{
			case RM20_A :
				if(message.getMsg().length()>0)
				{
					int cb_id = Integer.parseInt(message.getMsg());
					CommodityBatchDAO cbDAO = new CommodityBatchDAO();
					cBatch = cbDAO.get(cb_id);
					if(cBatch != null)
					{
						//Progress to the next phase -- Perform brutto control (Weight must be equal to -tare)--
						if(cBatch.getCommodityID() == currentRecipeComp.getCommodityID())
						{
							if(cBatch.getQuantity() >= netto)
							{
								RM20Expecting = false;
								phase = PhaseType.BRUTTO_CONTROL;
								conn.outputMsg("P111 \"Clear the weight [->\"");
							}
							else
							{
								conn.outputMsg("P111 \"Insufficient quantity!\"");
								try 
								{
									Thread.sleep(2000);
								} 
								catch (InterruptedException e) {
									
								}
								conn.outputMsg("P111 \"\"");
								conn.outputMsg("RM20 8 \"Enter another CommBatch ID\" \"\" \"&3\"");
							}
							
						}
						else
						{
							conn.outputMsg("P111 \"Wrong commodity! try again\"");
							try 
							{
								Thread.sleep(2000);
							} 
							catch (InterruptedException e) 
							{
								
							}
							conn.outputMsg("P111 \"\"");
							conn.outputMsg("RM20 8 \"Enter CommodityBatch ID\" \"\" \"&3\"");
						}
						
					}
					else
					{
						conn.outputMsg("P111 \"Invalid ID try again\"");
						try 
						{
							Thread.sleep(2000);
						}
						catch (InterruptedException e) 
						{

						}
						conn.outputMsg("P111 \"\"");
						conn.outputMsg("RM20 8 \"Enter CommodityBatch ID\" \"\" \"&3\"");
					}
				}
				else
				{
					conn.outputMsg("P111 \"Invalid ID try again\"");
					try 
					{
						Thread.sleep(2000);
					}
					catch (InterruptedException e) 
					{

					}
					conn.outputMsg("P111 \"\"");
					conn.outputMsg("RM20 8 \"Enter CommodityBatch ID\" \"\" \"&3\"");
				}
				break;
			case RM20_C :
				RM20Expecting = false;
				conn.createNewSession();
				break;
			default :
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
			default : 
				break;
			}
		}
	}

	private void bruttoControlPhase(SocketInMessage message) throws IOException
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
			//Progress to next phase --Write to database and then ask labtech to continue weighing--
			phase = PhaseType.END_OF_WEIGHING;
			acceptResult();
			break;
		case WEIGHT_REPLY :
			double w = Math.abs(Double.valueOf(message.getMsg()));
			if(w <= tara*(1+0.02) && w >= tara*(1-0.02))
			{
				//ACCEPT THE RESULT
				conn.outputMsg("T");
			}
			else
			{
				conn.outputMsg("P111 \"You haven't cleared the weight\"");
				try 
				{
					Thread.sleep(2000);
				} 
				catch (InterruptedException e) {

				}
				conn.outputMsg("P111 \"Clear the weight [->\"");
			}
			break;
		default : 
			break;
		}
	}

	private void endOfWeighing(SocketInMessage message) throws IOException
	{
		switch(message.getReplyType())
		{
		case SEND :
			conn.outputMsg("P111 \"Clear the weight [->\"");
			phase = PhaseType.CLEAR_WEIGHT;
			break;
		case EXIT :
			conn.createNewSession();
			break;
		default :
			break;
		}
	}

	private void acceptResult() throws IOException
	{
		conn.outputMsg("D \"SAVING\"");
		ProductBatchDAO pbDAO = new ProductBatchDAO();
		CommodityBatchDAO cbDAO = new CommodityBatchDAO();
		CommodityDAO cDAO = new CommodityDAO();

		ProductBatchCompDTO component = 
				new ProductBatchCompDTO(prod.getId() , 
										cBatch.getId(), 
										tara, netto , user.getId());

		System.out.println("Created ProductBatchComponent: "+pbDAO.addComponent(component));
		System.out.println("Changed CommodityBatch: "+cbDAO.changeAmount(cBatch.getId(), cBatch.getQuantity()-netto));
		

		try 
		{
			Thread.sleep(1000);
		} 
		catch (InterruptedException e1) {
			
		}
		conn.outputMsg("DW");
		currentRecipeComp = pbDAO.getNonWeighedComp(prod.getId());
		comm = cDAO.get(currentRecipeComp.getCommodityID());
		if(currentRecipeComp != null)
		{
			//There is more to weigh so we ask if the lab tech wishes to continue
			if(prod.getStatus() == 0)
			{	
				prod.setStatus(1);
				pbDAO.changeStatus(prod.getId(), 1);
			}
			phase = PhaseType.END_OF_WEIGHING;
			conn.outputMsg("P111 \"Continue? Y:[-> N:Exit\"");

		}
		else
		{
			//There is nothing more to weigh on this productbatch so it's finished 
			prod.setStatus(2);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			prod.setEndDate(ts);
			pbDAO.changeStatus(prod.getId(), 2);
			pbDAO.setStopdate(prod);
			conn.outputMsg("P111 \"ProductBatch finished!\"");
			try 
			{
				Thread.sleep(2000);
			} 
			catch (InterruptedException e)
			{
			}
			conn.createNewSession();
		}
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public ProductBatchDTO getProd() {
		return prod;
	}

	public void setProd(ProductBatchDTO prod) {
		this.prod = prod;
	}

	public RecipeCompDTO getCurrentRecipeComp() {
		return currentRecipeComp;
	}

	public void setCurrentRecipeComp(RecipeCompDTO currentRecipeComp) {
		this.currentRecipeComp = currentRecipeComp;
	}

	public CommodityDTO getComm() {
		return comm;
	}

	public void setComm(CommodityDTO comm) {
		this.comm = comm;
	}

	public ASEConnection getConn() {
		return conn;
	}

	public void setConn(ASEConnection conn) {
		this.conn = conn;
	}

	public double getTara() {
		return tara;
	}

	public void setTara(double tara) {
		this.tara = tara;
	}

	public double getNetto() {
		return netto;
	}

	public void setNetto(double netto) {
		this.netto = netto;
	}

	public PhaseType getPhase() {
		return phase;
	}

	public void setPhase(PhaseType phase) {
		this.phase = phase;
	}

	public CommodityBatchDTO getcBatch() {
		return cBatch;
	}

	public void setcBatch(CommodityBatchDTO cBatch) {
		this.cBatch = cBatch;
	}

	public boolean isRM20Expecting() {
		return RM20Expecting;
	}

	public void setRM20Expecting(boolean rM20Expecting) {
		RM20Expecting = rM20Expecting;
	}



}
