package machine;

public abstract class Game {
    Table table;

    public Game() {
        table = new Table(8, 8);
    }

    public abstract boolean move(int fromRow, int fromColumn, int toRow, int toColumn);

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
     * @param fromRow       the row where the moved piece was
     * @param fromColumn    the column where the moved piece was
     * @param toRow         the row where the moved piece goes
     * @param toColumn      the column where the moved piece goes
     * @return              true if the pawn reached the final rank
     */
    public boolean userMove(int fromRow, int fromColumn, int toRow, int toColumn) {
        return move(fromRow, fromColumn, toRow, toColumn);
    }

    public void wake() {
    }

}
