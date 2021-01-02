package machine;

public class StandardGameAgainstMachine extends StandardGame {
    final Color machine;

    public StandardGameAgainstMachine() {
        super();
        machine = Color.BLACK;
        wake();
    }

    public StandardGameAgainstMachine(Color color) {
        machine = color;
        wake();
    }

    public void makeMove(int depth) {
        int[] bestCoords = new int[4];
        attempt(depth, bestCoords);
        move(bestCoords[0], bestCoords[1], bestCoords[2], bestCoords[3]);
    }

    public boolean userMove(int fromRow, int fromColumn, int toRow, int toColumn) {

        boolean pawnReached = move(fromRow, fromColumn, toRow, toColumn);

        // here we should switch a flag what a computing thread watches or something like that

        if (table.whoTurns == machine) {
            makeMove(4);
        }
        return pawnReached;
    }

    public void wake() {
        if (table.whoTurns == machine) {
            makeMove(4);
        }
    }
}
