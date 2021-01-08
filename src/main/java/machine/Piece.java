package machine;

public class Piece {
    PieceKind kind;
    Color color;

    public Piece(PieceKind kind, Color color) {
        this.kind = kind;
        this.color = color;
    }

    public double getValue() {
        if (kind == PieceKind.BISHOP) {
            return 3;
        } else if (kind == PieceKind.KING) {
            return 50;
        } else if (kind == PieceKind.KNIGHT) {
            return 3;
        } else if (kind == PieceKind.PAWN) {
            return 1;
        } else if (kind == PieceKind.QUEEN) {
            return 9;
        } else if (kind == PieceKind.ROOK) {
            return 5;
        }
        return 0;
    }

    public Piece copy() {
        return new Piece(kind, color);
    }
}
