package machine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class FieldTest {
	Field field;

	@Before
	public void setUp() {
		field = new Field();
	}
	
	@Test
	public void testSetPieceGetPiece() {
		Piece piece = new Piece(PieceKind.PAWN, Color.WHITE);
		field.setPiece(piece);
		Assert.assertNotNull(field.piece);
	}
}
