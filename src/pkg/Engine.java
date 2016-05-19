package pkg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import DOMparser.Users;;

public class Engine {

	public int[][] gameMap;
	DOMComunicatorImp mapbuilder = new DOMComunicatorImp();

	
	
	public Engine(DomComunicator mapbuilder) {
		super();
		this.mapbuilder =(DOMComunicatorImp) mapbuilder;
	}
	
	public Engine() {
		super();
	}



	public List<Users> getUsers() {
		return mapbuilder.getUsers();
	}

	public void setUser(String name , int level) {
		mapbuilder.addUser(name, level);
	}
	
	public PlayerIndex myMapinicialzed(int[][] map) {
		gameMap = map;
		int x = 0, y = 0;
		for (x = 0; x < gameMap.length; x++)
			for (y = 0; y < gameMap.length; y++)
				if (gameMap[x][y] == 4)
					return new PlayerIndex(x, y);

		return null;

	}

	public PlayerIndex inicializeMap(int level) {
		gameMap = mapbuilder.getMap(level);
		int x = 0, y = 0;
		for (x = 0; x < gameMap.length; x++)
			for (y = 0; y < gameMap.length; y++)
				if (gameMap[x][y] == 4)

					return new PlayerIndex(x, y);

		return null;
	}

	private static final int ROAD = 1;
	private static final int PLAYER = 2;

	public char setkey() {
		return input();
	}

	public PlayerIndex playerMove(PlayerIndex playerpoz, char key) {
		final int left = 0, right = 1, button = 3, top = 2;

		int i = playerpoz.x;
		int j = playerpoz.y;

		if (key == 'w') {
			if (alkalmazasiElofeltetel(top, playerpoz)) {
				box(top, playerpoz);
				gameMap[i][j] = ROAD;
				gameMap[i - 1][j] = PLAYER;

				return new PlayerIndex(i - 1, j);
			}
		}
		if (key == 'a') {
			if (alkalmazasiElofeltetel(left, playerpoz)) {
				box(left, playerpoz);
				gameMap[i][j] = ROAD;
				gameMap[i][j - 1] = PLAYER;
				return new PlayerIndex(i, j - 1);
			}
		}

		if (key == 's') {
			if (alkalmazasiElofeltetel(button, playerpoz)) {
				box(button, playerpoz);
				gameMap[i][j] = ROAD;
				gameMap[i + 1][j] = PLAYER;
				return new PlayerIndex(i + 1, j);
			}
		}
		if (key == 'd') {
			if (alkalmazasiElofeltetel(right, playerpoz)) {
				box(right, playerpoz);
				gameMap[i][j] = ROAD;
				gameMap[i][j + 1] = PLAYER;
				return new PlayerIndex(i, j + 1);
			}
		}
		return new PlayerIndex(i, j);
	}

	private int level = 1;

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public boolean box(int vektor, PlayerIndex playerpoz) {
		int x = playerpoz.x;
		int y = playerpoz.y;
		if (vektor == 2)
			if (gameMap[x - 1][y] == 3) {
				gameMap[x - 2][y] = 3;
				return true;
			}
		if (vektor == 3)
			if (gameMap[x + 1][y] == 3) {
				gameMap[x + 2][y] = 3;
				return true;
			}
		if (vektor == 0)
			if (gameMap[x][y - 1] == 3) {
				gameMap[x][y - 2] = 3;
				return true;
			}
		if (vektor == 1)
			if (gameMap[x][y + 1] == 3) {
				gameMap[x][y + 2] = 3;
				return true;
			}

		return false;
	}

	public boolean alkalmazasiElofeltetel(int vektor, PlayerIndex playerpoz) {
		int x = playerpoz.x;
		int y = playerpoz.y;

		if (vektor == 2)
			if (0 <= x - 1)
				if (gameMap[x - 1][y] == 3)
					if (0 <= x - 2)
						return gameMap[x - 2][y] != 0;
					else
						return false;
				else

					return gameMap[x - 1][y] != 0;

		if (vektor == 3)
			if (gameMap.length > x + 1)
				if (gameMap[x + 1][y] == 3)
					if (gameMap.length > x + 2)
						return gameMap[x + 2][y] != 0;
					else
						return false;
				else
					return gameMap[x + 1][y] != 0;
		if (vektor == 0)
			if (0 <= y - 1)
				if (gameMap[x][y - 1] == 3)
					if (0 <= y - 2)
						return gameMap[x][y - 2] != 0;
					else
						return false;
				else
					return gameMap[x][y - 1] != 0;
		if (vektor == 1)
			if (gameMap.length > y + 1)
				if (gameMap[x][y + 1] == 3)
					if (gameMap.length > y + 2)
						return gameMap[x][y + 2] != 0;
					else
						return false;
				else
					return gameMap[x][y + 1] != 0;

		return false;
	}

	public boolean celboolean(PlayerIndex cel) {

		int x = 0, y = 0;
		PlayerIndex akt = null;
		for (x = 0; x < gameMap.length; x++)
			for (y = 0; y < gameMap.length; y++)
				if (gameMap[x][y] == 3) {
					akt = new PlayerIndex(x, y);
					break;
				}

		return cel.equals(akt);

	}

	public boolean getMaxlevel() {
		return mapbuilder.getMaxlevel();
	}
	public void setMaxlevel() {
		mapbuilder.setMaxlevel();
	}

	public static void main(String[] args) {

		Engine enginerun = new Engine();
		PlayerIndex cel = enginerun.inicializeMap(1);
		PlayerIndex playerindex = new PlayerIndex(enginerun.gameMap);
		while (true) {

			for (int i = 0; i < enginerun.gameMap.length; i++) {
				System.out.println("");
				for (int j = 0; j < enginerun.gameMap.length; j++)
					System.out.print(enginerun.gameMap[i][j]);
			}
			playerindex = enginerun.playerMove(playerindex, enginerun.setkey());

			if (enginerun.celboolean(cel))
				break;
			if (!playerindex.equals(cel))
				enginerun.gameMap[cel.x][cel.y] = 4;
		}

		System.out.println("RaCoon Gratulation :D");

	}
	
	


	public char input() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

			String s = input.readLine();
			System.out.println("My chose: " + s);

			return s.charAt(0);
		} catch (Exception e) {
			;
		}
		return 0;
	}
}
