package machine;

import java.util.List;

public class StandardGame extends Game {

    public StandardGame() {
        super();
        table.placePiece(new Rook(null, Color.WHITE), 7, 0);
        table.placePiece(new Knight(null, Color.WHITE), 7, 1);
        table.placePiece(new Bishop(null, Color.WHITE), 7, 2);
        table.placePiece(new Queen(null, Color.WHITE), 7, 3);
        table.placePiece(new King(null, Color.WHITE), 7, 4);
        table.placePiece(new Bishop(null, Color.WHITE), 7, 5);
        table.placePiece(new Knight(null, Color.WHITE), 7, 6);
        table.placePiece(new Rook(null, Color.WHITE), 7, 7);
        table.placePiece(new Rook(null, Color.BLACK), 0, 0);
        table.placePiece(new Knight(null, Color.BLACK), 0, 1);
        table.placePiece(new Bishop(null, Color.BLACK), 0, 2);
        table.placePiece(new Queen(null, Color.BLACK), 0, 3);
        table.placePiece(new King(null, Color.BLACK), 0, 4);
        table.placePiece(new Bishop(null, Color.BLACK), 0, 5);
        table.placePiece(new Knight(null, Color.BLACK), 0, 6);
        table.placePiece(new Rook(null, Color.BLACK), 0, 7);
        for (int i = 0; i < 8; i++) {
            table.placePiece(new Pawn(null, Color.WHITE), 6, i);
            table.placePiece(new Pawn(null, Color.BLACK), 1, i);
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

            // save the table state
            Table initialTable = table.getCopy();

            // to find the best possible move, we have to search the whole table
            for (int i = 0; i < table.height; i++) {
                for (int j = 0; j < table.width; j++) {

                    // we found a movable piece, so we will try all its opportunities
                    if (table.getPieceColor(i, j) == table.whoTurns) {
                        Piece analyzedPiece = table.fields[i][j].piece;
                        List<Position> opportunities = table.getOpportunities(new Position(i, j));

                        // go through all of its opportunities
                        for (Position opp : opportunities) {

                            // save the data for the restoration
                            int startingRow = analyzedPiece.field.row;
                            int startingColumn = analyzedPiece.field.column;
                            Piece knocked = table.fields[opp.row][opp.column].piece;

                            // make a move, and then guess, how good the move was
                            move(startingRow, startingColumn, opp.row, opp.column);
                            int[] willBeThrown = new int[4];
                            double howGood = attempt(depth - 1, willBeThrown);

                            // make changes back
                            table.placePiece(knocked, opp.row, opp.column);
                            table.placePiece(analyzedPiece, startingRow, startingColumn);
//                            table = initialTable;
                            switch (table.whoTurns) {
                                case WHITE:
                                    table.whoTurns = Color.BLACK;
                                    break;
                                case BLACK:
                                    table.whoTurns = Color.WHITE;
                                    break;
                            }

                            // we found a better move
                            if ((table.whoTurns == Color.WHITE && howGood > best) || (table.whoTurns == Color.BLACK && howGood < best)) {
                                best = howGood;
                                bestCoords[0] = startingRow;
                                bestCoords[1] = startingColumn;
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

    void setOpportunities(int row, int column) {
        List<Position> opportunities = table.getOpportunities(new Position(row, column));
        for (Position opp : opportunities) {
            table.fields[opp.row][opp.column].canBeSteppedOn = true;
        }
    }

    public boolean move(int fromRow, int fromColumn, int toRow, int toColumn) {

        Piece piece = table.getPiece(fromRow, fromColumn);

        updateCastleRights(piece);
        switchWhoTurns();

        table.placePiece(piece, toRow, toColumn);
        table.placePiece(null, fromRow, fromColumn);

//        // check if it's a castle move
//        if (piece.kind == PieceEnum.KING) {
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

        return piece.kind == PieceEnum.PAWN && pieceOnFurthestRank(piece);
    }

    private void updateCastleRights(Piece movedPiece) {
        if (movedPiece == null) {       // it should be useless
            return;
        }
        if (movedPiece.kind == PieceEnum.KING) {
            if (movedPiece.color == Color.BLACK) {
                table.bk = false;
                table.bq = false;
            } else if (movedPiece.color == Color.WHITE) {
                table.wk = false;
                table.wq = false;
            }
        } else if (movedPiece.kind == PieceEnum.ROOK) {
            if (movedPiece.color == Color.BLACK) {
                if (movedPiece.field.column == 0) {
                    table.bq = false;
                } else if (movedPiece.field.column == 7) {
                    table.bk = false;
                }
            } else if (movedPiece.color == Color.WHITE) {
                if (movedPiece.field.column == 0) {
                    table.wq = false;
                } else if (movedPiece.field.column == 7) {
                    table.wk = false;
                }
            }
        }
    }

    private boolean pieceOnFurthestRank(Piece piece) {
        int furthestRank = piece.color == Color.BLACK ? 7 : 0;
        return piece.field.row == furthestRank;
    }
}
