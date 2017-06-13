package ase;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ASEConnection {

	private Socket socket;
	private SessionController sesh;
	

	public void setBroadcasting(boolean broadcasting) {
		this.broadcasting = broadcasting;
	}

	private WeightInput input;
	private DataOutputStream output;
	private ASEConnectionManager cM;
	private volatile boolean broadcasting;
	private int connNr;
	
	public ASEConnection(Socket socket , ASEConnectionManager cM , int connNr) throws IOException {
			this.socket = socket;
			this.input = new WeightInput(socket.getInputStream() , this);
			this.output = new DataOutputStream(socket.getOutputStream());
			sesh = new SessionController(this);
			this.cM = cM;
			this.connNr = connNr;
			broadcasting = false;
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
		if(broadcasting)
			System.out.println
				   ("Connection#"+connNr+
					" Input: "+msg.getMsg() + 
					" type : "+msg.getReplyType() + 
					" at phase: "+sesh.getPhase());
		
		this.sesh.processInput(msg);
	}
	
	public void outputMsg(String msg) throws IOException
	{	if(broadcasting)
			System.out.println("Output: "+msg+ " at phase: "+sesh.getPhase());
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
	
	public SessionController getSesh() {
		return sesh;
	}

	public void setSesh(SessionController sesh) {
		this.sesh = sesh;
	}

	public WeightInput getInput() {
		return input;
	}

	public void setInput(WeightInput input) {
		this.input = input;
	}

	public DataOutputStream getOutput() {
		return output;
	}

	public void setOutput(DataOutputStream output) {
		this.output = output;
	}

	public boolean isBroadcasting() {
		return broadcasting;
	}

}
