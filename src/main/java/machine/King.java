package machine;

public class King extends Piece {

    public King(Color color) {
        super(PieceKind.KING, color);
    }

    public double getValue() {
        return 50;
    }
}
