package FxView;

import java.util.ArrayList;
import java.util.List;

import DOMparser.Users;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import pkg.Engine;
import pkg.MapValidator;
import pkg.PlayerIndex;

public class SampleController {
	
	private PlayerIndex playerpoz;
	private PlayerIndex cel;
	
	private Image img0 = new Image("http://blizzardskies.com/bz/bg/graybg53.gif");
	private Image img1 = new Image("http://3.bp.blogspot.com/-05Z-np4NrmY/T-t2LKi4m8I/AAAAAAAACC4/E4Yhz9QsyIA/s1600/seamless_green_background_for_web_sites.jpg");
	private Image img2 = new Image("http://i34.tinypic.com/2lcwy0o.png");
	private Image img3 = new Image("http://corrupteddevelopment.com/wp-content/uploads/2012/04/cardboard-box-icon-256.jpg");
	private Image img4 = new Image("http://static1.grsites.com/archive/textures/red/red185.jpg");
	private List<ImageView> listimgv = new ArrayList<ImageView>();
	
	private int [][] gameMap = null;
	

	Engine engine = new Engine();
	
		public SampleController() {
			// TODO Auto-generated constructor stub
		}
	
	private boolean ingame=false;
	
	public void celIsPlayer(){
		
		if(!cel.equals(playerpoz) && gameMap[cel.getX()][cel.getY()]!=3)
			gameMap[cel.getX()][cel.getY()]=4;
	}
	
	
	public void setingame(boolean ingame){
		this.ingame=ingame;
	}
	public boolean getingameg(){
		return this.ingame;
	}
	
	public void gridOption(GridPane grid){
		grid.autosize();
		 grid.setAlignment(Pos.TOP_CENTER);
		 grid.setPadding(new Insets(100,100,100,100));
		
	}
	public Button buttonControl(String name){
		Button button = new Button();
		button.setText(name);
		button.setPadding(new Insets(10,10,10,10));
		button.setVisible(true);
		return button;
	}
	
	public void gridControl(GridPane grid , List<Node> buttonlist , int oszlop){
		int k = 0;
		for(int i = 0 ; i < buttonlist.size()/oszlop ;i++)
			for (int j = 0; j<oszlop ; j++)
				{grid.add(buttonlist.get(k), j, i);
				k++;
				}
	}
	
	
	
	GridPane grid;
	public void newGame(int level , Node root ){
		engine.setMaxlevel();
		cel = engine.inicializeMap(level);
		playerpoz = new PlayerIndex(engine.gameMap);
		gameMap=engine.gameMap;
		grid = new GridPane();
		reloadgrid(grid, gameMap, root);
		engine.setLevel(level);
		
		
		
	}
	public void endGame(){
		grid.setVisible(false);
	}
	
	public boolean isBeCho(){
		MapValidator mapvalid = new MapValidator(0, false, gameMap);
		return mapvalid.joe();
	}
	
	
	void runReloadGrid(Node root){
		reloadgrid(grid, gameMap, root);
		
		/*if(isBeCho())
		{}			{grid.setVisible(false);
		setingame(false);
	Text tx = new Text("You Lose!!!");
	visible(root, tx, "CENTER");}*/
		
		if(engine.celboolean(cel) && !controltest)
			{newGame(engine.getLevel()+1, root);
			if(engine.getMaxlevel() && !controltest)
			{grid.setVisible(false);
			setingame(false);
		Text tx = new Text("You Win!!!");
		visible(root, tx, "CENTER");}
			}
			else
				if(engine.celboolean(cel) && controltest)
				newGame(engine.getLevel(), root);
	
	}
	
	public void setUser(String name) {
		engine.setUser(name, engine.getLevel());
		}
	
		public void reloadgrid(GridPane grid, int[][] matrix , Node root) {
			int k = 0;

			listimgv = new ArrayList<ImageView>();

			for (int i = 0; i < matrix.length; i++)
				for (int j = 0; j < matrix.length; j++) {
					if (matrix[i][j] == 0)
						listimgv.add(new ImageView(img0));
					else if (matrix[i][j] == 1)

						listimgv.add(new ImageView(img1));
					else if (matrix[i][j] == 2)
						listimgv.add(new ImageView(img2));
					else if (matrix[i][j] == 3)
						listimgv.add(new ImageView(img3));
					else
						listimgv.add(new ImageView(img4));
				}
			for (ImageView js : listimgv) {
				imgeControl(js);
			}

			k = 0;
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix.length; j++) {

					grid.add(listimgv.get(k), j, i);
					// grid.addRow(i, new Text(" "));
					// grid.addColumn(j, new Text(" "));

					k++;

				}
			}
			gridOption(grid);
			visible(root, grid , "CENTER");

		}
	boolean test = false;
	public void setTest(boolean bool){
		test=bool;
	}

	
		public void keyControl(KeyEvent ke){
			if (ke.getCode().equals(KeyCode.W) && getingameg()) {

				playerpoz = engine.playerMove(playerpoz, 'w');


			}
			if (ke.getCode().equals(KeyCode.A) && getingameg()) {

				playerpoz = engine.playerMove(playerpoz, 'a');
				

			}
			if (ke.getCode().equals(KeyCode.S) && getingameg()) {

				playerpoz = engine.playerMove(playerpoz, 's');
				
				

			}
			if (ke.getCode().equals(KeyCode.D) && getingameg()) {

				playerpoz = engine.playerMove(playerpoz, 'd');

			}
		}
	
	static final class Pozition{

		public final String [] poz ={"LEFT" , "RIGHT" , "CENTER" , "BUTTON" , "TOP"};
		
		public String LEFT(){
			return poz[0];
		}
		public String RIGHT(){
			return poz[1];
		}
		public String CENTER(){
			return poz[2];
		}
		public String BOTTON(){
			return poz[3];
		}
		public String TOP(){
			return poz[4];
		}
	}
	
	public List<Users> getUsers(){
		return engine.getUsers();
	}
	
	public boolean controltest;
	
public void visible(Object root , Object children ,  String poz ) {
		Pozition pozition = new Pozition();
		BorderPane rootview = (BorderPane) root;
	if(poz==pozition.CENTER())	
	rootview.setCenter((Node)children);
	if(poz==pozition.LEFT())	
		rootview.setLeft((Node)children);
	if(poz==pozition.RIGHT())	
		rootview.setRight((Node)children);
		
	if(poz==pozition.TOP())	
		rootview.setTop((Node)children);
	if(poz==pozition.BOTTON())	
		rootview.setBottom((Node)children);
		
	}
	
	public void imgeControl(ImageView image){
		image.setFitWidth(50);
		image.setPreserveRatio(true);
		image.autosize();
	}
	
}
