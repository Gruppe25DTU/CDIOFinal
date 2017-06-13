
package ase;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

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
import logic.CDIOException.DALException;

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

	public void processInput(SocketInMessage msg)
	{
		
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
			try 
			{
				conn.outputMsg("P111 \"*DATABASE ERROR*\"");
			} 
			catch (IOException e1) {
				
			}
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}


	private void loginPhase(SocketInMessage message) throws IOException, SQLException
	{
		if(RM20Expecting)
		{
			switch(message.getReplyType())
			{
			case RM20_A:
				if(message.getMsg().length()>0)
				{
					conn.outputMsg("P111 \"...\"");
					int labId = Integer.parseInt(message.getMsg());
					UserDAO uDAO = new UserDAO();
					try 
					{
						user = uDAO.get(labId);
					} 
					catch (DALException e1) {
						throw new SQLException("Problem occured trying to retrieve User");
					}

					if(user != null)
					{
					  ArrayList<String> roles = (ArrayList<String>) Arrays.asList(user.getRoles());
						if((roles.contains("LaboratoryTechnician") 
								|| roles.contains("Pharmacist")
								|| roles.contains("Foreman")) && user.getStatus()==1)
						{
							//Progress to next phase --Confirm the user name--
							String name = user.getFirstName().charAt(0)+" "+user.getLastName();
							if(name.length() > 25)
								name = name.substring(0, 25);
							name.replace("\"", ""); // removes quotation marks that might confuse the weight
							String output = "P111 \""+name+"? [->\"";
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
				conn.outputMsg("P111 \"Shutdown? y:[-> n:Exit\"");
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
					conn.outputMsg("P111 \"...\"");
					try {
						prod = pbDAO.get(pb_id);
					} catch (DALException e2) {
						throw new SQLException("Problem occured trying to retrieve Product batch");
					}
					if(prod != null)
					{
						if(prod.getStatus() < 2)
						{
							//Progress to the next phase -- Clear the weight--
							try {
								currentRecipeComp = pbDAO.getNonWeighedComp(pb_id);
							} catch (DALException e1) {
								throw new SQLException("Problem occured trying to retrieve Recipe component");
							}
							CommodityDAO cDAO = new CommodityDAO();
							try {
								comm = cDAO.get(currentRecipeComp.getCommodityID());
							} catch (DALException e) {
								throw new SQLException("Problem occured trying to retrieve Commodity");
							}
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
			if(t > 0.020)
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
			//Progress to the next phase --Weighing the commodity--
			String name = comm.getName().length() > 17 ? comm.getName().substring(0 , 17) : comm.getName();
			String nom_netto = Double.toString(currentRecipeComp.getNomNetWeight()).replace(",", ".");
			nom_netto = nom_netto.length() > 5 ? nom_netto.substring(0 , 5)+" kg" : nom_netto+ " kg";
			conn.outputMsg("P111 \""+name+ " "+nom_netto+" [->\"");
			phase = PhaseType.WEIGH_COMMODITY;
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
				String lBS = Double.toString(lowerBound).replaceAll(",", ".");
				lBS = lBS.length() > 6 ? lBS.substring(0 , 6) : lBS.substring(0 , lBS.length());
				conn.outputMsg("P111 \"Minimum: "+lBS+" kg [->\"");

			}
			else if(netto > upperBound)
			{
				String uBS = Double.toString(upperBound).replaceAll(",", ".");
				uBS = uBS.length() > 6 ? uBS.substring(0 , 6) : uBS.substring(0 , uBS.length());
				conn.outputMsg("P111 \"Maximum: "+uBS+" kg [->\"");
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

	private void chooseCommBatchPhase(SocketInMessage message) throws IOException, SQLException
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
					conn.outputMsg("P111 \"...\"");
					try 
					{
						cBatch = cbDAO.get(cb_id);
					} 
					catch (DALException e1) {
						throw new SQLException("Problem occured trying to retrieve Commodity batch");
					}
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

	private void bruttoControlPhase(SocketInMessage message) throws IOException, SQLException
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

	private void acceptResult() throws IOException, SQLException
	{
		conn.outputMsg("D \"SAVING\"");
		conn.outputMsg("P111 \"...\"");
		ProductBatchDAO pbDAO = new ProductBatchDAO();
		CommodityDAO cDAO = new CommodityDAO();

		ProductBatchCompDTO component = 
				new ProductBatchCompDTO(prod.getId() , 
						cBatch.getId(), 
						tara, netto , user.getId());

		try 
		{
			pbDAO.addComponent(component);
			CommodityBatchDAO.changeAmount(cBatch.getId(), cBatch.getQuantity()-netto);
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e1) {

			}
			conn.outputMsg("DW");
			currentRecipeComp = pbDAO.getNonWeighedComp(prod.getId());
			if(currentRecipeComp != null)
			{
				comm = cDAO.get(currentRecipeComp.getCommodityID());
				//There is more to weigh so we ask if the lab tech wishes to continue
				if(prod.getStatus() == 0)
				{	
					prod.setStatus(1);
					pbDAO.changeStatus(prod.getId(), 1);
				}
				phase = PhaseType.END_OF_WEIGHING;
				conn.outputMsg("P111 \"Continue? [->\"");

			}
			else
			{
				//There is nothing more to weigh on this productbatch so it's finished 
				prod.setStatus(2);
				Timestamp ts = new Timestamp(System.currentTimeMillis());
				prod.setEndDate(ts);
				pbDAO.changeStatus(prod.getId(), 2);
				ProductBatchDAO.setStopdate(prod);
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
		catch (DALException e2) 
		{
			throw new SQLException("Problem occured when saving to database");
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
