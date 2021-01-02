package machine;

import java.util.ArrayList;

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
     * this function sets the best move's coordinates
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
            switch (whoTurns) {
                case WHITE:
                    best = -100;
                    break;
                case BLACK:
                    best = 100;
                    break;
            }

            // to find the best possible move, we have to search the whole table
            for (int i = 0; i < table.height; i++) {
                for (int j = 0; j < table.width; j++) {

                    // we found a movable piece, so we will try all its opportunities
                    if (table.getPieceColor(i, j) == whoTurns) {
                        Piece analyzedPiece = table.fields[i][j].piece;
                        ArrayList<int[]> opps = analyzedPiece.getOpportunities();

                        // go through all of its opportunities
                        for (int[] opp : opps) {

                            // save the data for the restoration
                            int startingRow = analyzedPiece.field.row;
                            int startingColumn = analyzedPiece.field.column;
                            Piece knocked = table.fields[opp[0]][opp[1]].piece;

                            // make a move, and then guess, how good the move was
                            move(startingRow, startingColumn, opp[0], opp[1]);
                            int[] willBeThrown = new int[4];
                            double howGood = attempt(depth - 1, willBeThrown);

                            // make changes back
                            table.placePiece(knocked, opp[0], opp[1]);
                            table.placePiece(analyzedPiece, startingRow, startingColumn);
                            switch (whoTurns) {
                                case WHITE:
                                    whoTurns = Color.BLACK;
                                    break;
                                case BLACK:
                                    whoTurns = Color.WHITE;
                                    break;
                            }

                            // we found a better move
                            if ((whoTurns == Color.WHITE && howGood > best) || (whoTurns == Color.BLACK && howGood < best)) {

                                best = howGood;
                                bestCoords[0] = startingRow;
                                bestCoords[1] = startingColumn;
                                bestCoords[2] = opp[0];
                                bestCoords[3] = opp[1];
                            }
                        }
                    }
                }
            }
            return best;
        }
    }

    void setOpportunities(int row, int column) {
        ArrayList<int[]> opps = table.getPiece(row, column).getOpportunities();
        for (int[] opp : opps) {
            table.fields[opp[0]][opp[1]].steppable = true;
        }
    }

    public boolean move(int fromRow, int fromColumn, int toRow, int toColumn) {

        // we execute the move
        Piece piece = table.getPiece(fromRow, fromColumn);
        table.placePiece(piece, toRow, toColumn);
        table.placePiece(null, fromRow, fromColumn);

        switchWhoTurns();

        boolean pawnReached = piece.kind == PieceEnum.PAWN && pieceOnFurthestRank(piece);
        return pawnReached;
    }

    private boolean pieceOnFurthestRank(Piece piece) {
        int furthestRank = piece.color == Color.BLACK ? 7 : 0;
        return piece.field.row == furthestRank;
    }
}
