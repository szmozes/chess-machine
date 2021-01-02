package machine;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(PieceEnum.PAWN, color);
    }

    public double getValue() {
        return 1;
    }
}
