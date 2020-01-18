package machine;

import java.util.ArrayList;

/**
 * ___________________	___________________
 * |\|0|1|2|3|4|5|6|7|	|8|_|_|_|_|_|_|_|_|
 * |0|_|_|_|_|_|_|_|_|	|7|_|_|_|_|_|_|_|_|
 * |1|_|_|_|_|_|_|_|_|	|6|_|_|_|_|_|_|_|_|
 * |2|_|_|_|_|_|_|_|_|	|5|_|_|_|_|_|_|_|_|
 * |3|_|_|_|_|_|_|_|_|	|4|_|_|_|_|_|_|_|_|
 * |4|_|_|_|_|_|_|_|_|	|3|_|_|_|_|_|_|_|_|
 * |5|_|_|_|_|_|_|_|_|	|2|_|_|_|_|_|_|_|_|
 * |6|_|_|_|_|_|_|_|_|	|1|_|_|_|_|_|_|_|_|
 * |7|_|_|_|_|_|_|_|_|	|/|a|b|c|d|e|f|g|h|
 * 
 * @author Samsung
 *
 */

public class Table {
	
	Field[][] fields;
	int height, width;
	
	public Table(int height, int width) {
		this.height = height;
		this.width = width;
		fields = new Field[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				fields[i][j] = new Field(this, i, j);
			}
		}
	}
	
	public void standardLineUp(){
		placePiece(new Rook(null, Color.WHITE), 7, 0);
		placePiece(new Knight(null, Color.WHITE), 7, 1);
		placePiece(new Bishop(null, Color.WHITE), 7, 2);
		placePiece(new Queen(null, Color.WHITE), 7, 3);
		placePiece(new King(null, Color.WHITE), 7, 4);
		placePiece(new Bishop(null, Color.WHITE), 7, 5);
		placePiece(new Knight(null, Color.WHITE), 7, 6);
		placePiece(new Rook(null, Color.WHITE), 7, 7);
		placePiece(new Rook(null, Color.BLACK), 0, 0);
		placePiece(new Knight(null, Color.BLACK), 0, 1);
		placePiece(new Bishop(null, Color.BLACK), 0, 2);
		placePiece(new Queen(null, Color.BLACK), 0, 3);
		placePiece(new King(null, Color.BLACK), 0, 4);
		placePiece(new Bishop(null, Color.BLACK), 0, 5);
		placePiece(new Knight(null, Color.BLACK), 0, 6);
		placePiece(new Rook(null, Color.BLACK), 0, 7);
		for(int i = 0; i < 8; i++) {
			placePiece(new Pawn(null, Color.WHITE), 6, i);
			placePiece(new Pawn(null, Color.BLACK), 1, i);
		}
	}
	
	public void placePiece(Piece piece, int row, int column) {
		fields[row][column].setPiece(piece);
	}
	
	public Color getPieceColor(int row, int column) {
		if(row < 0 || row >= height || column < 0 || column >= width) {
			return null;
		}
		Piece piece = fields[row][column].getPiece();
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
				} else {
					System.out.print("  ");
				}
				System.out.print("  ");
			}
			System.out.println();
		}
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

	public void setChoosingState(Piece grabbed) {
		System.out.println("in the restoring: " + grabbed);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				fields[i][j].steppable = false;
			}
		}
		grabbed.grabbed = false;
		grabbed = null;
		System.out.println("in the restoring, after the null-ing: " + grabbed);
	}
}
