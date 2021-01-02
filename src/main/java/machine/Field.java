package machine;

public class Field {
    Table table;
    int column;
    int row;
    Piece piece;
    boolean canBeSteppedOn;

    public Field(Table table, int row, int column) {
        this.table = table;
        this.row = row;
        this.column = column;
        canBeSteppedOn = false;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
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