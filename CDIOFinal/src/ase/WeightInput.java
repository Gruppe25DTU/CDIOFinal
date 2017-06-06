package ase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WeightInput implements Runnable{

	private BufferedReader input;
	private Connection conn;
	
	public WeightInput(InputStream in , Connection conn) {
		this.input = new BufferedReader(new InputStreamReader(in));
		this.conn = conn;
	}

	@Override
	public void run() {
		listenForMessages();
		
	}
	
	public void listenForMessages()
	{
		try
		{
			while(true)
			{
				String msg = input.readLine();
			}
		}
		catch(IOException e)
		{
			
		}
		finally
		{
			try {
				input.close();
			} catch (IOException e) {
			}
		}
		
	}

}
