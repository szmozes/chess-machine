package machine;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(Field field, Color color) {
        super(field, PieceEnum.BISHOP, color);
    }

    public ArrayList<int[]> getOpportunities() {

        // the opportunities (x-y pairs) are stored here
        ArrayList<int[]> opportunities = new ArrayList<>();

        toNorthWest(opportunities);
        toNorthEast(opportunities);
        toSouthWest(opportunities);
        toSouthEast(opportunities);

        return opportunities;
    }

    public double getValue() {
        return 3;
    }
}
