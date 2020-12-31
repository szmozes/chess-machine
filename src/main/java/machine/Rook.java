package machine;

import java.util.ArrayList;

public class Rook extends Piece {

    boolean hasNotMovedYet;


    public Rook(Field field, Color color) {
        super(field, PieceEnum.ROOK, color);
        hasNotMovedYet = true;
    }

    public ArrayList<int[]> getOpportunities() {

        // the opportunities (x-y pairs) are stored here
        ArrayList<int[]> opportunities = new ArrayList<>();

        toNorth(opportunities);
        toSouth(opportunities);
        toWest(opportunities);
        toEast(opportunities);

        return opportunities;
    }

    public double getValue() {
        return 5;
    }
}
