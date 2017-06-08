package ase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WeightInput implements Runnable{

	private BufferedReader input;
	private Connection conn;

	public WeightInput(InputStream in , Connection conn) {
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
		System.out.println(input);
		try{
			SocketInMessage sInMsg;
			switch(input.split(" ")[0])
			{
			case "RM20" :

				if(input.split(" ").length <= 1)
					break;

				if(input.split(" ")[1].equals("B"))
				{
					sInMsg = new SocketInMessage(MessageType.RM20_B , input);
					conn.processInput(sInMsg);
				}
				else if(input.split(" ")[1].equals("A"))
				{
					if(input.split("\"").length>1)
						input = input.split("\"")[1];
					sInMsg = new SocketInMessage(MessageType.RM20_A , "");
					conn.processInput(sInMsg);
				}
				break;
			case "P111" :
				sInMsg = new SocketInMessage(MessageType.P111_A , input);
				conn.processInput(sInMsg);
				break;
			case "S" : 
				input = input.split(" ")[6];
				sInMsg = new SocketInMessage(MessageType.WEIGHT , input);
				conn.processInput(sInMsg);
				break;
			case "T" :
				input = input.split(" ")[6];
				sInMsg = new SocketInMessage(MessageType.TARA , input);
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
				sInMsg = new SocketInMessage(MessageType.K_C_4 , input);
				conn.processInput(sInMsg);
				break;
			case "F" :
				sInMsg = new SocketInMessage(MessageType.K_F_4 , input);
				conn.processInput(sInMsg);
				break;
			default : 
				break;
			}
		}

	}

}
