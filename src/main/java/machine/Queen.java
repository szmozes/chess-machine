package machine;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(Field field, Color color) {
        super(field, PieceEnum.QUEEN, color);
    }

    public ArrayList<int[]> getOpportunities() {

        // the opportunities (x-y pairs) are stored here
        ArrayList<int[]> opportunities = new ArrayList<>();

        toNorth(opportunities);
        toSouth(opportunities);
        toWest(opportunities);
        toEast(opportunities);

        toNorthWest(opportunities);
        toNorthEast(opportunities);
        toSouthWest(opportunities);
        toSouthEast(opportunities);

        return opportunities;
    }

    public double getValue() {
        return 9;
    }
}
