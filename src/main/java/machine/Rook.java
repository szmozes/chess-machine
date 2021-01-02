package machine;

public class Rook extends Piece {

    public Rook(Color color) {
        super(PieceEnum.ROOK, color);
    }

    public double getValue() {
        return 5;
    }
}
