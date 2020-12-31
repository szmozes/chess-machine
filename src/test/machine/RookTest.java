package machine;


import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class RookTest {
	
	Table table;
	Rook rook;
	Piece p1;
	Piece p2;
	Piece p3;
	
	/*
	 A - Ally
	 E - Enemy
	 O - Test
	 
	   ___________________
	   |\|0|1|2|3|4|5|6|7|
	   |0|_|_|_|_|_|_|_|_|
	   |1|_|_|_|_|_|_|_|_|
	   |2|_|_|_|_|_|_|_|_|
	   |3|_|_|_|E|_|_|_|_|
	   |4|_|_|_|_|_|_|_|_|
	   |5|_|_|_|O|_|_|_|A|
	   |6|_|_|_|A|_|_|_|_|
	   |7|_|_|_|_|_|_|_|_|
	   
	 */
	@Before
	public void setUp() {
		table = new Table(8, 8);
		rook = new Rook(null, Color.WHITE);
		p1 = new Piece(new Field(null, 0, 0), Color.WHITE);
		p2 = new Piece(new Field(null, 0, 0), Color.WHITE);
		p3 = new Piece(new Field(null, 0, 0), Color.BLACK);
		table.placePiece(rook, 5, 3);
		table.placePiece(p1, 5, 7);
		table.placePiece(p2, 6, 3);
		table.placePiece(p3, 3, 3);
	}

	@Test
	public void opportunitiesTest() {
		ArrayList<int[]> opportunities = rook.getOpportunities();
		for(int i = 0; i < opportunities.size(); i++) {
			for(int j = 0; j < opportunities.get(i).length; j++) {
				System.out.print(opportunities.get(i)[j] + " ");
			}
			System.out.println();
		}
	}

}
