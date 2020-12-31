package machine;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class KnightTest {

	Knight knight;
	Table table;
	
	/*
	 A - Ally
	 E - Enemy
	 O - Test
	 
	   ___________________
	   |\|0|1|2|3|4|5|6|7|
	   |0|_|_|_|_|_|_|A|_|
	   |1|_|_|_|_|O|_|_|_|
	   |2|_|_|E|_|_|_|_|_|
	   |3|_|_|_|A|_|_|_|_|
	   |4|_|_|_|_|_|_|_|_|
	   |5|_|_|_|_|_|_|_|_|
	   |6|_|_|_|_|_|_|_|_|
	   |7|_|_|_|_|_|_|_|_|
	   
	 */
	@Before
	public void setUp() {
		table = new Table(8, 8);
		knight = new Knight(null, Color.WHITE);
		table.placePiece(knight, 1, 4);
		table.placePiece(new Piece(new Field(null, 0, 0), Color.WHITE), 0, 6);
		table.placePiece(new Piece(new Field(null, 0, 0), Color.WHITE), 3, 3);
		table.placePiece(new Piece(new Field(null, 0, 0), Color.BLACK), 2, 2);
	}

	@Test
	public void opportunitiesTest() {
		ArrayList<int[]> opportunities = knight.opportunities();
		for(int i = 0; i < opportunities.size(); i++) {
			for(int j = 0; j < opportunities.get(i).length; j++) {
				System.out.print(opportunities.get(i)[j] + " ");
			}
			System.out.println();
		}
	}

}
