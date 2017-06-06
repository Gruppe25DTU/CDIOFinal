package ase;

import java.io.DataOutputStream;
import java.net.Socket;

public class Connection {

	private Socket socket;
	private Session sesh;
	private WeightInput input;
	private DataOutputStream output;
	
	public Connection(Socket socket) {
		this.socket = socket;
		sesh = new Session();
		
	}
	
	public void disconnect()
	{
		
	}
	
	public String processInput(SocketInMessage msg)
	{
		return null;
	}
	
	public void outputMsg(String msg)
	{
		
	}
	
	public void createNewSession()
	{
		this.sesh = new Session();
	}

}
