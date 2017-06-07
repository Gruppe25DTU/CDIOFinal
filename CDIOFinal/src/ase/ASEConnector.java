package ase;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ASEConnector implements Runnable{

	private String ip;
	private int portNr;
	private ConnectionManager cM;
	
	public ASEConnector(ConnectionManager cM , String ip , int portNr) {
		this.cM = cM;
		this.ip = ip;
		this.portNr = portNr;
	}

	@Override
	public void run() {
		attemptToConnect();
		
	}
	
	public void attemptToConnect()
	{
		try {
			Socket newSock = new Socket(ip , portNr);
			System.out.println("Successfully connected to "+newSock.getInetAddress()+ " at port: "+newSock.getPort());
			this.cM.addConnection(newSock);
		} catch (IOException e) {
			System.out.println("Failed to connect to "+ip);
		}
	}

}
