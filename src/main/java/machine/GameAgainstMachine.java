package machine;

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
        attempt(depth, bestCoords);
        move(bestCoords[0], bestCoords[1], bestCoords[2], bestCoords[3]);
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
                makeMove(4);
                controller.refreshView(table);
            }).start();
        }
    }
}
