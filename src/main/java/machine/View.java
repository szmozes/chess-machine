package machine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View extends JPanel {
    Graphics g;
    int size;                   // length of a field in pixels
    RightSideMenu rightMenu;    // menu at the right of the chessboard
    MenuHandler handleMenu;     // execute the clicked menu item
    Controller controller;
    Table table;
    boolean[][] opportunities;

    BufferedImage blackBishopImage;
    BufferedImage blackKingImage;
    BufferedImage blackKnightImage;
    BufferedImage blackPawnImage;
    BufferedImage blackQueenImage;
    BufferedImage blackRookImage;
    BufferedImage whiteBishopImage;
    BufferedImage whiteKingImage;
    BufferedImage whiteKnightImage;
    BufferedImage whitePawnImage;
    BufferedImage whiteQueenImage;
    BufferedImage whiteRookImage;

    public View() {
        rightMenu = new RightSideMenu();
        handleMenu = new MenuHandler();
        size = 60;
        opportunities = new boolean[8][8];
        setPreferredSize(new Dimension((8 + rightMenu.widthInBoardSquare) * size, 8 * size));
    }

    public void init(Controller controller) {
        String imagePath = "images";

        try {
            blackBishopImage = ImageIO.read(new File(imagePath, "black_bishop.png"));
            blackKingImage = ImageIO.read(new File(imagePath, "black_king.png"));
            blackKnightImage = ImageIO.read(new File(imagePath, "black_knight.png"));
            blackPawnImage = ImageIO.read(new File(imagePath, "black_pawn.png"));
            blackQueenImage = ImageIO.read(new File(imagePath, "black_queen.png"));
            blackRookImage = ImageIO.read(new File(imagePath, "black_rook.png"));
            whiteBishopImage = ImageIO.read(new File(imagePath, "white_bishop.png"));
            whiteKingImage = ImageIO.read(new File(imagePath, "white_king.png"));
            whiteKnightImage = ImageIO.read(new File(imagePath, "white_knight.png"));
            whitePawnImage = ImageIO.read(new File(imagePath, "white_pawn.png"));
            whiteQueenImage = ImageIO.read(new File(imagePath, "white_queen.png"));
            whiteRookImage = ImageIO.read(new File(imagePath, "white_rook.png"));
        } catch (IOException e) {
            System.out.println("some of the images couldn't be loaded");
        }
        this.controller = controller;
        table = controller.game.table;
        for (MouseListener ml : getMouseListeners()) {
            removeMouseListener(ml);
        }
        addMouseListener(controller);
        repaint();
    }

    public PieceKind askUserForPiece() {
        Object[] pieceEnums = {PieceKind.QUEEN, PieceKind.BISHOP, PieceKind.KNIGHT, PieceKind.ROOK};
        PieceKind chosenPieceKind = (PieceKind) JOptionPane.showInputDialog(null,
                "Choose the piece you want", "Choosing piece",
                JOptionPane.QUESTION_MESSAGE, null, pieceEnums, pieceEnums[0]);
        if (chosenPieceKind == null) {
            chosenPieceKind = PieceKind.QUEEN;
        }
        return chosenPieceKind;
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.g = g;

        // recalculate the current size of a chessboard square
        size = rightMenu.calculateSquareSize(this.getWidth(), this.getHeight());
        rightMenu.paint(g);

        // background
        for (int i = 0; i < table.height; i++) {
            for (int j = 0; j < table.width; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new java.awt.Color(255, 255, 127));
                } else {
                    g.setColor(new java.awt.Color(127, 63, 0));
                }
                g.fillRect(j * size, i * size, size, size);
            }
        }
        g.setColor(java.awt.Color.BLACK);
        g.drawRect(0, 0, size * table.width - 1, size * table.height - 1);

        // pieces
        for (int i = 0; i < table.height; i++) {
            for (int j = 0; j < table.width; j++) {
                if (table.fields[i][j] != null) {
                    BufferedImage pieceImage = getImageByPiece(table.fields[i][j]);
                    this.g.drawImage(pieceImage, j * size, i * size, size, size, null);
                }
            }
        }

        // opportunity signs
        for (int i = 0; i < opportunities.length; i++) {
            for (int j = 0; j < opportunities[i].length; j++) {
                if (opportunities[i][j]) {
                    this.g.setColor(new java.awt.Color(200, 200, 255, 255));
                    this.g.fillOval(j * size + size * 3 / 8, i * size + size * 3 / 8, size / 4, size / 4);
                    this.g.setColor(new java.awt.Color(0, 0, 0, 255));
                    this.g.drawOval(j * size + size * 3 / 8, i * size + size * 3 / 8, size / 4, size / 4);
                }
            }
        }
    }

    private void newGame() {
        Object[] colors = {Color.BLACK, Color.WHITE};
        Color chosenColor = (Color) JOptionPane.showInputDialog(null,
                "Choose color you want to play with", "Choosing color",
                JOptionPane.QUESTION_MESSAGE, null, colors, colors[0]);
        if (chosenColor == null) {
            chosenColor = Color.WHITE;
        }
        GameAgainstMachine newGame = new GameAgainstMachine(Color.values()[1 - chosenColor.ordinal()]);
        controller.init(this, newGame);
        newGame.init(controller);
        init(controller);
        newGame.wake();
    }

    private BufferedImage readImage(String imagePath, String filename) {
        try {
            return ImageIO.read(new File(imagePath, filename));
        } catch (IOException e) {
            System.out.println(filename + " at " + imagePath + " couldn't be loaded");
            return null;
        }
    }

    private BufferedImage getImageByPiece(Piece piece) {
        if (piece == null) {
            return null;
        }
        switch (piece.kind) {
            case BISHOP:
                return piece.color == Color.BLACK ? blackBishopImage : whiteBishopImage;
            case KING:
                return piece.color == Color.BLACK ? blackKingImage : whiteKingImage;
            case KNIGHT:
                return piece.color == Color.BLACK ? blackKnightImage : whiteKnightImage;
            case PAWN:
                return piece.color == Color.BLACK ? blackPawnImage : whitePawnImage;
            case QUEEN:
                return piece.color == Color.BLACK ? blackQueenImage : whiteQueenImage;
            case ROOK:
                return piece.color == Color.BLACK ? blackRookImage : whiteRookImage;
            default:
                return null;
        }
    }

    private JMenuBar createMenu() {

        // menu bar
        JMenuBar menuBar = new JMenuBar();

        // first menu
        JMenu menu = new JMenu("Game");
        menuBar.add(menu);

        // first menu's items
        JMenuItem menuItem = new JMenuItem("New Game");
        menuItem.addActionListener(arg0 -> newGame());
        menu.add(menuItem);

        return menuBar;
    }

    private static class NewGameDialog extends JFrame {
        GameType gameType;
        Color machineColor;
    }

    private static void createAndShowGUI() {
        View view = new View();
        Game game = new Game();
        Controller controller = new Controller();
        controller.init(view, game);
        game.init(controller);
        view.init(controller);
        view.handleMenu.controller = controller;

        JFrame f = new JFrame("Chess Program");
        f.setJMenuBar(view.createMenu());
        f.setContentPane(view);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        createAndShowGUI();
    }
}