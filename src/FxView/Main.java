package FxView;



import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.jws.soap.SOAPBinding.Use;
import javax.sound.midi.ControllerEventListener;
import javax.swing.text.Position;

import com.sun.javafx.application.LauncherImpl;

import DOMparser.Users;
import FxView.SampleController.Pozition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;


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
			control.controltest=test;
			root = (BorderPane) FXMLLoader.load(getClass().getResource("Sample.fxml"));
			root.setBackground(
					new Background(new BackgroundFill(Color.AQUA, new CornerRadii(20), new Insets(0, 0, 0, 0))));		
			scene = new Scene(root, 800, 800);
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
				
					first=false;
					control.endGame();
					initmenu();
					control.setingame(false);
					menu.setVisible(control.getingameg());
					if(userName.length()>1)
						control.setUser(userName);
					}
		});
		
	}
	public void subMenu(){
		
		ListView<Users> player = new ListView<Users>();
		Button loadGame =control.buttonControl("Load Game");
		try{
		ObservableList<Users> items =FXCollections.observableArrayList (
			    control.getUsers());
		
		player.setItems(items);
		}catch(Exception e) {System.out.println("No saved game");}
		List<Node> nlist = new ArrayList<Node>();
		//buttonlist.removeAll(buttonlist);
		
		nlist.add(player);
		nlist.add(loadGame);
		GridPane grid = new GridPane();
		
		control.visible(root, grid , pozition.CENTER());
		control.gridOption(grid);
		grid.setPadding(new Insets(200));
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
								control.newGame(selecteduser.getLevel() ,root);
								control.setingame(true);
							grid.setVisible(false);
							menu.setVisible(control.getingameg());}
						}
					});
			}
		});
		
	}
	public void nickmenu(){
		
		Text nickname = new Text("NickName:");
		TextField name = new TextField();
		Button ok = control.buttonControl("Ok");
		
	
		GridPane grid = new GridPane();
		
		grid.add(nickname, 0, 0);
		grid.add(name, 1, 0);
		grid.add(ok, 0, 1);
		
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
				
			if(!test)	
			control.newGame(1 ,root);
			else
				control.newGame(-1 ,root);
				
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