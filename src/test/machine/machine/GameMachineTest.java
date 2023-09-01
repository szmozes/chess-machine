package machine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameMachineTest {
    Table table;
    GameAgainstMachine game;

    //  A - Ally
    //  E - Enemy
    //  O - Test
    //
    //  ___________________
    //  |\|0|1|2|3|4|5|6|7|
    //  |0|_|_|K|_|_|_|_|_|
    //  |1|_|_|_|_|_|_|_|_|
    //  |2|_|_|_|_|_|_|R|_|
    //  |3|_|_|_|_|_|_|_|_|
    //  |4|_|_|_|_|B|_|_|_|
    //  |5|_|_|_|_|_|_|_|_|
    //  |6|_|_|_|_|_|_|_|_|
    //  |7|_|_|_|_|_|_|_|_|

    @BeforeEach
    public void setUp() {
        game = new GameAgainstMachine();
        table = game.table;
        clearTable(table);
    }

    @Test
    public void attemptTest() {
        TestWriter.writeTestTitle("Attempt Test");
        table.placePiece(new Piece(PieceKind.BISHOP, Color.WHITE), 4, 4);
        table.placePiece(new Piece(PieceKind.KNIGHT, Color.BLACK), 0, 2);
        table.placePiece(new Piece(PieceKind.ROOK, Color.BLACK), 2, 6);
        TestWriter.writeTable(table);
        int[] bestMove = new int[4];
        double state = GameAgainstMachine.findBestMove(2, bestMove, table);
        TestWriter.writeString("best move: " + " [" + bestMove[0] + ", " + bestMove[1] + "] -> [" + bestMove[2] + ", " + bestMove[3] + "]");
        TestWriter.writeString("\tvalue: " + state);
        TestWriter.writeString(game.table.whoTurns + " turns");
    }


    //  ___________________
    //  |\|0|1|2|3|4|5|6|7|
    //  |0|_|_|_|_|_|_|_|_|
    //  |1|_|_|_|_|_|_|_|_|
    //  |2|_|K|_|_|_|P|_|_|
    //  |3|_|_|_|_|_|_|R|_|
    //  |4|_|_|_|_|_|_|_|_|
    //  |5|_|_|_|_|Q|_|_|_|
    //  |6|_|_|_|_|_|_|_|_|
    //  |7|_|_|_|_|_|_|_|_|
    @Test
    public void makeMoveTest1() {
        TestWriter.writeTestTitle("Make Move Test");
        table.placePiece(new Piece(PieceKind.QUEEN, Color.WHITE), 5, 4);
        table.placePiece(new Piece(PieceKind.KNIGHT, Color.BLACK), 2, 1);
        table.placePiece(new Piece(PieceKind.ROOK, Color.BLACK), 3, 6);
        table.placePiece(new Piece(PieceKind.PAWN, Color.BLACK), 2, 5);
        TestWriter.writeString("before making a move:");
        TestWriter.writeTable(table);
        game.makeMove(1);
        TestWriter.writeString("after making a move:");
        TestWriter.writeTable(table);
    }

    //  ___________________
    //  |\|0|1|2|3|4|5|6|7|
    //  |0|_|_|_|_|_|_|_|_|
    //  |1|_|_|_|_|_|_|_|_|
    //  |2|_|B|_|_|_|P|_|_|
    //  |3|_|_|_|_|_|_|R|_|
    //  |4|_|_|_|_|_|_|_|_|
    //  |5|_|_|_|_|Q|_|_|_|
    //  |6|_|_|_|_|_|_|_|_|
    //  |7|_|_|_|_|_|_|_|_|
    @Test
    public void makeMoveTest2() {
        TestWriter.writeTestTitle("Make Move Test 2");
        table.placePiece(new Piece(PieceKind.QUEEN, Color.WHITE), 5, 4);
        table.placePiece(new Piece(PieceKind.BISHOP, Color.BLACK), 2, 1);
        table.placePiece(new Piece(PieceKind.ROOK, Color.BLACK), 3, 6);
        table.placePiece(new Piece(PieceKind.PAWN, Color.BLACK), 2, 5);
        TestWriter.writeString("before making a move:");
        TestWriter.writeTable(table);
        game.makeMove(2);
        TestWriter.writeString("after making a move:");
        TestWriter.writeTable(table);
    }

    private void clearTable(Table table) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                table.fields[i][j] = null;
            }
        }
    }
}
