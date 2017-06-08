package ase;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {

	private Socket socket;
	private Session sesh;
	private WeightInput input;
	private DataOutputStream output;
	
	public Connection(Socket socket) throws IOException {
			this.socket = socket;
			this.input = new WeightInput(socket.getInputStream() , this);
			this.output = new DataOutputStream(socket.getOutputStream());
			sesh = new Session(this);
			new Thread(this.input).start();
	}
	
	public void disconnect() throws IOException
	{
		this.socket.close();
	}
	
	public void processInput(SocketInMessage msg) throws IOException
	{
		this.sesh.processInput(msg);
	}
	
	public void outputMsg(String msg) throws IOException
	{	
		msg = msg + "\r\n";
		this.output.write(msg.getBytes());
		this.output.flush();
	}
	
	public void createNewSession()
	{
		this.sesh = new Session(this);
	}
	
	public Socket getSocket()
	{
		return socket;
	}

}
