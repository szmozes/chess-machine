package machine;

public class Piece {
    Field field;
    final PieceEnum kind;
    final Color color;

    public Piece(Field field, PieceEnum kind, Color color) {
        this.field = field;
        this.kind = kind;
        this.color = color;
    }

    public Piece(PieceEnum kind, Color color) {
        this.kind = kind;
        this.field = new Field(null, 0, 0);
        this.color = color;
    }

    public Piece(Field field, Color color) {
        this.field = field;
        this.kind = PieceEnum.PAWN;
        this.color = color;
    }

    public void write() {
        System.out.print(this.getClass().toString().subSequence(14, 16));
    }

    public double getValue() {
        return 0;
    }
}
