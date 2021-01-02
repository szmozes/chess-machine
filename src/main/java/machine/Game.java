package machine;

public abstract class Game {
    Color whoTurns;
    Table table;

    public Game() {
        table = new Table(8, 8);
        whoTurns = Color.WHITE;
    }

    public abstract boolean move(int fromRow, int fromColumn, int toRow, int toColumn);

    public void switchWhoTurns() {
        switch (whoTurns) {
            case WHITE:
                whoTurns = Color.BLACK;
                break;
            case BLACK:
                whoTurns = Color.WHITE;
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

    void setOpportunities(int row, int column) {

    }

}
