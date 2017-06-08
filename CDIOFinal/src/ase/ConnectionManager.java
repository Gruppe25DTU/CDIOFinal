package ase;

import java.io.IOException;
import java.net.Socket;

public class ConnectionManager {

	private Connection[] connections;
	
	public ConnectionManager() {
		connections = new Connection[10];
		
	}
	
	public void disconnect( int connNr) throws IOException
	{
		if(connections.length > connNr && connections[connNr] != null)
		{
			connections[connNr].disconnect();
			connections[connNr] = null;
		}
	}
	
	public void connect(String ip , int portNr)
	{
		new Thread(new ASEConnector(this , ip , portNr)).start();
	}
	
	public void addConnection(Socket socket) throws IOException
	{
		for(int i = 0;  i < connections.length ; i++)
		{
			if(connections[i] == null)
			{
				connections[i] = new Connection(socket);
				break;
			}
				
		}
	}
	
	public void disconnectAll() throws IOException
	{
		for(int i = 0 ; i<connections.length ; i++)
		{
			if(connections[i] != null)
				connections[i].disconnect();
		}
	}
	
	public String toString()
	{
		String result = "";
		result += "#__|__IP_______________|__PortNr__|\n";
		for( int i = 0 ; i < connections.length ; i++ )
		{
			if(connections[i] != null)
				result += i +"  |  "+ connections[i].getSocket().getInetAddress()+ " |"+connections[i].getSocket().getPort()+ "\n";
		}
		return result;
	}

}
