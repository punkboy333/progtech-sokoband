package DOMparser;

import java.util.List;

public class DOMComunicatorImp implements DomComunicator{

	XMLParser parser = new XMLParser();
	
	
	@Override
	public List<Users> getUsers() {
		return parser.getUsers();
	}
	@Override
	public SokoBandMap getMap(int level , int hard){
		 return parser.getMap(level,hard);		
	}
	@Override
	public boolean getMaxlevel() {
		return parser.getMaxLevel();
	}
	@Override
	public void setMaxlevel() {
		parser.setMaxLevel();
	}
	@Override
	public void addUser(String name , int level , int hard) {
		parser.XmlWriter(name, level , hard);
		
	}

	
}
