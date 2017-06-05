package ase;

public class Main implements Runnable{

	private ConnectionManager cM;
	
	public Main() {
		cM = new ConnectionManager();
	}

	public static void main(String[] args) {
		new Thread(new Main()).start();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	

}
