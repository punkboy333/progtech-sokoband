package pkg;

import java.util.List;

import DOMparser.Users;
import DOMparser.XMLParser;

public class DOMComunicatorImp implements DomComunicator{

	XMLParser parser = new XMLParser();
	
	
	@Override
	public List<Users> getUsers() {
		return parser.getUsers();
	}
	@Override
	public int[][] getMap(int level){
		 return parser.getMap(level);		
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
	public void addUser(String name , int level) {
		parser.XmlWriter(name, level);
		
	}

	
}
