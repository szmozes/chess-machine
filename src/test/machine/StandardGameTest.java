package machine;

import org.junit.Before;
import org.junit.Test;

public class StandardGameTest {
    Table table;
    StandardGame game;

//	 A - Ally
//	 E - Enemy
//	 O - Test

//	   ___________________
//	   |\|0|1|2|3|4|5|6|7|
//	   |0|_|_|K|_|_|_|_|_|
//	   |1|_|_|_|_|_|_|_|_|
//	   |2|_|_|_|_|_|_|R|_|
//	   |3|_|_|_|_|_|_|_|_|
//	   |4|_|_|_|_|B|_|_|_|
//	   |5|_|_|_|_|_|_|_|_|
//	   |6|_|_|_|_|_|_|_|_|
//	   |7|_|_|_|_|_|_|_|_|

    @Before
    public void setUp() {
        table = new Table(8, 8);
        game = new StandardGame();
    }

    @Test
    public void attemptTest() {
        table.placePiece(new Bishop(null, Color.WHITE), 4, 4);
        table.placePiece(new Knight(null, Color.BLACK), 0, 2);
        table.placePiece(new Rook(null, Color.BLACK), 2, 6);
        table.write();
        int[] bestMove = new int[4];
        double state = game.attempt(2, bestMove);
        System.out.println("best move : " + state + " " + bestMove[0] + " " + bestMove[1] + " " + bestMove[2] + " " + bestMove[3] + "\n");
        System.out.println(game.whoTurns + " turns");
    }
	
/*
//	___________________
//  |\|0|1|2|3|4|5|6|7|
//	|0|_|_|_|_|_|_|_|_|
//	|1|_|_|_|_|_|_|_|_|
//	|2|_|K|_|_|_|P|_|_|
//	|3|_|_|_|_|_|_|R|_|
//	|4|_|_|_|_|_|_|_|_|
//	|5|_|_|_|_|Q|_|_|_|
//	|6|_|_|_|_|_|_|_|_|
//	|7|_|_|_|_|_|_|_|_|
	@Test
	public void makeMoveTest1() {
		table.placePiece(new Queen(null, Color.WHITE), 5, 4);
		table.placePiece(new Knight(null, Color.BLACK), 2, 1);
		table.placePiece(new Rook(null, Color.BLACK), 3, 6);
		table.placePiece(new Pawn(null, Color.BLACK), 2, 5);
		table.write();
		game.makeMove(1);
		table.write();
	}
	
//	___________________
//  |\|0|1|2|3|4|5|6|7|
//	|0|_|_|_|_|_|_|_|_|
//	|1|_|_|_|_|_|_|_|_|
//	|2|_|B|_|_|_|P|_|_|
//	|3|_|_|_|_|_|_|R|_|
//	|4|_|_|_|_|_|_|_|_|
//	|5|_|_|_|_|Q|_|_|_|
//	|6|_|_|_|_|_|_|_|_|
//	|7|_|_|_|_|_|_|_|_|
	@Test
	public void makeMoveTest2() {
		table.placePiece(new Queen(null, Color.WHITE), 5, 4);
		table.placePiece(new Bishop(null, Color.BLACK), 2, 1);
		table.placePiece(new Rook(null, Color.BLACK), 3, 6);
		table.placePiece(new Pawn(null, Color.BLACK), 2, 5);
		table.write();
		game.makeMove(2);
		table.write();
	}*/
}
