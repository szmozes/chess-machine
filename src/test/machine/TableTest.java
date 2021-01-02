package machine;

import org.junit.Assert;
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
        Piece test = new Piece(PieceEnum.PAWN, Color.WHITE);
        table.placePiece(test, 1, 1);
        Assert.assertEquals(Color.WHITE, table.getPieceColor(1, 1));
    }
}
