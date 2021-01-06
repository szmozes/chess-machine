package machine;

import java.util.ArrayList;
import java.util.List;

//	__________________	___________________
//	|\|0|1|2|3|4|5|6|7|	|8|_|_|_|_|_|_|_|_|
//	|0|_|_|_|_|_|_|_|_|	|7|_|_|_|_|_|_|_|_|
//	|1|_|_|_|_|_|_|_|_|	|6|_|_|_|_|_|_|_|_|
//	|2|_|_|_|_|_|_|_|_|	|5|_|_|_|_|_|_|_|_|
//	|3|_|_|_|_|_|_|_|_|	|4|_|_|_|_|_|_|_|_|
//	|4|_|_|_|_|_|_|_|_|	|3|_|_|_|_|_|_|_|_|
//	|5|_|_|_|_|_|_|_|_|	|2|_|_|_|_|_|_|_|_|
//	|6|_|_|_|_|_|_|_|_|	|1|_|_|_|_|_|_|_|_|
//	|7|_|_|_|_|_|_|_|_|	|/|a|b|c|d|e|f|g|h|

public class Table {
    final Field[][] fields;
    int height;
    int width;
    Color whoTurns;
    boolean wk, wq, bk, bq;    // how the players can castle

    public Table(int height, int width) {
        this.height = height;
        this.width = width;
        fields = new Field[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fields[i][j] = new Field();
            }
        }
        whoTurns = Color.WHITE;
        wk = wq = bk = bq = true;
    }

    public Table(Field[][] fields, int height, int width, Color whoTurns, boolean wk, boolean wq, boolean bk, boolean bq) {
        this.fields = fields;
        this.height = height;
        this.width = width;
        this.whoTurns = whoTurns;
        this.wk = wk;
        this.wq = wq;
        this.bk = bk;
        this.bq = bq;
    }

    public Table copy() {
        Field[][] newFields;
        newFields = new Field[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newFields[i][j] = fields[i][j].copy();
            }
        }
        return new Table(newFields, height, width, whoTurns, wk, wq, bk, bq);
    }

    public Piece getPiece(int row, int column) {
        return fields[row][column].piece;
    }

    public void placePiece(Piece piece, int row, int column) {
        Field field = fields[row][column];
        field.setPiece(piece);
    }

    public Color getPieceColor(int row, int column) {
        if (row < 0 || row >= height || column < 0 || column >= width) {
            return null;
        }
        Piece piece = fields[row][column].piece;
        if (piece == null) {
            return null;
        } else {
            return piece.color;
        }
    }

    /**
     * a primitive way to get the current state (which player has more chance to win)
     */
    public double state() {
        double ret = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Piece piece = fields[i][j].piece;
                if (piece != null) {
                    Color color = piece.color;
                    switch (color) {
                        case WHITE:
                            ret += piece.getValue();
                            break;
                        case BLACK:
                            ret -= piece.getValue();
                            break;
                    }
                }
            }
        }
        return ret;
    }

    public boolean isBQAvailable() {
        boolean areFieldsEmpty = fields[0][1].piece == null && fields[0][2].piece == null && fields[0][3].piece == null;
        return bq && areFieldsEmpty;
    }

    public boolean isBKAvailable() {
        boolean areFieldsEmpty = fields[0][5].piece == null && fields[0][6].piece == null;
        return bk && areFieldsEmpty;
    }

    public boolean isWQAvailable() {
        boolean areFieldsEmpty = fields[7][1].piece == null && fields[7][2].piece == null && fields[7][3].piece == null;
        return wq && areFieldsEmpty;
    }

    public boolean isWKAvailable() {
        boolean areFieldsEmpty = fields[7][5].piece == null && fields[7][6].piece == null;
        return wk && areFieldsEmpty;
    }

    public List<Position> getOpportunities(Position fieldPosition) {
        Piece piece = fields[fieldPosition.row][fieldPosition.column].piece;
        List<Position> opportunities = new ArrayList<>();
        if (piece == null) {
            return opportunities;
        }
        switch (piece.kind) {
            case BISHOP:
                opportunities.addAll(getBishopOpportunities(piece, fieldPosition));
                break;
            case KING:
                opportunities.addAll(getKingOpportunities(piece, fieldPosition));
                break;
            case KNIGHT:
                opportunities.addAll(getKnightOpportunities(piece, fieldPosition));
                break;
            case PAWN:
                opportunities.addAll(getPawnOpportunities(piece, fieldPosition));
                break;
            case QUEEN:
                opportunities.addAll(getQueenOpportunities(piece, fieldPosition));
                break;
            case ROOK:
                opportunities.addAll(getRookOpportunities(piece, fieldPosition));
                break;
        }
        return opportunities;
    }

    private List<Position> getRookOpportunities(Piece rook, Position position) {

        // the opportunities (x-y pairs) are stored here
        ArrayList<Position> opportunities = new ArrayList<>();

        toNorth(opportunities, rook, position);
        toSouth(opportunities, rook, position);
        toWest(opportunities, rook, position);
        toEast(opportunities, rook, position);
        return opportunities;
    }

    private List<Position> getQueenOpportunities(Piece piece, Position position) {

        // the opportunities (x-y pairs) are stored here
        ArrayList<Position> opportunities = new ArrayList<>();

        toNorth(opportunities, piece, position);
        toSouth(opportunities, piece, position);
        toWest(opportunities, piece, position);
        toEast(opportunities, piece, position);

        toNorthWest(opportunities, piece, position);
        toNorthEast(opportunities, piece, position);
        toSouthWest(opportunities, piece, position);
        toSouthEast(opportunities, piece, position);
        return opportunities;
    }

    private List<Position> getBishopOpportunities(Piece piece, Position position) {

        // the opportunities (x-y pairs) are stored here
        ArrayList<Position> opportunities = new ArrayList<>();

        toNorthWest(opportunities, piece, position);
        toNorthEast(opportunities, piece, position);
        toSouthWest(opportunities, piece, position);
        toSouthEast(opportunities, piece, position);
        return opportunities;
    }

    private List<Position> getKingOpportunities(Piece king, Position position) {

        // the opportunities (x-y pairs) are stored here
        ArrayList<Position> opportunities = new ArrayList<>();

        // here we have the relative spots
        int[] rowPos = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colPos = {-1, 0, 1, -1, 1, -1, 0, 1};

        // we go through one-by-one, to see if we can add it to the possible opportunities
        for (int i = 0; i < 8; i++) {

            // to get the exact spot, we add the current and the relative coordinates
            int oppRow = position.row + rowPos[i];
            int oppCol = position.column + colPos[i];

            // first, we have to make sure we don't exceed the table's edges
            if (oppRow < 0 || oppRow >= height || oppCol < 0 || oppCol >= width) {
                // in case we exceed, we ignore the opportunity, and go to the next one
                continue;
            }

            // an then, the other conditions, for example, if there is a same colored piece
            if (getPieceColor(oppRow, oppCol) != king.color) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
            }
        }

