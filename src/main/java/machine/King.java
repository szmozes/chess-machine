package machine;

public class King extends Piece {

    public King(Color color) {
        super(PieceEnum.KING, color);
    }

    public double getValue() {
        return 50;
    }
}
