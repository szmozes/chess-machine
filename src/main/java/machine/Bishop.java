package machine;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(PieceEnum.BISHOP, color);
    }

    public double getValue() {
        return 3;
    }
}
