package ase;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ASEConnection {

	private Socket socket;
	private SessionController sesh;
	private WeightInput input;
	private DataOutputStream output;
	private ASEConnectionManager cM;
	private int connNr;
	
	public ASEConnection(Socket socket , ASEConnectionManager cM , int connNr) throws IOException {
			this.socket = socket;
			this.input = new WeightInput(socket.getInputStream() , this);
			this.output = new DataOutputStream(socket.getOutputStream());
			sesh = new SessionController(this);
			this.cM = cM;
			this.connNr = connNr;
			new Thread(this.input).start();
	}
	
	public void disconnect() throws IOException
	{
		this.outputMsg("P111 \"\"");
		this.outputMsg("K 1");
		this.outputMsg("Q");
		this.cM.disconnect(connNr);
		this.socket.close();
		
	}
	
	public void processInput(SocketInMessage msg) throws IOException
	{
		this.sesh.processInput(msg);
	}
	
	public void outputMsg(String msg) throws IOException
	{	
		System.out.println("Output: "+msg);
		msg = msg + "\r\n";
		this.output.write(msg.getBytes());
		this.output.flush();
	}
	
	public void createNewSession()
	{
		this.sesh = new SessionController(this);
	}
	
	public Socket getSocket()
	{
		return socket;
	}

}
