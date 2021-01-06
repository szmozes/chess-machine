package machine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class Controller extends MouseAdapter {
    View view;
    Game game;
    GameType gameType;
    Piece grabbed;
    Position grabbedPiecePosition;
    UIState state;

    public Controller(View view, Game game) {
        this.view = view;
        this.game = game;
        grabbed = null;
        grabbedPiecePosition = null;
        state = UIState.CHOOSING;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {

            // get the clicked field
            int column = e.getX() / view.size;
            int row = e.getY() / view.size;

            if ((column < 8) && (row < 8)) { // clicked on the chess board
                switch (state) {
                    case CHOOSING:
                        pickingUpAPiece(column, row);
                        break;
                    case GRABBING:
                        puttingAPiece(column, row);
                        break;
                    case PROMOTING:     // right now it's unreachable
                        promotingAPawn();
                        break;
                }
            } else {    // clicked out of the chess board
                int clickedButton = view.rightMenu.getButtonID(e.getX(), e.getY());
                view.handleMenu.buttonHandler(clickedButton);
            }

        }
        view.repaint();
    }

    private void promotingAPawn() {
        PieceKind newPieceKind = view.askUserForPiece();
        Piece newPiece = null;
        switch (newPieceKind) {
            case BISHOP:
                newPiece = new Piece(PieceKind.BISHOP, grabbed.color);
                break;
            case KNIGHT:
                newPiece = new Piece(PieceKind.KNIGHT, grabbed.color);
                break;
            case QUEEN:
                newPiece = new Piece(PieceKind.QUEEN, grabbed.color);
                break;
            case ROOK:
                newPiece = new Piece(PieceKind.ROOK, grabbed.color);
                break;
        }
        game.table.placePiece(newPiece, grabbedPiecePosition.row, grabbedPiecePosition.column);
        grabbed = null;
        grabbedPiecePosition = null;
        state = UIState.CHOOSING;
    }

    private void puttingAPiece(int column, int row) {
        if (view.opportunities[row][column]) {
            boolean pawnToFinalRank = game.userMove(grabbedPiecePosition.row, grabbedPiecePosition.column, row, column);
            grabbedPiecePosition = new Position(row, column);
            resetOpportunities();
            if (pawnToFinalRank) {
                promotingAPawn();
            } else {
                grabbed = null;
                grabbedPiecePosition = null;
                state = UIState.CHOOSING;
            }
        } else {
            resetOpportunities();
            grabbed = null;
            grabbedPiecePosition = null;
            state = UIState.CHOOSING;
        }
    }

    private void pickingUpAPiece(int column, int row) {
        Color pieceColor = game.table.getPieceColor(row, column);
        if (pieceColor == game.table.whoTurns) {
            grabbed = game.table.fields[row][column];
            grabbedPiecePosition = new Position(row, column);
            setOpportunities(row, column);
            state = UIState.GRABBING;
        }
    }

    private void setOpportunities(int row, int column) {
        List<Position> opportunities = game.table.getOpportunities(new Position(row, column));
        for (Position opp : opportunities) {
            view.opportunities[opp.row][opp.column] = true;
        }
    }

    private void resetOpportunities() {
        for (int i = 0; i < view.opportunities.length; i++) {
            Arrays.fill(view.opportunities[i], false);
        }
    }
}

enum UIState {
    CHOOSING,
    GRABBING,
    PROMOTING
}