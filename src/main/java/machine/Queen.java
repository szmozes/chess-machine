package machine;

public class Queen extends Piece {

    public Queen(Color color) {
        super(PieceKind.QUEEN, color);
    }

    public double getValue() {
        return 9;
    }
}
