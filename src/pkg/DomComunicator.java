package pkg;

import java.util.List;

import DOMparser.Users;

public interface DomComunicator {

	
	public List<Users> getUsers() ;
	public int[][] getMap(int level);
	public boolean getMaxlevel() ;
	public void setMaxlevel() ;
	public void addUser(String name , int level);
	
}
