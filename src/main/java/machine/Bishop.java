package machine;

public class Bishop extends Piece {

    public Bishop(Field field, Color color) {
        super(field, PieceEnum.BISHOP, color);
    }

    public double getValue() {
        return 3;
    }
}
