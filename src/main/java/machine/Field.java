package machine;

public class Field {
    Piece piece;
    boolean canBeSteppedOn;

    public Field() {
        canBeSteppedOn = false;
    }

    public Field(Piece piece, boolean canBeSteppedOn) {
        this.piece = piece;
        this.canBeSteppedOn = canBeSteppedOn;
    }

    public Field copy() {
        Piece newPiece = null;
        if (piece != null) {
            newPiece = piece.copy();
        }
        return new Field(newPiece, canBeSteppedOn);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isCanBeSteppedOn() {
        return canBeSteppedOn;
    }

}