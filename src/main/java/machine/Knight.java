package machine;

public class Knight extends Piece {

    public Knight(Field field, Color color) {
        super(field, PieceEnum.KNIGHT, color);
    }

    public double getValue() {
        return 3;
    }
}
