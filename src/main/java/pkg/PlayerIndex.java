package pkg;

public class PlayerIndex{
	  public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	int x ;
	 int y ;
		public PlayerIndex(int x ,int y){
		this.x=x;
		this.y=y;
	}
		public PlayerIndex(int [][]GameMap){
			for(int i=0 ; i<GameMap.length;i++){
				for(int j=0 ; j<GameMap.length;j++)
					if(GameMap[i][j]==2)
					{
						x=i;
						y=j;
					}
				}
		}
		@Override
		public int hashCode() {
			return 10*x+y;
		}
		@Override
		public boolean equals(Object obj) {

			PlayerIndex other = (PlayerIndex) obj;
			
			if (x == other.x)
			if (y == other.y)
				return true;
			return false;
		}
		@Override
		public String toString() {
			return "PlayerIndex [x=" + x + ", y=" + y + "]";
		}
		
		
	}