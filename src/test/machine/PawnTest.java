package machine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PawnTest {
    Table table;
    Pawn pawn;
    Piece p1;
    Piece p2;
    Piece p3;

    @Before
    public void setUp() {
        table = new Table(8, 8);
        pawn = new Pawn(null, Color.WHITE);
        p1 = new Piece(new Field(null, 0, 0), Color.WHITE);
        p2 = new Piece(new Field(null, 0, 0), Color.WHITE);
        p3 = new Piece(new Field(null, 0, 0), Color.BLACK);
    }

    /*A - Ally
    E - Enemy
    O - Test

      ___________________
      |\|0|1|2|3|4|5|6|7|
      |0|_|_|_|_|_|_|_|_|
      |1|_|_|_|_|_|_|_|_|
      |2|_|_|_|_|_|_|_|_|
      |3|_|_|_|_|_|_|_|_|
      |4|_|_|_|_|_|_|_|_|
      |5|_|_|_|A|A|E|_|_|
      |6|_|_|_|_|O|_|_|_|
      |7|_|_|_|_|_|_|_|_|

    */
    @Test
    public void opportunitiesTest1() {
        table.placePiece(pawn, 6, 4);
        table.placePiece(p1, 5, 4);
        table.placePiece(p2, 5, 3);
        table.placePiece(p3, 5, 5);
        int result;
        ArrayList<int[]> opportunities = pawn.getOpportunities();
        for (int i = 0; i < opportunities.size(); i++) {
            for (int j = 0; j < opportunities.get(i).length; j++) {
                result = opportunities.get(i)[j];
                Assert.assertEquals(5, result, 0);
                System.out.print(result + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
     A - Ally
     E - Enemy
     O - Test

       ___________________
       |\|0|1|2|3|4|5|6|7|
       |0|_|_|_|_|_|_|_|_|
       |1|_|_|_|_|_|_|_|_|
       |2|_|_|_|_|_|_|_|_|
       |3|_|_|_|_|_|_|_|_|
       |4|_|_|_|_|_|_|_|_|
       |5|_|_|_|_|_|_|_|_|
       |6|_|_|O|_|_|_|_|_|
       |7|_|_|_|_|_|_|_|_|

     */
    @Test
    public void opportunitiesTest2() {
        table.placePiece(pawn, 6, 2);
        ArrayList<int[]> opportunities = pawn.getOpportunities();
        for (int i = 0; i < opportunities.size(); i++) {
            for (int j = 0; j < opportunities.get(i).length; j++) {
                System.out.print(opportunities.get(i)[j] + " ");
            }
            System.out.println();
        }
    }
}
