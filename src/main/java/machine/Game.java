package machine;

import java.util.ArrayList;
import java.util.List;

public class Game {
    Table table;
    Controller controller;
    List<Table> history;

    public Game() {
        table = new Table();
        history = new ArrayList<>();
    }

    public void init(Controller controller) {
        this.controller = controller;
        standardLineUp();
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
        table.move(fromRow, fromColumn, toRow, toColumn);
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
        table.move(fromRow, fromCol, toRow, toCol);
        Piece newPiece = null;
        if (chosenKind != null) {
            newPiece = new Piece(chosenKind, pawnToPromote.color);
        }
        table.placePiece(newPiece, toRow, toCol);
        controller.refreshView(table);
        wake();
    }

    /**
     * This function returns the state's value - which player has more chance to win
     * note for the usage: it will try to move the turning player's pieces,
     * so we will have to set the 'whoTurns'
     *
     * @param depth the recursion's depth
     * @param table the function calculates the state value based on this
     * @param alpha the caller's best move's value for white
     * @param beta  the caller's best move's value for black
     * @return the best move's value
     */
    public static double stateValue(int depth, Table table, double alpha, double beta) {
        if (depth == 0) {
            return table.eval();
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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                // if it's not our piece, we go to the next one
                if (table.whoTurns != table.getPieceColor(i, j)) {
                    continue;
                }
                List<Position> opportunities = table.getOpportunities(new Position(i, j));

                // go through all of its opportunities
                for (Position opp : opportunities) {

                    // make a copy so the initial doesn't change
                    Table copiedTable = table.copy();

                    // make a move, and then guess how good the move was
                    copiedTable.move(i, j, opp.row, opp.column);
                    double attemptValue = stateValue(depth - 1, copiedTable, alpha, beta);

                    // alpha-beta pruning
                    if (table.whoTurns == Color.WHITE) {
                        bestMoveValue = Math.max(bestMoveValue, attemptValue);
                        if (bestMoveValue >= beta) {
                            return bestMoveValue;
                        }
                        alpha = Math.max(alpha, attemptValue);
                    }
                    if (table.whoTurns == Color.BLACK) {
                        bestMoveValue = Math.min(bestMoveValue, attemptValue);
                        if (bestMoveValue <= alpha) {
                            return bestMoveValue;
                        }
                        beta = Math.min(beta, bestMoveValue);
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

    public void wake() {
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
}
