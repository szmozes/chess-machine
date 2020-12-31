package machine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View extends JPanel{
	
	private Game game;
	private Table table;
	private Graphics g;
	private int size;				// length of a field in pixels
	private int menuWidth;		    /** menu width (at the right of the board) */
	private MenuItems rightMenu;	/** menu at the right of the chessboard    */
	private MenuHandler handleMenu; /** execute the clicked menu item		   */

	private BufferedImage black_bishop;
	private BufferedImage black_king;
	private BufferedImage black_knight;
	private BufferedImage black_pawn;
	private BufferedImage black_queen;
	private BufferedImage black_rook;
	private BufferedImage white_bishop;
	private BufferedImage white_king;
	private BufferedImage white_knight;
	private BufferedImage white_pawn;
	private BufferedImage white_queen;
	private BufferedImage white_rook;
	
	public View(Game game) {
		this.game = game;
		table = game.table;
		size = 60;	// initial value of 'size'
		rightMenu = new MenuItems();    /** menu at the right of the chessboard */
		handleMenu = new MenuHandler(); /** execute the clicked menu item		*/
		menuWidth = rightMenu.getMenuWidth( size ); /** menu width */
		setPreferredSize(new Dimension(8*size+menuWidth, 8*size));
	}
	
	public void init() {

		String imageDirectory = "images";

		try {
			black_bishop = ImageIO.read(new File(imageDirectory, "black_bishop.png"));
			black_king = ImageIO.read(new File(imageDirectory, "black_king.png"));
			black_knight = ImageIO.read(new File(imageDirectory, "black_knight.png"));
			black_pawn = ImageIO.read(new File(imageDirectory, "black_pawn.png"));
			black_queen = ImageIO.read(new File(imageDirectory, "black_queen.png"));
			black_rook = ImageIO.read(new File(imageDirectory, "black_rook.png"));
			white_bishop = ImageIO.read(new File(imageDirectory, "white_bishop.png"));
			white_king = ImageIO.read(new File(imageDirectory, "white_king.png"));
			white_knight = ImageIO.read(new File(imageDirectory, "white_knight.png"));
			white_pawn = ImageIO.read(new File(imageDirectory, "white_pawn.png"));
			white_queen = ImageIO.read(new File(imageDirectory, "white_queen.png"));
			white_rook = ImageIO.read(new File(imageDirectory, "white_rook.png"));

		} catch (IOException e) {
			System.out.println("valamelyik kepet nem lehetett betolteni");
		}
		
		JFrame frame = new JFrame();
		
	}
	
}
