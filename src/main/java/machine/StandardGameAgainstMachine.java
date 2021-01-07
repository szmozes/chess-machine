package machine;

public class StandardGameAgainstMachine extends StandardGame {
    final Color machineColor;

    public StandardGameAgainstMachine() {
        machineColor = Color.BLACK;
    }

    public StandardGameAgainstMachine(Color machineColor) {
        this.machineColor = machineColor;
    }

    public void makeMove(int depth) {
        int[] bestCoords = new int[4];
        attempt(depth, bestCoords);
        move(bestCoords[0], bestCoords[1], bestCoords[2], bestCoords[3]);
    }

    public void userMove(int fromRow, int fromColumn, int toRow, int toColumn) {
        move(fromRow, fromColumn, toRow, toColumn);
        wake();
    }

    public void wake() {
        if (table.whoTurns == machineColor) {
            new Thread(() -> {
                controller.view.table = table.copy();
                controller.view.repaint();
                makeMove(4);
                controller.view.table = table;
                controller.view.repaint();
            }).start();
        }
    }
}
