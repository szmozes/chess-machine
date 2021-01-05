package machine;

public class TableState {
    Piece[][] fields;
    Color whoTurns;
    boolean bq, bk, wq, wk;        // castling rights
    Position enPassantTarget;

    public TableState() {
        fields = new Piece[8][];
        for (int i = 0; i < 8; i++) {
            fields[i] = new Piece[8];
        }
        standardLineUp();
        whoTurns = Color.WHITE;
        bq = bk = wq = wk = true;
    }

    public TableState(TableState ref) {
        fields = new Piece[8][];
        for (int i = 0; i < 8; i++) {
            fields[i] = new Piece[8];
            System.arraycopy(ref.fields[i], 0, fields[i], 0, 8);
        }
        whoTurns = ref.whoTurns;
        bq = ref.bq;
        bk = ref.bk;
        wq = ref.wq;
        wk = ref.wq;
    }

    public void standardLineUp() {
        fields[0][0] = new Piece(PieceKind.ROOK, Color.BLACK);
        fields[0][1] = new Piece(PieceKind.KNIGHT, Color.BLACK);
        fields[0][2] = new Piece(PieceKind.BISHOP, Color.BLACK);
        fields[0][3] = new Piece(PieceKind.QUEEN, Color.BLACK);
        fields[0][4] = new Piece(PieceKind.KING, Color.BLACK);
        fields[0][5] = new Piece(PieceKind.BISHOP, Color.BLACK);
        fields[0][6] = new Piece(PieceKind.KNIGHT, Color.BLACK);
        fields[0][7] = new Piece(PieceKind.ROOK, Color.BLACK);
        fields[7][0] = new Piece(PieceKind.ROOK, Color.WHITE);
        fields[7][1] = new Piece(PieceKind.BISHOP, Color.WHITE);
        fields[7][2] = new Piece(PieceKind.KNIGHT, Color.WHITE);
        fields[7][3] = new Piece(PieceKind.QUEEN, Color.WHITE);
        fields[7][4] = new Piece(PieceKind.KING, Color.WHITE);
        fields[7][5] = new Piece(PieceKind.BISHOP, Color.WHITE);
        fields[7][6] = new Piece(PieceKind.KNIGHT, Color.WHITE);
        fields[7][7] = new Piece(PieceKind.ROOK, Color.WHITE);
        for (int i = 0; i < 8; i++) {
            fields[1][i] = new Piece(PieceKind.PAWN, Color.BLACK);
            fields[6][i] = new Piece(PieceKind.PAWN, Color.WHITE);
        }
    }
}
