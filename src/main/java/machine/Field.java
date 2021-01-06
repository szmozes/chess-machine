package machine;

public class Field {
    Piece piece;

    public Field() {
    }

    public Field(Piece piece) {
        this.piece = piece;
    }

    public Field copy() {
        Piece newPiece = null;
        if (piece != null) {
            newPiece = piece.copy();
        }
        return new Field(newPiece);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

}