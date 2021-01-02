package machine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class FieldTest {
	Field field;

	@Before
	public void setUp() {
		field = new Field(null, 3, 3);
	}
	
	@Test
	public void testSetPieceGetPiece() {
		Piece piece = new Piece(PieceEnum.PAWN, Color.WHITE);
		field.setPiece(piece);
		Assert.assertNotNull(field.piece);
	}
}
