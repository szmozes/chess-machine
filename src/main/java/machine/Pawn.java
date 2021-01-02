package machine;

public class Pawn extends Piece {

    public Pawn(Field field, Color color) {
        super(field, PieceEnum.PAWN, color);
    }

    public double getValue() {
        return 1;
    }
}
