package ase;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main implements Runnable{

	private ConnectionManager cM;
	private Scanner keyb;
	private boolean running;
	
	public Main() {
		cM = new ConnectionManager();
		keyb = new Scanner(System.in);
		running = true;
	}

	public static void main(String[] args) {
		new Thread(new Main()).start();

	}

	@Override
	public void run() {
	
		Date d = new Date(System.currentTimeMillis());
		Time t = new Time(System.currentTimeMillis());
		String dString = d.toString()+" "+t.toString();
		System.out.println(dString);
		
		
		while(running)
		{
			System.out.println("Options: disconnect | connect | list | quit");
			String in = keyb.nextLine();
			try 
			{
				checkCommand(in);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public void checkCommand(String in) throws IOException
	{
		switch(in)
		{
		case "disconnect" : 
			disconnect();
			break;
		case "connect" : 
			connect();
			break;
		case "list" : 
			System.out.println("\n" + cM);
			break;
		case "quit" :
			cM.disconnectAll();
			running = false;
			break;
		default : 
			System.out.println("Invalid command");
			break;
		}
	}
	
	public void disconnect()
	{
		try 
		{
			System.out.println("\n" + cM);
			System.out.println("Write a number from 0-9 to disconnect a connections to a weight");
			String in = keyb.nextLine();
			int connNr = Integer.parseInt(in);
			cM.disconnect(connNr);
		} 
		catch (NumberFormatException e)
		{
			System.out.println("Please write a number");
		}
		catch (IOException e) 
		{
		
		}
		
	}
	
	public void connect()
	{
		try
		{
			System.out.println("Enter the ip address you want to connect to");
			String ip = keyb.nextLine();
			System.out.println("Enter the port number you want to connect to");
			int portNr = Integer.parseInt(keyb.nextLine());
			cM.connect(ip, portNr);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Please enter a number");
		}
	}
	

}
