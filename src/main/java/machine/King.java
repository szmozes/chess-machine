package machine;

public class King extends Piece {

    public King(Field field, Color color) {
        super(field, PieceEnum.KING, color);
    }

    public double getValue() {
        return 50;
    }
}
