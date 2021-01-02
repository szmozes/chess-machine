package machine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseListener extends MouseAdapter {
    View view;
    Game game;
    Piece grabbed;
    UIState state;

    public MyMouseListener(View view, Game game) {
        this.view = view;
        this.game = game;
        grabbed = null;
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
                        choosingAPiece(column, row);
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
        game.wake();
    }

    private void promotingAPawn() {
        PieceEnum newPieceKind = view.askUserForPiece();
        Piece newPiece = null;
        switch (newPieceKind) {
            case BISHOP:
                newPiece = new Bishop(grabbed.field, grabbed.color);
                break;
            case KNIGHT:
                newPiece = new Knight(grabbed.field, grabbed.color);
                break;
            case QUEEN:
                newPiece = new Queen(grabbed.field, grabbed.color);
                break;
            case ROOK:
                newPiece = new Rook(grabbed.field, grabbed.color);
                break;
        }
        game.table.placePiece(newPiece, grabbed.field.row, grabbed.field.column);
        grabbed = null;
        state = UIState.CHOOSING;
    }

    private void puttingAPiece(int column, int row) {
        if (view.table.fields[row][column].steppable) {
            boolean pawnToFinalRank = game.userMove(grabbed.field.row, grabbed.field.column, row, column);
            view.table.makeFieldsUnsteppable();
            if (pawnToFinalRank) {
                promotingAPawn();
            } else {
                grabbed = null;
                state = UIState.CHOOSING;
            }
        } else {
            view.table.makeFieldsUnsteppable();
            grabbed = null;
            state = UIState.CHOOSING;
        }
    }

    private void choosingAPiece(int column, int row) {
        Color pieceColor = view.table.getPieceColor(row, column);
        if (pieceColor == game.whoTurns) {
            grabbed = view.table.fields[row][column].piece;
            game.setOpportunities(row, column);
            state = UIState.GRABBING;
        }
    }
}

enum UIState {
    CHOOSING,
    GRABBING,
    PROMOTING
}
