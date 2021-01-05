package machine;

import java.util.List;

public class StandardGame extends Game {

    public StandardGame() {
        super();
        table.placePiece(new Piece(PieceKind.ROOK, Color.WHITE), 7, 0);
        table.placePiece(new Piece(PieceKind.KNIGHT, Color.WHITE), 7, 1);
        table.placePiece(new Piece(PieceKind.BISHOP, Color.WHITE), 7, 2);
        table.placePiece(new Piece(PieceKind.QUEEN, Color.WHITE), 7, 3);
        table.placePiece(new Piece(PieceKind.KING, Color.WHITE), 7, 4);
        table.placePiece(new Piece(PieceKind.BISHOP, Color.WHITE), 7, 5);
        table.placePiece(new Piece(PieceKind.KNIGHT, Color.WHITE), 7, 6);
        table.placePiece(new Piece(PieceKind.ROOK, Color.WHITE), 7, 7);
        table.placePiece(new Piece(PieceKind.ROOK, Color.BLACK), 0, 0);
        table.placePiece(new Piece(PieceKind.KNIGHT, Color.BLACK), 0, 1);
        table.placePiece(new Piece(PieceKind.BISHOP, Color.BLACK), 0, 2);
        table.placePiece(new Piece(PieceKind.QUEEN, Color.BLACK), 0, 3);
        table.placePiece(new Piece(PieceKind.KING, Color.BLACK), 0, 4);
        table.placePiece(new Piece(PieceKind.BISHOP, Color.BLACK), 0, 5);
        table.placePiece(new Piece(PieceKind.KNIGHT, Color.BLACK), 0, 6);
        table.placePiece(new Piece(PieceKind.ROOK, Color.BLACK), 0, 7);
        for (int i = 0; i < 8; i++) {
            table.placePiece(new Piece(PieceKind.PAWN, Color.WHITE), 6, i);
            table.placePiece(new Piece(PieceKind.PAWN, Color.BLACK), 1, i);
        }
    }

    /**
     * This function sets the best move's coordinates
     * the first two is the starting position,
     * the second two is the landing position
     * and returns the best move's state's value
     * note for the usage: it will try to move the turning player's pieces,
     * so we will have to set the 'whoTurns'
     *
     * @param depth      the recursion's depth
     * @param bestCoords this parameter gets set as the best move's coordinates
     * @return the best move's value
     */
    public double attempt(int depth, int[] bestCoords) {
        if (depth == 0) {
            return table.state();
        } else {

            // we initialize the best with a safe value
            double best = 0;
            switch (table.whoTurns) {
                case WHITE:
                    best = -100;
                    break;
                case BLACK:
                    best = 100;
                    break;
            }

            // to find the best possible move, we search the whole table for movable pieces
            for (int i = 0; i < table.height; i++) {
                for (int j = 0; j < table.width; j++) {

                    // we found a movable piece, so we will try all of its opportunities
                    Color pieceColor = null;
                    Field fieldToGetTheColor = null;
                    Piece pieceToGetTheColor = null;
                    if (i >= 0 && i < table.height && j >= 0 && j < table.width) {
                        fieldToGetTheColor = table.fields[i][j];
                        pieceToGetTheColor = fieldToGetTheColor.piece;
                        if (pieceToGetTheColor != null) {
                            pieceColor = pieceToGetTheColor.color;
                        }
                    }
                    if (pieceColor == table.whoTurns) {
                        Field analyzedField = table.fields[i][j];
                        Piece analyzedPiece = analyzedField.piece;
                        List<Position> opportunities = table.getOpportunities(new Position(i, j));

                        // go through all of its opportunities
                        for (Position opp : opportunities) {

                            // save the data for the restoration
                            Piece knocked = table.fields[opp.row][opp.column].piece;
//                            Table initialTable = table.copy();

                            // make a move, and then guess how good the move was
                            boolean pawnPromoted = move(i, j, opp.row, opp.column);
//                            table.fields[opp.row][opp.column].piece = new Piece(PieceKind.QUEEN, table.whoTurns);
                            double howGood = attempt(depth - 1, new int[4]);

                            // make changes back
                            table.placePiece(knocked, opp.row, opp.column);
                            table.placePiece(analyzedPiece, i, j);
                            switchWhoTurns();
//                            table = initialTable;

                            // we found a better move
                            if ((table.whoTurns == Color.WHITE && howGood > best) || (table.whoTurns == Color.BLACK && howGood < best)) {
                                best = howGood;
                                bestCoords[0] = i;
                                bestCoords[1] = j;
                                bestCoords[2] = opp.row;
                                bestCoords[3] = opp.column;
                            }
                        }
                    }
                }
            }
            return best;
        }
    }

    public boolean move(int fromRow, int fromColumn, int toRow, int toColumn) {

        TestWriter.writeTable(table);

        Field fromField = table.fields[fromRow][fromColumn];
        Piece piece = fromField.piece;

        updateCastleRights(piece, fromColumn);
        switchWhoTurns();

        table.placePiece(piece, toRow, toColumn);
        table.placePiece(null, fromRow, fromColumn);

//        // check if it's a castle move
//        if (piece.kind == PieceKind.KING) {
//            if (Math.abs(toColumn - fromColumn) > 1) {
//                if (toColumn == 1) {
//                    Piece rook = table.fields[toRow][0].piece;
//                    table.placePiece(rook, toRow, 2);
//                    table.placePiece(null, toRow, 0);
//                } else if (toColumn == 6) {
//                    Piece rook = table.fields[toRow][7].piece;
//                    table.placePiece(rook, toRow, 5);
//                    table.placePiece(null, toRow, 7);
//                }
//            }
//        }


        int furthestRank = piece.color == Color.BLACK ? 7 : 0;
        return piece.kind == PieceKind.PAWN && toRow == furthestRank;
    }

    private void updateCastleRights(Piece movedPiece, int fromColumn) {
        if (movedPiece.kind == PieceKind.KING) {
            if (movedPiece.color == Color.BLACK) {
                table.bk = false;
                table.bq = false;
            } else if (movedPiece.color == Color.WHITE) {
                table.wk = false;
                table.wq = false;
            }
        } else if (movedPiece.kind == PieceKind.ROOK) {
            if (movedPiece.color == Color.BLACK) {
                if (fromColumn == 0) {
                    table.bq = false;
                } else if (fromColumn == 7) {
                    table.bk = false;
                }
            } else if (movedPiece.color == Color.WHITE) {
                if (fromColumn == 0) {
                    table.wq = false;
                } else if (fromColumn == 7) {
                    table.wk = false;
                }
            }
        }
    }
}
