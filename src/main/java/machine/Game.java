package machine;

import java.util.ArrayList;
import java.util.List;

public class Game {
    Table table;
    Controller controller;
    List<Table> history;

    public Game() {
        table = new Table(8, 8);
        history = new ArrayList<>();
    }

    public void init(Controller controller) {
        this.controller = controller;
        standardLineUp();
    }

    public void move(int fromRow, int fromColumn, int toRow, int toColumn) {

        Piece piece = table.fields[fromRow][fromColumn];

        table.placePiece(piece, toRow, toColumn);
        table.placePiece(null, fromRow, fromColumn);

        // check if it's a castle move
        if (piece.kind == PieceKind.KING) {
            if (Math.abs(toColumn - fromColumn) > 1) {
                if (toColumn == 2) {
                    Piece rook = table.fields[toRow][0];
                    table.placePiece(rook, toRow, 3);
                    table.placePiece(null, toRow, 0);
                } else if (toColumn == 6) {
                    Piece rook = table.fields[toRow][7];
                    table.placePiece(rook, toRow, 5);
                    table.placePiece(null, toRow, 7);
                }
            }
        }

        updateCastleRights(piece, fromColumn);
        switchWhoTurns();
    }

    /**
     * Processes a user move
     *
     * @param fromRow    the row where the moved piece was
     * @param fromColumn the column where the moved piece was
     * @param toRow      the row where the moved piece goes
     * @param toColumn   the column where the moved piece goes
     */
    public void userMove(int fromRow, int fromColumn, int toRow, int toColumn) {
        history.add(table.copy());
        move(fromRow, fromColumn, toRow, toColumn);
        controller.refreshView(table);
        wake();
    }

    /**
     * Processes a user move which promotes a pawn
     *
     * @param fromRow    the row where the moved piece was
     * @param fromCol    the column where the moved piece was
     * @param toRow      the row where the moved piece goes
     * @param toCol      the column where the moved piece goes
     * @param chosenKind the piece kind the user want to promote the pawn to
     */
    public void userMovePromoting(int fromRow, int fromCol, int toRow, int toCol, PieceKind chosenKind) {
        history.add(table.copy());
        Piece pawnToPromote = table.fields[fromRow][fromCol];
        move(fromRow, fromCol, toRow, toCol);
        Piece newPiece = null;
        switch (chosenKind) {
            case BISHOP:
                newPiece = new Piece(PieceKind.BISHOP, pawnToPromote.color);
                break;
            case KNIGHT:
                newPiece = new Piece(PieceKind.KNIGHT, pawnToPromote.color);
                break;
            case QUEEN:
                newPiece = new Piece(PieceKind.QUEEN, pawnToPromote.color);
                break;
            case ROOK:
                newPiece = new Piece(PieceKind.ROOK, pawnToPromote.color);
                break;
        }
        table.placePiece(newPiece, toRow, toCol);
        controller.refreshView(table);
        wake();
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
        }

        // initialize the best with a safe value
        double bestMoveValue = 0;
        switch (table.whoTurns) {
            case WHITE:
                bestMoveValue = -100;
                break;
            case BLACK:
                bestMoveValue = 100;
                break;
        }

        // to find the best possible move, we search the whole table for movable pieces
        for (int i = 0; i < table.height; i++) {
            for (int j = 0; j < table.width; j++) {

                // if it's not our piece, we go to the next one
                if (table.whoTurns != getPieceColor(i, j)) {
                    continue;
                }
                List<Position> opportunities = table.getOpportunities(new Position(i, j));

                // go through all of its opportunities
                for (Position opp : opportunities) {

                    // save the data for the restoration
                    Table initialTable = table.copy();
//                    controller.view.table = table;

                    // make a move, and then guess how good the move was
                    move(i, j, opp.row, opp.column);
                    boolean pawnPromoted = false;
                    double howGood = attempt(depth - 1, new int[4]);

                    // make changes back
                    table = initialTable;

                    // found a better move
                    if ((table.whoTurns == Color.WHITE && howGood > bestMoveValue) || (table.whoTurns == Color.BLACK && howGood < bestMoveValue)) {
                        bestMoveValue = howGood;
                        bestCoords[0] = i;
                        bestCoords[1] = j;
                        bestCoords[2] = opp.row;
                        bestCoords[3] = opp.column;
                    }
                }
            }
        }
        return bestMoveValue;
    }

    /**
     * Undoes the last move
     */
    public void undoMove() {
        if (history.size() < 1) {
            return;
        }
        table = history.get(history.size() - 1);
        history.remove(history.size() - 1);
        controller.refreshView(table);
    }

    public void switchWhoTurns() {
        switch (table.whoTurns) {
            case BLACK:
                table.whoTurns = Color.WHITE;
                break;
            case WHITE:
                table.whoTurns = Color.BLACK;
                break;
        }
    }

    public void wake() {
    }

    private Color getPieceColor(int i, int j) {
        Color pieceColor = null;
        if (i >= 0 && i < table.height && j >= 0 && j < table.width) {
            if (table.fields[i][j] != null) {
                pieceColor = table.fields[i][j].color;
            }
        }
        return pieceColor;
    }

    private void standardLineUp() {
        table.fields[0][0] = new Piece(PieceKind.ROOK, Color.BLACK);
        table.fields[0][1] = new Piece(PieceKind.KNIGHT, Color.BLACK);
        table.fields[0][2] = new Piece(PieceKind.BISHOP, Color.BLACK);
        table.fields[0][3] = new Piece(PieceKind.QUEEN, Color.BLACK);
        table.fields[0][4] = new Piece(PieceKind.KING, Color.BLACK);
        table.fields[0][5] = new Piece(PieceKind.BISHOP, Color.BLACK);
        table.fields[0][6] = new Piece(PieceKind.KNIGHT, Color.BLACK);
        table.fields[0][7] = new Piece(PieceKind.ROOK, Color.BLACK);
        table.fields[7][0] = new Piece(PieceKind.ROOK, Color.WHITE);
        table.fields[7][1] = new Piece(PieceKind.KNIGHT, Color.WHITE);
        table.fields[7][2] = new Piece(PieceKind.BISHOP, Color.WHITE);
        table.fields[7][3] = new Piece(PieceKind.QUEEN, Color.WHITE);
        table.fields[7][4] = new Piece(PieceKind.KING, Color.WHITE);
        table.fields[7][5] = new Piece(PieceKind.BISHOP, Color.WHITE);
        table.fields[7][6] = new Piece(PieceKind.KNIGHT, Color.WHITE);
        table.fields[7][7] = new Piece(PieceKind.ROOK, Color.WHITE);
        for (int i = 0; i < 8; i++) {
            table.fields[1][i] = new Piece(PieceKind.PAWN, Color.BLACK);
            table.fields[6][i] = new Piece(PieceKind.PAWN, Color.WHITE);
        }
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

enum Color {
    BLACK,
    WHITE
}

enum GameType {
    BOTH_USER,
    AGAINST_MACHINE
}
