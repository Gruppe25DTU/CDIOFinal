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
	
	public void switchBroadCastingState(int connNr)
	{
		if(connections[connNr] != null)
			connections[connNr].setBroadcasting(!connections[connNr].isBroadcasting());
	}
	
	public String toString()
	{
		String result = "";
		result += "#|__IP____________|__PortNr__|Broadcasting|";
		for( int i = 0 ; i < connections.length ; i++ )
		{
			
			if(connections[i] != null)
			{
				String line = "\n";
				int whitespace = 0;
				line += i;
				line += "|"+connections[i].getSocket().getInetAddress();
				whitespace = 18-line.length();
				for(int w = 0 ; w < whitespace ; w++){line += " ";}
				line += "|" + connections[i].getSocket().getPort();
				whitespace = 29-line.length();
				for(int w = 0 ; w < whitespace ; w++){line += " ";}
				line += "|"+connections[i].isBroadcasting();
				whitespace = 42 - line.length();
				for(int w = 0 ; w < whitespace ; w++){line += " ";}
				line += "|";
				result += line;
				
			}
				         
		}
		return result;
	}

}
