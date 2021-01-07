package machine;

public abstract class Game {
    Table table;
    Controller controller;

    public Game() {
        table = new Table(8, 8);
    }

    public abstract void move(int fromRow, int fromColumn, int toRow, int toColumn);

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

    /**
     * Processes a user move
     *
     * @param fromRow    the row where the moved piece was
     * @param fromColumn the column where the moved piece was
     * @param toRow      the row where the moved piece goes
     * @param toColumn   the column where the moved piece goes
     */
    public void userMove(int fromRow, int fromColumn, int toRow, int toColumn) {
        move(fromRow, fromColumn, toRow, toColumn);
    }

    public void wake() {
    }

    public void init(Controller controller) {
        this.controller = controller;
    }

    public void userMovePromoting(int fromRow, int fromCol, int toRow, int toCol, PieceKind chosenKind) {

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
