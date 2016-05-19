package pkg;

import java.util.LinkedList;
import java.util.List;


public class MapValidator {

	int[][] validmap;
	PlayerIndex player;
	PlayerIndex cel;

	public MapValidator(int level,int hard, boolean xmlmap, int[][] map) {
		if (xmlmap)
			cel = engine.inicializeMap(level , hard);
		else
			cel = engine.myMapinicialzed(map);
		player = new PlayerIndex(engine.gameMap);

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

		return engine.gameMap;
	}

	public List<String> joe() {
		int i, j;

		if (player == null || cel == null)
			return null;
		boolean isBox = false;
		for (i = 0; i < engine.gameMap.length; i++)
			for (j = 0; j < engine.gameMap.length; j++) {
				if (engine.gameMap[i][j] == 3) {
					isBox = true;
					break;
				}
			}
		if (!isBox)
			return null;

		Node start = new Node(player, null, null, operator(), engine.gameMap);

		if (engine.celboolean(cel))
			return null;

		while (true) {
			//System.out.println("");
			if (!cel.equals(player))
				engine.gameMap[cel.x][cel.y] = 4;
			// 3
			if (start.oplis.isEmpty()) {
				if (start.parent != null) {

					start = start.parent;
					engine.gameMap = start.map;
				} else
					return null;
			} else {
				// 4
				String vektor = start.oplis.getFirst();
				start.oplis.removeFirst();
				PlayerIndex player1 = (engine.playerMove(start.player, vektor.charAt(0)));

				boolean isbe = false;
				
				
				Node start1 = new Node(player1, start, vektor, operator(), lastMap());
				isbe = false;

				for (i = 0; i < engine.gameMap.length; i++) {
					for (j = 0; j < engine.gameMap.length; j++) {
						if (start1.map[i][j] == 2 && !(i == start1.player.x && j == start1.player.y))
							start1.map[i][j] = 1;
							//System.out.print(start1.map[i][j]);
					}
						//System.out.println("");
				}

				for (Node cs = start; cs != null; cs = cs.parent) {
					if (cs.player.equals(start1.player)) {
						isbe = true;
						engine.gameMap = start.map;
						break;
					}
				}

				if (!isbe) {

					start = start1;

					for (i = 0; i < engine.gameMap.length; i++) {
						for (j = 0; j < engine.gameMap.length; j++) {
							if (engine.gameMap[i][j] == 2 && !(i == start.player.x && j == start.player.y))
								engine.gameMap[i][j] = 1;
						}
					}
					// 2
					
					try{
					if (engine.celboolean(cel))
						{
						LinkedList<String> op = new LinkedList<String>();
						for(Node cs = start; cs!=null ; cs=cs.parent)
						op.addFirst(cs.vector);
						return op;
						}
					}catch(Exception e){e.getMessage();}
						
					
				}
			}

		}
	}
}
