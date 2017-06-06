package ase;

import java.net.Socket;

public class ConnectionManager {

	private Connection[] connections;
	
	public ConnectionManager() {
		connections = new Connection[10];
		
	}
	
	public void disconnect( int connNr)
	{
		
	}
	
	public void connect(String ip , int portNr)
	{
		new Thread(new ASEConnector(this , ip , portNr)).start();
	}
	
	public void addConnection(Socket socket)
	{
		
	}
	
	public String toString()
	{
		for( int i = 0 ; i < connections.length ; i++ )
		{
			return null;
		}
		return "";
	}

}
