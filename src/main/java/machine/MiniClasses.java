package machine;

enum UIState {
    CHOOSING,
    GRABBING,
    PROMOTING
}

enum Color {
    BLACK,
    WHITE
}

enum GameType {
    BOTH_USER,
    AGAINST_MACHINE
}

enum PieceKind {
    PAWN,
    ROOK,
    KNIGHT,
    BISHOP,
    QUEEN,
    KING
}

enum ControlButtonType {
    FAST_BACKWARD,
    BACKWARD,
    FORWARD,
    FAST_FORWARD
}

class Position {
    final int row;
    final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
