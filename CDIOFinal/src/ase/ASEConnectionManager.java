package ase;

import java.io.IOException;
import java.net.Socket;

public class ASEConnectionManager {

	private ASEConnection[] connections;
	
	public ASEConnectionManager() {
		connections = new ASEConnection[10];
		
	}
	
	public void disconnect( int connNr) throws IOException
	{
		if(connections.length > connNr && connections[connNr] != null)
		{
			ASEConnection temp = connections[connNr];
			connections[connNr] = null;
			temp.disconnect();
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
				connections[i] = new ASEConnection(socket, this , i);
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
