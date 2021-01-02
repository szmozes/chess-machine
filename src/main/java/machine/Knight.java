package machine;

public class Knight extends Piece {

    public Knight(Color color) {
        super(PieceEnum.KNIGHT, color);
    }

    public double getValue() {
        return 3;
    }
}
