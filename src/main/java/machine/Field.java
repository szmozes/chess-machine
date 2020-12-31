package machine;

public class Field {

    Table table;
    int column;
    int row;
    Piece piece;
    boolean steppable;


    public Field(Table table, int row, int column) {
        this.table = table;
        this.row = row;
        this.column = column;
        steppable = false;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
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

    public boolean isSteppable() {
        return steppable;
    }

    public void setSteppable(boolean steppable) {
        this.steppable = steppable;
    }

    public String toString() {
        return "Field(table=" + this.getTable() + ", column=" + this.getColumn() + ", row=" + this.getRow() + ", piece=" + this.getPiece() + ", steppable=" + this.isSteppable() + ")";
    }
}