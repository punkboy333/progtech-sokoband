package FxView;



import java.util.ArrayList;
import java.util.List;

import DOMparser.Users;
import FxView.SampleController.Pozition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
	
	SampleController control = new SampleController();
	BorderPane root;
	String userName;
	public boolean isTest=false;
	
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Sokoband THE GAME");
		Scene scene = null;

		try {
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(600);
			primaryStage.setMaxHeight(600);
			primaryStage.setMaxWidth(600);
			control.controltest=test;
			root = (BorderPane) FXMLLoader.load(getClass().getResource("Sample.fxml"));
			root.setBackground(
					new Background(new BackgroundFill(Color.AQUA, new CornerRadii(20), new Insets(0, 0, 0, 0))));		
			scene = new Scene(root, 600, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
					if(control.getingameg()){
					control.keyControl(ke);
					control.celIsPlayer();
					control.runReloadGrid(root);
					}
					
					
			}
			
		});
		
		initmenu();
		back();
		buttonInicialized(primaryStage);
		
	}
	Button menu ;
	public void back(){
		menu=
		control.buttonControl("Back");
		
		control.visible(root, menu, pozition.TOP());
		menu.setVisible(false);
		
	}
	
	boolean first=true;
	List<Node> buttonlist = new ArrayList<Node>();
	Pozition pozition = new Pozition();
	public void initmenu(){
		
		Button newGame =control.buttonControl("New Game");
		Button loadGame =control.buttonControl("Load Game");
		Button exitGame =control.buttonControl("Exit Game");
		GridPane grid = new GridPane();
		if(first)
		{
		buttonlist.add(newGame);
		buttonlist.add(loadGame);
		buttonlist.add(exitGame);
		}
		control.visible(root, grid , pozition.CENTER());
		control.gridOption(grid);
		grid.setPadding(new Insets(200));
		grid.setHgap(20);
		grid.setVgap(20);
		control.gridControl(grid, buttonlist, 1);
	
		
	}
	public void buttonInicialized(Stage primaryStage){
		if(buttonlist.size()>2){
		buttonlist.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseclic) {
				if(mouseclic.isPrimaryButtonDown())
					root.getCenter().setVisible(false);
					nickmenu();
			}
		});
		
		buttonlist.get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseclic) {
				if(mouseclic.isPrimaryButtonDown())
					control.setingame(true);
					root.getCenter().setVisible(false);
					subMenu();
			}
		});

		
		buttonlist.get(2).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseclic) {
				if(mouseclic.isPrimaryButtonDown())
					primaryStage.close();
					System.exit(0);
			}
		});
		}
		menu.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseclic) {
					
						//passedmap();
					
						control.setingame(false);	
						first=false;
						control.endGame();
						
						initmenu();
						menu.setVisible(control.getingameg());
						if(userName.length()>1)
							control.setUser(userName);
						
					}
		});
		
	}
	public void passedmap(){
		List<String>op =control.isBeCho();
		if(op!=null){
		op.remove(0);
		op.remove(op.size()-1);
		root.getCenter().setVisible(false);
		for (String string : op) {
			control.movControl(string);
			control.celIsPlayer();
			control.runReloadGrid(root);
			
			for(int i = 0 ; i<100; i++)
				for(int j = 0 ; j<1000 ; j++)
					System.out.println(string);;
				
		}	
		}
		
	}
	
	
	public void subMenu(){
		
		ListView<Users> player = new ListView<Users>();
		Button loadGame =control.buttonControl("Load Game");
		try{
			
		ObservableList<Users> items =FXCollections.observableArrayList (
			    control.getUsers());
		player.autosize();
		player.setItems(items);
		}catch(Exception e) {System.out.println("No saved game");}
		List<Node> nlist = new ArrayList<Node>();
		
		
		nlist.add(player);
		nlist.add(loadGame);
		GridPane grid = new GridPane();
		
		control.visible(root, grid , pozition.CENTER());
		control.gridOption(grid);
		grid.setPadding(new Insets(100,100,100,100));
		grid.setHgap(20);
		grid.setVgap(20);
		control.gridControl(grid, nlist, 1);
		nlist.removeAll(nlist);
		
		
		player.setOnMouseClicked(new EventHandler<MouseEvent>()  {
			Users selecteduser;
				
			@Override
			public void handle(MouseEvent mouseclic) {
				
					selecteduser= player.getSelectionModel().getSelectedItem();
					loadGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
								if(selecteduser!=null){
									userName=selecteduser.getName();
									control.setHardLevel(selecteduser.getHard());
									control.newGame(selecteduser.getLevel(),selecteduser.getHard(),root);
								control.setingame(true);
							grid.setVisible(false);
							menu.setVisible(control.getingameg());}
						}
					});
			}
		});
		
	}
	int hardlevel;
	public void nickmenu(){
		
		Text nickname = new Text("NickName:");
		TextField name = new TextField();
		Button ok = control.buttonControl("Ok");
		TextField hard = new TextField();
		Text Difficulty = new Text("Difficulty:");
		
		GridPane grid = new GridPane();
		
		grid.add(nickname, 0, 0);
		grid.add(name, 1, 0);
		grid.add(Difficulty, 0, 1);
		grid.add(hard, 1, 1);
		grid.add(ok, 0, 2);
		control.visible(root, grid , pozition.CENTER());
		control.gridOption(grid);
		grid.setPadding(new Insets(200));
		grid.setHgap(20);
		grid.setVgap(20);
		
		ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				userName=name.getText();
				if(userName.length()>1)
				control.setUser(userName);
				System.out.println(hard.getText());
				try{Integer.parseInt(hard.getText());
					hardlevel=Integer.parseInt(hard.getText());
					if(hardlevel>3)hardlevel=3;
					if(hardlevel<1)hardlevel=1;
					}catch (Exception e) {
						hardlevel=1;
					}
				control.setHardLevel(hardlevel);
			if(!test)	
			control.newGame(1 ,hardlevel,root);
			else
				control.newGame(-1,hardlevel ,root);
				
				control.setingame(true);
				grid.setVisible(false);
				menu.setVisible(control.getingameg());
			}
		});
		
	}
KeyEvent ke;
	
	public static boolean test=false;

	public static void main(String[] args) {
	
		if(args.length>0)
		if(args[0].equals("test"))
			test=true;
		launch(args);
	}
}
