package machine;

import org.junit.Before;
import org.junit.Test;

public class TableStateTest {

	TableState ts;
	
	@Before
	public void setUp() {
		ts = new TableState();
	}
	
	@Test
	public void testConstructor() {
		System.out.println("testCtr:");
		ts.print();
	}
	
	@Test
	public void testCopy() {
		System.out.println("testCpy:");
		// create a copy
		TableState copy = new TableState(ts);
		// change the original, and see if the copy changes (it shouldn't change)
		ts.fields[0][0] = null;
		ts.fields[3][3] = new Piece(PieceEnum.PAWN, Color.BLACK);
		ts.whoTurns = Color.BLACK;
		ts.bq = false;
		ts.bk = false;
		ts.wq = false;
		ts.wk = false;
		ts.print();
		copy.print();
	}
}
