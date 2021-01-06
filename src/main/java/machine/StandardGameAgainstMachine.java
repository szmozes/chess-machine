package machine;

public class StandardGameAgainstMachine extends StandardGame {
    final Color machineColor;

    public StandardGameAgainstMachine() {
        super();
        machineColor = Color.BLACK;
        wake();
    }

    public StandardGameAgainstMachine(Color color) {
        machineColor = color;
        wake();
    }

    public void makeMove(int depth) {
        int[] bestCoords = new int[4];
        attempt(depth, bestCoords);
        move(bestCoords[0], bestCoords[1], bestCoords[2], bestCoords[3]);
    }

    public boolean userMove(int fromRow, int fromColumn, int toRow, int toColumn) {

        boolean pawnReached = move(fromRow, fromColumn, toRow, toColumn);
        wake();
        return pawnReached;
    }

    public void wake() {
        if (table.whoTurns == machineColor) {
            new Thread(() -> {
                controller.view.table = table.copy();
                makeMove(4);
                controller.view.table = table;
                controller.view.repaint();
            }).start();
        }
    }
}
