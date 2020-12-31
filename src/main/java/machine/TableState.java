package machine;

public class TableState {

	Piece[][] fields;
	Color whoTurns;
	boolean bq, bk, wq, wk;	// the players can castle

	public TableState() {
		fields = new Piece[8][];
		for(int i = 0; i < 8; i++) {
			fields[i] = new Piece[8];
		}
		standardLineUp();
		whoTurns = Color.WHITE;
		bq = bk = wq = wk = true;
	}
	
	public TableState(TableState ref) {
		fields = new Piece[8][];
		for(int i = 0; i < 8; i++) {
			fields[i] = new Piece[8];
			for(int j = 0; j < 8; j++) {
				fields[i][j] = ref.fields[i][j];
			}
		}
		whoTurns = ref.whoTurns;
		bq = ref.bq;
		bk = ref.bk;
		wq = ref.wq;
		wk = ref.wq;
	}
	
	public void standardLineUp() {
		fields[0][0] = new Piece(PieceEnum.ROOK, Color.BLACK);
		fields[0][1] = new Piece(PieceEnum.KNIGHT, Color.BLACK);
		fields[0][2] = new Piece(PieceEnum.BISHOP, Color.BLACK);
		fields[0][3] = new Piece(PieceEnum.QUEEN, Color.BLACK);
		fields[0][4] = new Piece(PieceEnum.KING, Color.BLACK);
		fields[0][5] = new Piece(PieceEnum.BISHOP, Color.BLACK);
		fields[0][6] = new Piece(PieceEnum.KNIGHT, Color.BLACK);
		fields[0][7] = new Piece(PieceEnum.ROOK, Color.BLACK);
		fields[7][0] = new Piece(PieceEnum.ROOK, Color.WHITE);
		fields[7][1] = new Piece(PieceEnum.KNIGHT, Color.WHITE);
		fields[7][2] = new Piece(PieceEnum.BISHOP, Color.WHITE);
		fields[7][3] = new Piece(PieceEnum.QUEEN, Color.WHITE);
		fields[7][4] = new Piece(PieceEnum.KING, Color.WHITE);
		fields[7][5] = new Piece(PieceEnum.BISHOP, Color.WHITE);
		fields[7][6] = new Piece(PieceEnum.KNIGHT, Color.WHITE);
		fields[7][7] = new Piece(PieceEnum.ROOK, Color.WHITE);
		for(int i = 0; i < 8; i++) {
			fields[1][i] = new Piece(PieceEnum.PAWN, Color.BLACK);
			fields[6][i] = new Piece(PieceEnum.PAWN, Color.WHITE);
		}
	}
	
	// for test purposes
	public void print() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(fields[i][j] != null) {
					switch(fields[i][j].kind) {
					case PAWN: System.out.print(fields[i][j].color == Color.WHITE ? "P" : "p"); break;
					case ROOK: System.out.print(fields[i][j].color == Color.WHITE ? "r" : "R"); break;
					case KNIGHT: System.out.print(fields[i][j].color == Color.WHITE ? "n" : "N"); break;
					case BISHOP: System.out.print(fields[i][j].color == Color.WHITE ? "b" : "B"); break;
					case QUEEN: System.out.print(fields[i][j].color == Color.WHITE ? "q" : "Q"); break;
					case KING: System.out.print(fields[i][j].color == Color.WHITE ? "k" : "K"); break;
					}
					System.out.print("|");
				} else {
					System.out.print("_|");
				}
			}
			System.out.println();
		}
		System.out.println("who turns: " + whoTurns);
		System.out.print("castles: ");
		if(bq) System.out.print("bq ");
		if(bk) System.out.print("bk ");
		if(wq) System.out.print("wq ");
		if(wk) System.out.print("wk ");
		System.out.println();
	}
}
