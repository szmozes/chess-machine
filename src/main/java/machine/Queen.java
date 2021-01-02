package machine;

public class Queen extends Piece {

    public Queen(Field field, Color color) {
        super(field, PieceEnum.QUEEN, color);
    }

    public double getValue() {
        return 9;
    }
}
