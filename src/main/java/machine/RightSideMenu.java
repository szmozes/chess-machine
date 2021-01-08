package machine;

import java.awt.*;

// draw menu items and return the serial number of the selected item
public class RightSideMenu {
    int widthInBoardSquare = 4;     // Width  of the menu = 4 * squareSize (4 chessboard square)
    int squareSize;         // Size of one chessboard square in pixels
    int buttonCount = 4;     // forward, backward, fast-forward, fast-backward
    int x0, y0;             // menu top-left coordinates
    ControlButton buttonFastBackward;
    ControlButton buttonBackward;
    ControlButton buttonForward;
    ControlButton buttonFastForward;

    public RightSideMenu() {
        buttonFastBackward = new ControlButton();
        buttonBackward = new ControlButton();
        buttonForward = new ControlButton();
        buttonFastForward = new ControlButton();
    }

    public int calculateSquareSize(int panelWidth, int panelHeight) {
        // panelWidth, panelHeight: size of the main panel in pixels
        int xSize, ySize; // max size of a chessboard square in pixels
        xSize = panelWidth / (8 + widthInBoardSquare); // 8 chessboard square + 4 space width for the menu
        ySize = panelHeight / 8;
        this.squareSize = Math.min(xSize, ySize);  // squareSize: size of a chessboard square

        return this.squareSize;
    }

    public ControlButtonType getButtonType(int xPixel, int yPixel) {
        if (buttonFastBackward.isButtonClicked(xPixel, yPixel)) return buttonFastBackward.type;
        if (buttonBackward.isButtonClicked(xPixel, yPixel)) return buttonBackward.type;
        if (buttonForward.isButtonClicked(xPixel, yPixel)) return buttonForward.type;
        if (buttonFastForward.isButtonClicked(xPixel, yPixel)) return buttonFastForward.type;

        return null;
    }

    public int convertCoordinate(int x1, int x0, int xSize) {
        double xRatio = xSize / 100.0;
        double x;
        x = x1;
        x *= xRatio;
        x += x0;
        return (int) (x);
    }

    public void initAndPaintButton(Graphics g, ControlButton button1, int x0, int y0, int buttonWidth, int buttonHeight, ControlButtonType controlButtonType) {
        initButton(button1, x0, y0, buttonWidth, buttonHeight, controlButtonType);
        g.setColor(new java.awt.Color(225, 225, 170));
        g.fillRect(x0, y0, buttonWidth, buttonHeight);
    }

    private void initButton(ControlButton button1, int x0, int y0, int buttonWidth, int buttonHeight, ControlButtonType controlButtonType) {
        button1.x0 = x0;
        button1.y0 = y0;
        button1.buttonWidth = buttonWidth;
        button1.buttonHeight = buttonHeight;
        button1.type = controlButtonType;
    }

    public void paintButtons(Graphics g) {
        int allButtonWidth = squareSize * (widthInBoardSquare - 1);
        int buttonWidth = allButtonWidth / buttonCount;
        int buttonHeight = squareSize / 2;
        g.setColor(java.awt.Color.BLACK);

        initAndPaintButton(g, buttonFastBackward, this.x0, this.y0, buttonWidth, buttonHeight, ControlButtonType.FAST_BACKWARD);
        drawLineButton(g, buttonFastBackward, 19, 30, 19, 70);
        drawLineButton(g, buttonFastBackward, 20, 30, 20, 70);
        drawLineButton(g, buttonFastBackward, 21, 30, 21, 70);
        drawTriangle(g, buttonFastBackward, 20, 50, 50, 30, 50, 70);
        drawLineButton(g, buttonFastBackward, 50, 50, 80, 30);
        drawLineButton(g, buttonFastBackward, 50, 50, 80, 70);
        drawTriangle(g, buttonFastBackward, 50, 50, 80, 30, 80, 70);

        initAndPaintButton(g, buttonBackward, this.x0 + buttonWidth, this.y0, buttonWidth, buttonHeight, ControlButtonType.BACKWARD);
        drawTriangle(g, buttonBackward, 35, 50, 65, 30, 65, 70);

        initAndPaintButton(g, buttonForward, this.x0 + 2 * buttonWidth, this.y0, buttonWidth, buttonHeight, ControlButtonType.FORWARD);
        drawTriangle(g, buttonForward, 35, 30, 65, 50, 35, 70);

        initAndPaintButton(g, buttonFastForward, this.x0 + 3 * buttonWidth, this.y0, buttonWidth, buttonHeight, ControlButtonType.FAST_FORWARD);
        drawLineButton(g, buttonFastForward, 80, 30, 80, 70);
        drawLineButton(g, buttonFastForward, 81, 30, 81, 70);
        drawLineButton(g, buttonFastForward, 82, 30, 82, 70);
        drawLineButton(g, buttonFastForward, 83, 30, 83, 70);
        drawTriangle(g, buttonFastForward, 20, 30, 50, 50, 20, 70);
        drawTriangle(g, buttonFastForward, 50, 30, 80, 50, 50, 70);
    }

    public void paint(Graphics g) {
        this.x0 = (int) (squareSize * 8.5);
        this.y0 = (int) (squareSize * 3.75);
        paintButtons(g);
    }

    private void drawLineButton(Graphics g, ControlButton lineButton, int x1, int y1, int x2, int y2) {
        g.setColor(java.awt.Color.BLACK);
        x1 = convertCoordinate(x1, lineButton.x0, lineButton.buttonWidth);
        y1 = convertCoordinate(y1, lineButton.y0, lineButton.buttonHeight);
        x2 = convertCoordinate(x2, lineButton.x0, lineButton.buttonWidth);
        y2 = convertCoordinate(y2, lineButton.y0, lineButton.buttonHeight);
        g.drawLine(x1, y1, x2, y2);
    }

    private void drawTriangle(Graphics g, ControlButton lineButton, int x1, int y1, int x2, int y2, int x3, int y3) {
        g.setColor(java.awt.Color.BLACK);
        x1 = convertCoordinate(x1, lineButton.x0, lineButton.buttonWidth);
        y1 = convertCoordinate(y1, lineButton.y0, lineButton.buttonHeight);
        x2 = convertCoordinate(x2, lineButton.x0, lineButton.buttonWidth);
        y2 = convertCoordinate(y2, lineButton.y0, lineButton.buttonHeight);
        x3 = convertCoordinate(x3, lineButton.x0, lineButton.buttonWidth);
        y3 = convertCoordinate(y3, lineButton.y0, lineButton.buttonHeight);
        int[] x = {x1, x2, x3};
        int[] y = {y1, y2, y3};
        g.fillPolygon(x, y, 3);

        g.setColor(new java.awt.Color(0, 0, 0));
        g.drawPolygon(x, y, 3);

    }
}

class ControlButton {
    int x0, y0;         // top-left coordinates
    int buttonWidth, buttonHeight;
    ControlButtonType type;

    public ControlButton() {
    }

    public ControlButton(int x0, int y0, int buttonWidth, int buttonHeight, ControlButtonType type) {
        this.x0 = x0;
        this.y0 = y0;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.type = type;
    }

    /**
     * check whether mouse click is between button coordinates
     */
    public boolean isButtonClicked(int xPixel, int yPixel) {
        return (this.x0 <= xPixel) &&
                (xPixel <= (this.x0 + this.buttonWidth)) &&
                (this.y0 <= yPixel) &&
                (yPixel <= (this.y0 + this.buttonHeight));
    }
}

enum ControlButtonType {
    FAST_BACKWARD,
    BACKWARD,
    FORWARD,
    FAST_FORWARD
}
