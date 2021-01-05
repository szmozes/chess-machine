package machine;

public class Rook extends Piece {

    public Rook(Color color) {
        super(PieceKind.ROOK, color);
    }

    public double getValue() {
        return 5;
    }
}
