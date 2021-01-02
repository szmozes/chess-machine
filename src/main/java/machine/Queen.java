package machine;

public class Queen extends Piece {

    public Queen(Color color) {
        super(PieceEnum.QUEEN, color);
    }

    public double getValue() {
        return 9;
    }
}
