package pkg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MapValidator {

	int[][] validmap;
	PlayerIndex player;
	PlayerIndex cel;

	public MapValidator(int level, boolean xmlmap, int[][] map) {
		if (xmlmap)
			cel = engine.inicializeMap(level);
		else
			cel = engine.myMapinicialzed(map);
		player = new PlayerIndex(engine.GameMap);

	}

	Engine engine = new Engine();

	public final class Node {
		PlayerIndex player;
		Node parent;
		String vector;
		LinkedList<String> oplis = new LinkedList<String>();
		int[][] map;

		public Node(PlayerIndex player, Node parent, String vector, LinkedList<String> oplis, int[][] map) {
			super();
			this.player = player;
			this.parent = parent;
			this.vector = vector;
			this.oplis = oplis;
			this.map = map;
		}

		@Override
		public String toString() {
			return "Node [player=" + player + ", parent=" + parent + ", vector=" + vector + "]";
		}

		@Override
		public int hashCode() {

			return player.x * 10 + player.y;

		}

		@Override
		public boolean equals(Object obj) {
			Node other = (Node) obj;

			if (this.vector != null)
				if (this.player.equals(other.player))
					 if(this.map.equals(other.map))
					if (this.vector.equals(other.vector))
					return true;
			if (this.vector == null)
				if (this.player.equals(other.player))
					if (this.map.equals(other.map))
					return true;
				
			return false;
		}

	}

	public LinkedList<String> operator() {
		LinkedList<String> op = new LinkedList<String>();

		op.add("w");
		op.add("a");
		op.add("s");
		op.add("d");

		return op;
	}

	public int[][] lastMap() {

		return engine.GameMap;
	}

	public boolean joe() {
		int i, j;

		if (player == null || cel == null)
			return false;
		boolean isBox = false;
		for (i = 0; i < engine.GameMap.length; i++)
			for (j = 0; j < engine.GameMap.length; j++) {
				if (engine.GameMap[i][j] == 3) {
					isBox = true;
					break;
				}
			}
		if (!isBox)
			return false;

		Node start = new Node(player, null, null, operator(), engine.GameMap);

		if (engine.celboolean(cel))
			return true;

		while (true) {
			//System.out.println("");
			if (!cel.equals(player))
				engine.GameMap[cel.x][cel.y] = 4;
			// 3
			if (start.oplis.isEmpty()) {
				if (start.parent != null) {

					start = start.parent;
					engine.GameMap = start.map;
				} else
					return false;
			} else {
				// 4
				String vektor = start.oplis.getFirst();
				start.oplis.removeFirst();
				PlayerIndex player1 = (engine.playerMove(start.player, vektor.charAt(0)));
				if (start.player.equals(player1))
					continue;
				boolean isbe = false;
				
			/*	for (i = 0; i < engine.GameMap.length; i++) {
					for (j = 0; j < engine.GameMap.length; j++) {
						if (start.map[i][j] == 3
								&& ((start.map[i][j + 1] == 0 && start.map[i + 1][j] == 0)
										|| (start.map[i][j - 1] == 0 && start.map[i + 1][j] == 0)
										|| (start.map[i][j + 1] == 0 && start.map[i - 1][j] == 0)
										|| (start.map[i][j - 1] == 0 && start.map[i - 1][j] == 0))) {
							//start.map;
							isbe=true;
							
						}
					}
				}*/
				if(isbe)continue;
				
				Node start1 = new Node(player1, start, vektor, operator(), lastMap());
				isbe = false;

				for (i = 0; i < engine.GameMap.length; i++) {
					for (j = 0; j < engine.GameMap.length; j++) {
						if (start1.map[i][j] == 2 && !(i == start1.player.x && j == start1.player.y))
							start1.map[i][j] = 1;
							//System.out.print(start1.map[i][j]);
					}
						//System.out.println("");
				}

				for (Node cs = start; cs != null; cs = cs.parent) {
					if (cs.equals(start1)) {
						isbe = true;
						engine.GameMap = start.map;
						break;
					}
				}

				if (!isbe) {

					start = start1;

					for (i = 0; i < engine.GameMap.length; i++) {
						for (j = 0; j < engine.GameMap.length; j++) {
							if (engine.GameMap[i][j] == 2 && !(i == start.player.x && j == start.player.y))
								engine.GameMap[i][j] = 1;
							//System.out.print(engine.GameMap[i][j] + " ");
						}
						//System.out.println("");
					}
					//for(int k = 0 ; k<100000000;k++)
					//	for(int h = 0 ; h<100;h++){h+=1;h-=1;}
						
					//engine.GameMap = start.map;
					// 2
					try{
					if (engine.celboolean(cel))
						return true;}
					catch(Exception e){e.getMessage();
						
					}
				}
			}

		}
	}
}
