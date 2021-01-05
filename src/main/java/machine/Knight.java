package machine;

public class Knight extends Piece {

    public Knight(Color color) {
        super(PieceKind.KNIGHT, color);
    }

    public double getValue() {
        return 3;
    }
}
