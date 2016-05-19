package DOMparser;

import java.util.List;

public interface DomComunicator {

	
	public List<Users> getUsers() ;
	public SokoBandMap getMap(int level , int hard);
	public boolean getMaxlevel() ;
	public void setMaxlevel() ;
	public void addUser(String name , int level , int hard);
	
}
