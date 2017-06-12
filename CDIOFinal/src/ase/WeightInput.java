package ase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;


public class WeightInput implements Runnable{

	private BufferedReader input;
	private ASEConnection conn;

	public WeightInput(InputStream in , ASEConnection conn) {
		this.input = new BufferedReader(new InputStreamReader(in));
		this.conn = conn;
	}

	@Override
	public void run() {
		listenForMessages();

	}

	public void listenForMessages()
	{
		try
		{
			while(true)
			{
				String msg = input.readLine();
				processInput(msg);
			}
		}
		catch(IOException e)
		{
			
		}
		finally
		{
			try {
				input.close();
			} catch (IOException e) {
			}
		}

	}

	private void processInput(String input)
	{
		if(input!=null)
		{
			System.out.println(Arrays.asList(input.split(" ")));
			try{
				SocketInMessage sInMsg;
				switch(input.split(" ")[0])
				{
				case "RM20" :
					
					
					if(input.split(" ").length <= 1)
						break;
					
					char rm = input.split(" ")[1].charAt(0);
					if(rm == 'B')
					{
						sInMsg = new SocketInMessage(MessageType.RM20_B , input);
						conn.processInput(sInMsg);
					}
					else if(rm == 'A')
					{
						if(input.split("\"").length>1)
							input = input.split("\"")[1];
						else
							input = "";
						sInMsg = new SocketInMessage(MessageType.RM20_A , input);
						conn.processInput(sInMsg);
					}
					else if(rm == 'C')
					{
						sInMsg = new SocketInMessage(MessageType.RM20_C , input);
						conn.processInput(sInMsg);	
					}
					break;
				case "P111" :
					sInMsg = new SocketInMessage(MessageType.P111_A , input);
					conn.processInput(sInMsg);
					break;
				case "S" : 
					System.out.println(input.length() + input.split(" ")[6]);
					if(input.split(" ").length == 8)
						input = input.split(" ")[6];
					else
						input = input.split(" ")[7];
					System.out.println(input);
					sInMsg = new SocketInMessage(MessageType.WEIGHT_REPLY , input);
					conn.processInput(sInMsg);
					break;
				case "T" :
					if(input.split(" ").length == 8)
						input = input.split(" ")[6];
					else
						input = input.split(" ")[7];
					sInMsg = new SocketInMessage(MessageType.TARA_REPLY , input);
					conn.processInput(sInMsg);
					break;
				case "DW" : 
					sInMsg = new SocketInMessage(MessageType.DW_A , input);
					conn.processInput(sInMsg);
					break;
				case "D" :
					sInMsg = new SocketInMessage(MessageType.D_A , input);
					conn.processInput(sInMsg);
					break;
				case "K" :
					checkKValues(input);
					break;
				case "ES" :
					sInMsg = new SocketInMessage(MessageType.ERROR , input);
					conn.processInput(sInMsg);
					break;
				default : 
					break;
				}
			}
			catch(IOException e)
			{

			}
		}
		
	}

	private void checkKValues(String input) throws IOException
	{
		if(input.split(" ").length > 1)
		{
			SocketInMessage sInMsg;
			switch(input.split(" ")[1])
			{
			case "A" :
				sInMsg = new SocketInMessage(MessageType.K_A , input);
				conn.processInput(sInMsg);
				break;
			case "C" :
				switch(input.split(" ")[2])
				{
				case "2" : 
					sInMsg = new SocketInMessage(MessageType.ZERO , input);
					conn.processInput(sInMsg);
					break;
				case "3" :
					sInMsg = new SocketInMessage(MessageType.TARE , input);
					conn.processInput(sInMsg);
					break;
				case "4" :
					sInMsg = new SocketInMessage(MessageType.SEND , input);
					conn.processInput(sInMsg);
					break;
				default :
					break;
				}
				
				break;
			case "R" :
				sInMsg = new SocketInMessage(MessageType.EXIT , input);
				conn.processInput(sInMsg);
				break;
			default : 
				break;
			}
		}

	}

}
