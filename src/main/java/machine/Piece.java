package machine;

public class Piece {
    final PieceEnum kind;
    final Color color;

    public Piece(PieceEnum kind, Color color) {
        this.kind = kind;
        this.color = color;
    }

    public double getValue() {
        return 0;
    }
}
