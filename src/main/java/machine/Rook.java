package machine;

public class Rook extends Piece {

    public Rook(Field field, Color color) {
        super(field, PieceEnum.ROOK, color);
    }

    public double getValue() {
        return 5;
    }
}
