package machine;

import java.util.ArrayList;

//	__________________	___________________
//	|\|0|1|2|3|4|5|6|7|	|8|_|_|_|_|_|_|_|_|
//	|0|_|_|_|_|_|_|_|_|	|7|_|_|_|_|_|_|_|_|
//	|1|_|_|_|_|_|_|_|_|	|6|_|_|_|_|_|_|_|_|
//	|2|_|_|_|_|_|_|_|_|	|5|_|_|_|_|_|_|_|_|
//	|3|_|_|_|_|_|_|_|_|	|4|_|_|_|_|_|_|_|_|
//	|4|_|_|_|_|_|_|_|_|	|3|_|_|_|_|_|_|_|_|
//	|5|_|_|_|_|_|_|_|_|	|2|_|_|_|_|_|_|_|_|
//	|6|_|_|_|_|_|_|_|_|	|1|_|_|_|_|_|_|_|_|
//	|7|_|_|_|_|_|_|_|_|	|/|a|b|c|d|e|f|g|h|

public class Table {
	
	Field[][] fields;
	int height, width;
	Color whoTurns;
	boolean wk, wq, bk, bq;	// how the players can castle
	
	public Table(int height, int width) {
		this.height = height;
		this.width = width;
		fields = new Field[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				fields[i][j] = new Field(this, i, j);
			}
		}
		whoTurns = Color.WHITE;
		wk = wq = bk = bq = true;
	}
	
	public Piece getPiece(int row, int column) {
		return fields[row][column].piece;
	}
	
	public void placePiece(Piece piece, int row, int column) {
		fields[row][column].setPiece(piece);
	}
	
	public Color getPieceColor(int row, int column) {
		if(row < 0 || row >= height || column < 0 || column >= width) {
			return null;
		}
		Piece piece = fields[row][column].piece;
		if(piece == null) {
			return null;
		}
		else {
			return piece.color;
		}
	}
	
	// for test purposes
	public void write() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(fields[i][j].piece != null) {
					fields[i][j].piece.write();
					System.out.print("|");
				} else {
					System.out.print("__|");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	// for test purposes
	public void steppableWrite() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				System.out.print(fields[i][j].steppable);
				System.out.print("  ");
			}
			System.out.println();
		}
	}

	public void setOpportunities(Piece grabbed) {
		ArrayList<int[]> opportunities = grabbed.opportunities();
		for(int[] i : opportunities) {
			fields[i[0]][i[1]].steppable = true;
		}
	}

	public void setChoosingState() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				fields[i][j].steppable = false;
			}
		}
	}
	
	// a primitive way to get the current state (which player has more chance to win)
	public double state() {
		double ret = 0;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				Piece piece = fields[i][j].piece;
				if(piece != null) {
					Color color = piece.color;
					switch(color) {
					case WHITE: ret += piece.getValue(); break;
					case BLACK: ret -= piece.getValue(); break;
					}
				}
			}
		}
		return ret;
	}
}
