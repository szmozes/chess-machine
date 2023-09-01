package io.szmozes.chessmachine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class Controller extends MouseAdapter {
    View view;
    Game game;
    GameType gameType;
    Piece grabbed;
    Position grabbedPos;
    UIState state;

    public Controller() {
    }

    public void init(View view, Game game) {
        this.view = view;
        this.game = game;
        gameType = GameType.BOTH_USER;
        grabbed = null;
        grabbedPos = null;
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
                        pickingUpAPiece(row, column);
                        break;
                    case GRABBING:
                        puttingAPiece(row, column);
                        break;
                    case PROMOTING:     // right now it's unreachable
                        break;
                }
            } else {    // clicked out of the chess board
                ControlButtonType clickedButtonType = view.rightMenu.getButtonType(e.getX(), e.getY());
                view.rightMenu.handler.onBtnClicked(clickedButtonType);
            }
        }
        view.repaint();
    }

    /**
     * creates a copy of the table, sets it as the view's model, and repaints the canvas
     * @param table the view paints based on this
     */
    public void refreshView(Table table) {
        view.table = table.copy();
        view.repaint();
    }

    private void puttingAPiece(int row, int column) {
        if (view.opportunities[row][column]) {
            boolean isFurthestRank = grabbed.color == Color.BLACK ? row == 7 : row == 0;
            if (grabbed.kind == PieceKind.PAWN && isFurthestRank) {
                PieceKind chosenKind = view.askUserForPiece();
                game.userMovePromoting(grabbedPos.row, grabbedPos.column, row, column, chosenKind);
            } else {
                game.userMove(grabbedPos.row, grabbedPos.column, row, column);
            }
        }
        resetOpportunities();
        grabbed = null;
        grabbedPos = null;
        state = UIState.CHOOSING;
    }

    private void pickingUpAPiece(int row, int column) {
        Color pieceColor = game.table.getPieceColor(row, column);
        if (pieceColor == game.table.whoTurns) {
            grabbed = game.table.fields[row][column];
            grabbedPos = new Position(row, column);
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
