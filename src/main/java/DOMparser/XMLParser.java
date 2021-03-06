package DOMparser;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class XMLParser {
	

	
	File xmlfile = null;
	int[][] GameMap ;
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder documentBuldar;
	Document doc = null;
	boolean maxlevel=false;
	
	 private static final int VAL=0;  
	 private static final int ROAD=1;  
	 private static final int PLAYER=2;  
	 private static final int BOX=3;  
	 private static final int CHECPOINT=4;		
	 
	private void matrixBuilder(int max ){
		GameMap = new int[max][max];
		
		
	}
	public SokoBandMap getMap(int level , int hard){
		return DomRead(level , hard);
		
	}
	public NodeList openFile( String name) {
		
		
		try{
			documentBuldar = factory.newDocumentBuilder();
		}catch(Exception e){;}try {
			doc = documentBuldar.parse(xmlfile);

		
		} catch (Exception e) {
			maxlevel=true;
			System.out.println(e.getMessage() );;
		}
		try {
			NodeList list = doc.getElementsByTagName(name);	
			return list;
		} catch (Exception e) {
			;
		}
		return null;
	}	
	public boolean getMaxLevel(){
		return maxlevel;
	}
	public void setMaxLevel(){
		maxlevel=false;
	}
	public List<Users> getUsers(){

		List<Users> users = new ArrayList<Users>();
		try{
			String fileinput= System.getProperties().getProperty("user.dir").toString();
			Path path = FileSystems.getDefault().getPath(fileinput,"classes/InGame/User/User.xml");
			xmlfile=new File(path.toString());
	
			NodeList list =openFile("User");
			
		for(int j=0 ; j<list.getLength();j++)
		{
			Element ellem =(Element)list.item(j);
			try{
				users.add(new Users(ellem.getElementsByTagName("name").item(0).getTextContent(), Integer.parseInt(ellem.getElementsByTagName("level").item(0).getTextContent()),Integer.parseInt(ellem.getElementsByTagName("hard").item(0).getTextContent())));
			}catch(Exception e){System.out.println("no user");;}
			}
		xmlfile.exists();
		return users;
		}catch(Exception e){;}
			xmlfile.exists();
		return null;
	}	
	public void setGameMap(int [][]i){
		GameMap = new int [i.length][i.length];
		GameMap=i;
	}		
	private SokoBandMap DomRead(int level , int hard){
		SokoBandMap sokobandMap = new SokoBandMap();
		
		try{
			String fileinput= System.getProperties().getProperty("user.dir").toString();
			Path path = FileSystems.getDefault().getPath(fileinput,"classes/InGame/Map/Map.xml");
			xmlfile = new File(path.toString());

		NodeList list =openFile("Map");
		Element elem = null;
		for(int i=0 ; i<list.getLength() ;i++)
		{ 
			elem = (Element) list.item(i);
			System.out.println("Ellem :"+elem.getAttribute("id").toString() +"hl:"+hard);
			if(elem.getAttribute("id").toString().equals(String.valueOf(hard)) 
				&& Integer.parseInt(elem.getElementsByTagName("level").item(0).getTextContent())==level){
				
				break;
			}
			else elem = null;
		}
		if (elem == null)
			maxlevel=true;
			
		sokobandMap.setHard(Integer.parseInt(elem.getAttribute("id").toString()));
		sokobandMap.setLevel(Integer.parseInt(elem.getElementsByTagName("level").item(0).getTextContent()));
		sokobandMap.setName(elem.getElementsByTagName("name").item(0).getTextContent());
		Node node = elem.getElementsByTagName("rows").item(0);
			elem = (Element) node;
		int max=elem.getElementsByTagName("row").getLength();

		matrixBuilder(max);
		for(int i=0 ; i<max ;i++)
			for(int j=0 ; j<max;j++)
			{
				Element ellem =(Element)elem.getElementsByTagName("row").item(i);
				try{
				GameMap[i][j]=Integer.parseInt(ellem.getElementsByTagName("colum").item(j).getTextContent());
				}catch(Exception e){System.out.println("NOT_GAME_XML");}
			}
	}catch(Exception e){
		
		int [][] test ={{VAL,VAL,VAL,VAL,VAL,VAL},
				{VAL,VAL,CHECPOINT,VAL,VAL,VAL},
				{VAL,VAL,BOX,VAL,VAL,VAL},
				{VAL,VAL,ROAD,VAL,VAL,VAL},
				{VAL,VAL,ROAD,VAL,VAL,VAL},
				{VAL,VAL,PLAYER,VAL,VAL,VAL}};
		matrixBuilder(test.length);
		for(int i=0 ; i<test.length ;i++)
			for(int j=0 ; j<test.length;j++)
				GameMap[i][j]=test[i][j];
	}
		sokobandMap.setMap(GameMap);
		return sokobandMap;
	}	
	public void newUsers(String name ,int level , int hard){
		
		if (name != null) {
			NodeList nodlist = openFile("Users");
			if(nodlist!=null){
			Node node = nodlist.item(0);
			Element ellem = doc.createElement("User");
						node = nodlist.item(0);
			Element namenode = doc.createElement("name");
			Element levelnode = doc.createElement("level");
			Element hardnode = doc.createElement("hard");
			namenode.setTextContent(name);
			levelnode.setTextContent(String.valueOf(level));
			hardnode.setTextContent(String.valueOf(hard));
			ellem.appendChild(namenode);
			ellem.appendChild(levelnode);
			ellem.appendChild(hardnode);
			node.appendChild(ellem);
			
			try {
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				StreamResult result = new StreamResult(new FileOutputStream(xmlfile.toString()));
				DOMSource source = new DOMSource(doc);
				transformer.transform(source, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("SetOK");
		}
			}
	}
	public void modifiUserLevel(int level,int hard ,Element ellem){
		
		
		//Document doc = null;
		ellem.getElementsByTagName("level").item(0).setTextContent(String.valueOf(level));
		ellem.getElementsByTagName("hard").item(0).setTextContent(String.valueOf(hard));
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			StreamResult result = new StreamResult(new FileOutputStream(xmlfile.toString()));
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
		
			
		} catch (Exception e) {
			;
		}
		
	}
	public void XmlWriter(String name, int level , int hard) {
		
		
		String fileinput= System.getProperties().getProperty("user.dir").toString();
			Path path = FileSystems.getDefault().getPath(fileinput,"classes/InGame/User/User.xml");
			xmlfile=new File(path.toString());
	
		NodeList nodlist = openFile("User");
		if(nodlist!=null){
		boolean isBeee=false;
		Element ellem=null;
		for (int i = 0; i < nodlist.getLength(); i++) {
			Node node = nodlist.item(i);
			ellem = (Element) node;
			if(ellem.getElementsByTagName("name").item(0).getTextContent().equals(name))
			{isBeee=true;
			break;}
		}
		
		if(isBeee)
			modifiUserLevel(level ,hard,  ellem);
		else
			newUsers(name, level , hard );}
		xmlfile.exists();
		
	}
	
	
	
}
