package machine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TableTest {
    Table table;

    @Before
    public void setUp() {
        table = new Table();
    }

    @Test
    public void testGetPieceColor() {
        Piece test = new Piece(PieceKind.PAWN, Color.WHITE);
        table.placePiece(test, 1, 1);
        Assert.assertEquals(Color.WHITE, table.getPieceColor(1, 1));
    }
}
