package machine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TableTest {

	Table table;
	
	@Before
	public void setUp() {
		table = new Table(8, 8);
	}
	
	@Test
	public void testGetPieceColor() {
		Piece test = new Piece(table.fields[1][1], Color.WHITE);
		table.placePiece(test, 1, 1);
		System.out.println(table.getPieceColor(1, 1));
	}

}
