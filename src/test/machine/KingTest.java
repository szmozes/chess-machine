package machine;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class KingTest {
	
	King king;
	Table table;
	
	@Before
	public void setUp() {
		table = new Table(8, 8);
		king = new King(null, Color.WHITE);
		table.placePiece(king, 0, 1);
		table.placePiece(new Piece(new Field(null, 0, 0), Color.WHITE), 1, 2);
		table.placePiece(new Piece(new Field(null, 0, 0), Color.WHITE), 2, 2);
		table.placePiece(new Piece(new Field(null, 0, 0), Color.WHITE), 2, 1);
	}

	@Test
	public void opportunitiesTest() {
		ArrayList<int[]> opportunities = king.getOpportunities();
		for(int i = 0; i < opportunities.size(); i++) {
			for(int j = 0; j < opportunities.get(i).length; j++) {
				System.out.print(opportunities.get(i)[j] + " ");
			}
			System.out.println();
		}
	}

}
