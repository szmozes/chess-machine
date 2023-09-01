package io.szmozes.chessmachine;

public class Piece {
    PieceKind kind;
    Color color;

    public Piece(PieceKind kind, Color color) {
        this.kind = kind;
        this.color = color;
    }

    public double getValue() {
        switch (kind) {
            case BISHOP:
                return 3;
            case KING:
                return 50;
            case KNIGHT:
                return 3;
            case PAWN:
                return 1;
            case QUEEN:
                return 9;
            case ROOK:
                return 5;
        }
        return 0;
    }
}
