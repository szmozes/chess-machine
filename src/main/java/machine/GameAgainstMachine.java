package machine;

import java.util.List;

public class GameAgainstMachine extends Game {
    final Color machineColor;

    public GameAgainstMachine() {
        machineColor = Color.BLACK;
    }

    public GameAgainstMachine(Color machineColor) {
        this.machineColor = machineColor;
    }

    public void makeMove(int depth) {
        history.add(table.copy());
        int[] bestCoords = new int[4];
        findBestMove(depth, bestCoords, this.table);
        table.move(bestCoords[0], bestCoords[1], bestCoords[2], bestCoords[3]);
    }

    public static double findBestMove(int depth, int[] bestCoords, Table table) {
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
//                    controller.view.table = table;

                    // make a move, and then guess how good the move was
                    copiedTable.move(i, j, opp.row, opp.column);
                    double howGood = stateValue(depth - 1, copiedTable, -1000, 1000);

                    // found a better move
                    if ((table.whoTurns == Color.WHITE && howGood > bestMoveValue) ||
                            (table.whoTurns == Color.BLACK && howGood < bestMoveValue)) {
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

    public void undoMove() {
        if (history.size() < 2) {
            return;
        }
        table = history.get(history.size() - 2);
        history.remove(history.size() - 1);
        history.remove(history.size() - 1);
        controller.refreshView(table);
    }

    public void wake() {
        if (table.whoTurns == machineColor) {
            new Thread(() -> {
                controller.refreshView(table);
                makeMove(5);
                controller.refreshView(table);
            }).start();
        }
    }
}
