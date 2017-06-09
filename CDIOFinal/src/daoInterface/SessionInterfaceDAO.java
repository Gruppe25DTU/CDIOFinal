package daoInterface;

public interface SessionInterfaceDAO {
	void logout(int ID);
	boolean login(String name, String pwd);
}
