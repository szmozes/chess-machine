package machine;

import java.util.ArrayList;

public class Piece {

    Field field;
    private final PieceEnum kind;
    private final Color color;


    public Piece(Field field, PieceEnum kind, Color color) {
        this.field = field;
        this.kind = kind;
        this.color = color;
    }

    public Piece(PieceEnum kind, Color color) {
        this.kind = kind;
        this.field = new Field(null, 0, 0);
        this.color = color;
    }

    public Piece(Field field, Color color) {
        this.field = field;
        this.kind = PieceEnum.PAWN;
        this.color = color;
    }

    public ArrayList<int[]> getOpportunities() {
        return null;
    }

    public void write() {
        System.out.print(this.getClass().toString().subSequence(14, 16));
    }

    public double getValue() {
        return 0;
    }

    public Field getField() {
        return this.field;
    }

    public PieceEnum getKind() {
        return this.kind;
    }

    public Color getColor() {
        return this.color;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String toString() {
        return "Piece(field=" + this.getField() + ", kind=" + this.getKind() + ", color=" + this.getColor() + ")";
    }

    void toNorth(ArrayList<int[]> opportunities) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row changes, the column stays
            int oppRow = field.row - i;
            int oppCol = field.column;

            // make sure not to exceed the edges
            if (oppRow < 0) {
                break;
            }

            // get to know what is on the field
            Color color = field.table.getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != this.getColor()) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    void toSouth(ArrayList<int[]> opportunities) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row changes, the column stays
            int oppRow = field.row + i;
            int oppCol = field.column;

            // make sure not to exceed the edges
            if (oppRow >= field.table.height) {
                break;
            }

            // get to know what is on the field
            Color color = field.table.getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != this.getColor()) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    void toWest(ArrayList<int[]> opportunities) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row stays, the column changes
            int oppRow = field.row;
            int oppCol = field.column - i;

            // make sure not to exceed the edges
            if (oppCol < 0) {
                break;
            }

            // get to know what is on the field
            Color color = field.table.getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != this.getColor()) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    void toEast(ArrayList<int[]> opportunities) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row stays, the column changes
            int oppRow = field.row;
            int oppCol = field.column + i;

            // make sure not to exceed the edges
            if (oppCol >= field.table.width) {
                break;
            }

            // get to know what is on the field
            Color color = field.table.getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != this.getColor()) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    void toNorthWest(ArrayList<int[]> opportunities) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row decreases, the column decreases
            int oppRow = field.row - i;
            int oppCol = field.column - i;

            // make sure not to exceed the edges
            if (oppRow < 0 || oppCol < 0) {
                break;
            }

            // get to know what is on the field
            Color color = field.table.getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != this.getColor()) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    void toNorthEast(ArrayList<int[]> opportunities) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row decreases, the column increases
            int oppRow = field.row - i;
            int oppCol = field.column + i;

            // make sure not to exceed the edges
            if (oppRow < 0 || oppCol >= field.table.width) {
                break;
            }

            // get to know what is on the field
            Color color = field.table.getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != this.getColor()) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    void toSouthWest(ArrayList<int[]> opportunities) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row increases, the column decreases
            int oppRow = field.row + i;
            int oppCol = field.column - i;

            // make sure not to exceed the edges
            if (oppRow >= field.table.height || oppCol < 0) {
                break;
            }

            // get to know what is on the field
            Color color = field.table.getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != this.getColor()) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    void toSouthEast(ArrayList<int[]> opportunities) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row increases, the column increases
            int oppRow = field.row + i;
            int oppCol = field.column + i;

            // make sure not to exceed the edges
            if (oppRow >= field.table.height || oppCol >= field.table.width) {
                break;
            }

            // get to know what is on the field
            Color color = field.table.getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != this.getColor()) {
                int[] opportunity = new int[2];
                opportunity[0] = oppRow;
                opportunity[1] = oppCol;
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }
}
