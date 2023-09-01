package machine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TableTest {
    Table table;

    @BeforeEach
    public void setUp() {
        table = new Table();
    }

    @Test
    public void testGetPieceColor() {
        Piece test = new Piece(PieceKind.PAWN, Color.WHITE);
        table.placePiece(test, 1, 1);
        Assertions.assertEquals(Color.WHITE, table.getPieceColor(1, 1));
    }
}
