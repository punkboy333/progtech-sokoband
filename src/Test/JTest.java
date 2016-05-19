package Test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import DOMparser.Users;
import pkg.*;

public class JTest {

	DomComunicator dom ;
	Engine engine = new Engine();
	PlayerIndex player;
	PlayerIndex cel;
	@Before
	public void inicialized(){
		cel=engine.inicializeMap(-1);
		player= new PlayerIndex(engine.gameMap);
		}
	
	
	@Test
	public void test1() {
		assertEquals(false, engine.alkalmazasiElofeltetel(1, player))
		;
	}
	@Test
	public void test2() {
		assertEquals(true, engine.alkalmazasiElofeltetel(2, player))
		;
	}
	@Test
	public void test3() {
		assertEquals(false, engine.alkalmazasiElofeltetel(3, player))
		;
	}
	@Test
	public void test4() {
		assertEquals(false, engine.alkalmazasiElofeltetel(4, player))
		;
	}

	@Test
	public void test() {
		
		assertEquals(false,engine.celboolean(cel));
	}
	@Test
	public void leveltest() {
		 engine.setLevel(-1);
		assertEquals(-1,engine.getLevel());
	}
	@Test
	public void boxtest() {
		 
		assertEquals(true,engine.box(2, new PlayerIndex(3, 2)));
	}
	@Test
	public void playermoveleft() {
		 PlayerIndex playernew = engine.playerMove(player, 'a');
		 	
		 assertEquals(player, playernew);
	
	}
	@Test
	public void playermovetop() {
		 PlayerIndex playernew = engine.playerMove(player, 'w');
		 	
		 assertEquals(new PlayerIndex(4, 2), playernew);
	
	}
	
	@Test
	public void mapValidator(){
		            //0 1 2 3 4 5 6 ///// 
		int[][] i = {{0,0,0,0,0,0},// 0 
					 {0,1,1,4,1,0},// 1
					 {0,1,1,0,1,0},// 2
					 {0,1,1,0,0,0},// 3
					 {0,0,1,3,1,0},// 4
					 {0,0,1,1,2,0} // 5
					 };//6
		
		MapValidator mapvalidator = new MapValidator(-1 , false ,i);
		
		assertEquals(true, mapvalidator.joe());
		
	}

}
