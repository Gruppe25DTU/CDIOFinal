package ase;

public class WeightAddress {

	private String ip;
	private int port;
	
	public WeightAddress(String ip , int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public int port()
	{
		return port;
	}
	
	public String ip()
	{
		return ip;
	}

}
