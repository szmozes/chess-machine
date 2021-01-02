package performancebased;

import machine.Color;
import machine.PieceEnum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TableStateTest {
    TableState tableState;

    @Before
    public void setUp() {
        tableState = new TableState();
    }

    @Test
    public void testConstructor() {
        assertEquals(PieceEnum.ROOK, tableState.fields[0][0].getKind());
        assertNull(tableState.fields[3][3]);
        assertEquals(Color.WHITE, tableState.whoTurns);
        assertArrayEquals(new boolean[]{true, true, true, true}, new boolean[]{tableState.bk, tableState.bq, tableState.wk, tableState.wq});
    }

    @Test
    public void testCopy() {

        // arrange
        TableState copy = new TableState(tableState);

        // act
        tableState.fields[0][0] = null;
        tableState.fields[3][3] = new Piece(PieceEnum.PAWN, Color.BLACK);
        tableState.whoTurns = Color.BLACK;
        tableState.bq = false;
        tableState.bk = false;
        tableState.wq = false;
        tableState.wk = false;
        System.out.println("Halo");

        // see if the copy changed (it shouldn't change)
        assertEquals(PieceEnum.ROOK, copy.fields[0][0].getKind());
        assertNull(copy.fields[3][3]);
        assertEquals(Color.WHITE, copy.whoTurns);
        assertArrayEquals(new boolean[]{true, true, true, true}, new boolean[]{copy.bk, copy.bq, copy.wk, copy.wq});
    }
}