//        // add the castling opportunities
//        if (color == Color.BLACK) {
//            if (isBQAvailable()) {
//                opportunities.add(new Position{0, 2});
//            }
//            if (isBKAvailable()) {
//                opportunities.add(new Position{0, 6});
//            }
//        } else if (color == Color.WHITE) {
//            if (isWQAvailable()) {
//                opportunities.add(new Position{7, 2});
//            }
//            if (isWKAvailable()) {
//                opportunities.add(new Position{7, 6});
//            }
//        }
        return opportunities;
    }

    private List<Position> getKnightOpportunities(Piece knight, Position position) {

        // the opportunities (x-y pairs) are stored here
        ArrayList<Position> opportunities = new ArrayList<>();

        // here we have the relative spots
        int[] rowPos = {-1, -2, -2, -1, 1, 2, 2, 1};
        int[] colPos = {-2, -1, 1, 2, 2, 1, -1, -2};

        // we go through one-by-one, to see if we can add it to the possible opportunities
        for (int i = 0; i < 8; i++) {

            // to get the exact spot, we add the current and the relative coordinates
            int oppRow = position.row + rowPos[i];
            int oppCol = position.column + colPos[i];

            // first, we have to make sure we don't exceed the table's edges
            if (oppRow < 0 || oppRow >= height || oppCol < 0 || oppCol >= width) {
                // in case we exceed, we ignore the opportunity, and go to the next one
                continue;
            }

            // an then, the other conditions, for example, if there is a same colored piece
            if (getPieceColor(oppRow, oppCol) != knight.color) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
            }
        }
        return opportunities;
    }

    private List<Position> getPawnOpportunities(Piece pawn, Position position) {

        // the opportunities (x-y pairs) are stored here
        ArrayList<Position> opportunities = new ArrayList<>();

        int row = position.row;
        int col = position.column;

        // first we distinguish between black and white
        switch (pawn.color) {
            case WHITE:

                // make sure not to exceed the table edge
                if (row - 1 < 0) {
                    return opportunities;
                }

                // the fields before it are empty
                boolean freeNorth1 = getPieceColor(row - 1, col) == null;
                boolean freeNorth2 = getPieceColor(row - 2, col) == null;

                // check if it can step 1
                if (freeNorth1) {
                    Position opportunity = new Position(row - 1, col);
                    opportunities.add(opportunity);
                }

                // check if it can step 2
                if (row == 6 && freeNorth1 && freeNorth2) {
                    Position opportunity = new Position(row - 2, col);
                    opportunities.add(opportunity);
                }

                // capture to west
                if (col - 1 >= 0) {
                    Color westColor = getPieceColor(row - 1, col - 1);
                    if (westColor != null && westColor != pawn.color) {
                        Position opportunity = new Position(row - 1, col - 1);
                        opportunities.add(opportunity);
                    }
                }

                // capture to east
                if (col + 1 < width) {
                    Color eastColor = getPieceColor(row - 1, col + 1);
                    if (eastColor != null && eastColor != pawn.color) {
                        Position opportunity = new Position(row - 1, col + 1);
                        opportunities.add(opportunity);
                    }
                }
                break;
            case BLACK:

                // make sure not to exceed the table edge
                if (row + 1 >= height) {
                    return opportunities;
                }

                // the fields before it are empty
                boolean freeSouth1 = getPieceColor(row + 1, col) == null;
                boolean freeSouth2 = getPieceColor(row + 2, col) == null;

                // check if it can step 1
                if (freeSouth1) {
                    Position opportunity = new Position(row + 1, col);
                    opportunities.add(opportunity);
                }

                // check if it can step 2
                if (row == 1 && freeSouth1 && freeSouth2) {
                    Position opportunity = new Position(row + 2, col);
                    opportunities.add(opportunity);
                }

                // capture to west
                if (col - 1 >= 0) {
                    Color westColor = getPieceColor(row + 1, col - 1);
                    if (westColor != null && westColor != pawn.color) {
                        Position opportunity = new Position(row + 1, col - 1);
                        opportunities.add(opportunity);
                    }
                }

                // capture to east
                if (col + 1 < width) {
                    Color eastColor = getPieceColor(row + 1, col + 1);
                    if (eastColor != null && eastColor != pawn.color) {
                        Position opportunity = new Position(row + 1, col + 1);
                        opportunities.add(opportunity);
                    }
                }
                break;
        }

        return opportunities;

    }

    private void toNorth(ArrayList<Position> opportunities, Piece piece, Position position) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row changes, the column stays
            int oppRow = position.row - i;
            int oppCol = position.column;

            // make sure not to exceed the edges
            if (oppRow < 0) {
                break;
            }

            // get to know what is on the field
            Color color = getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != piece.color) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    private void toSouth(ArrayList<Position> opportunities, Piece piece, Position position) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row changes, the column stays
            int oppRow = position.row + i;
            int oppCol = position.column;

            // make sure not to exceed the edges
            if (oppRow >= height) {
                break;
            }

            // get to know what is on the field
            Color color = getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != piece.color) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    private void toWest(ArrayList<Position> opportunities, Piece piece, Position position) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row stays, the column changes
            int oppRow = position.row;
            int oppCol = position.column - i;

            // make sure not to exceed the edges
            if (oppCol < 0) {
                break;
            }

            // get to know what is on the field
            Color color = getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != piece.color) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    private void toEast(ArrayList<Position> opportunities, Piece piece, Position position) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row stays, the column changes
            int oppRow = position.row;
            int oppCol = position.column + i;

            // make sure not to exceed the edges
            if (oppCol >= width) {
                break;
            }

            // get to know what is on the field
            Color color = getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != piece.color) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    private void toNorthWest(ArrayList<Position> opportunities, Piece piece, Position position) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row decreases, the column decreases
            int oppRow = position.row - i;
            int oppCol = position.column - i;

            // make sure not to exceed the edges
            if (oppRow < 0 || oppCol < 0) {
                break;
            }

            // get to know what is on the field
            Color color = getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != piece.color) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    private void toNorthEast(ArrayList<Position> opportunities, Piece piece, Position position) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row decreases, the column increases
            int oppRow = position.row - i;
            int oppCol = position.column + i;

            // make sure not to exceed the edges
            if (oppRow < 0 || oppCol >= width) {
                break;
            }

            // get to know what is on the field
            Color color = getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != piece.color) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    private void toSouthWest(ArrayList<Position> opportunities, Piece piece, Position position) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row increases, the column decreases
            int oppRow = position.row + i;
            int oppCol = position.column - i;

            // make sure not to exceed the edges
            if (oppRow >= height || oppCol < 0) {
                break;
            }

            // get to know what is on the field
            Color color = getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != piece.color) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }

    private void toSouthEast(ArrayList<Position> opportunities, Piece piece, Position position) {
        for (int i = 1; i < 8; i++) {

            // to get the opportunity's spot, we add the iterator to the initial spot
            // the row increases, the column increases
            int oppRow = position.row + i;
            int oppCol = position.column + i;

            // make sure not to exceed the edges
            if (oppRow >= height || oppCol >= width) {
                break;
            }

            // get to know what is on the field
            Color color = getPieceColor(oppRow, oppCol);

            // if it's empty, we add the opportunity, and go further
            if (color == null) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                continue;
            }

            // if it's not empty, we stop, and add the opportunity if needed
            if (color != piece.color) {
                Position opportunity = new Position(oppRow, oppCol);
                opportunities.add(opportunity);
                break;
            }

            // if it's neither empty nor enemy, then we can't go further
            break;
        }
    }
}

class Position {
    final int row;
    final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
