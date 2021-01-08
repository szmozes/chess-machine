package machine;

import java.awt.*;
import java.awt.Color;

// draw menu items and return the serial number of the selected item
public class RightSideMenu {
    View view;
    int widthInBoardSquare = 4;     // Width  of the menu = 4 * squareSize (4 chessboard square)
    int buttonCount = 4;     // forward, backward, fast-forward, fast-backward
    int x0, y0;             // menu top-left coordinates
    ControlButton buttonFastBackward;
    ControlButton buttonBackward;
    ControlButton buttonForward;
    ControlButton buttonFastForward;

    public RightSideMenu(View view) {
        this.view = view;
        buttonFastBackward = new ControlButton();
        buttonBackward = new ControlButton();
        buttonForward = new ControlButton();
        buttonFastForward = new ControlButton();
    }

    public ControlButtonType getButtonType(int xPixel, int yPixel) {
        if (buttonFastBackward.isButtonClicked(xPixel, yPixel)) return buttonFastBackward.type;
        if (buttonBackward.isButtonClicked(xPixel, yPixel)) return buttonBackward.type;
        if (buttonForward.isButtonClicked(xPixel, yPixel)) return buttonForward.type;
        if (buttonFastForward.isButtonClicked(xPixel, yPixel)) return buttonFastForward.type;

        return null;
    }

    public void paint() {
        this.x0 = (int) (view.size * 8.5);
        this.y0 = (int) (view.size * 3.75);
        paintButtons(view.g);
    }

    private void paintButtons(Graphics g) {
        int allButtonWidth = view.size * (widthInBoardSquare - 1);
        int buttonWidth = allButtonWidth / buttonCount;
        int buttonHeight = view.size / 2;
        g.setColor(Color.BLACK);

        buttonFastBackward.init(this.x0, this.y0, buttonWidth, buttonHeight, ControlButtonType.FAST_BACKWARD);
        paintButtonBackground(g, buttonWidth, buttonHeight, this.x0);
        buttonFastBackward.paint(g);

        buttonBackward.init(this.x0 + buttonWidth, this.y0, buttonWidth, buttonHeight, ControlButtonType.BACKWARD);
        paintButtonBackground(g, buttonWidth, buttonHeight, this.x0 + buttonWidth);
        buttonBackward.paint(g);

        buttonForward.init(this.x0 + 2 * buttonWidth, this.y0, buttonWidth, buttonHeight, ControlButtonType.FORWARD);
        paintButtonBackground(g, buttonWidth, buttonHeight, this.x0 + 2 * buttonWidth);
        buttonForward.paint(g);

        buttonFastForward.init(this.x0 + 3 * buttonWidth, this.y0, buttonWidth, buttonHeight, ControlButtonType.FAST_FORWARD);
        paintButtonBackground(g, buttonWidth, buttonHeight, this.x0 + 3 * buttonWidth);
        buttonFastForward.paint(g);
    }

    private void paintButtonBackground(Graphics g, int buttonWidth, int buttonHeight, int x0) {
        g.setColor(new Color(225, 225, 170));
        g.fillRect(x0, this.y0, buttonWidth, buttonHeight);
    }
}

class ControlButton {
    int x0, y0;         // top-left coordinates
    int buttonWidth, buttonHeight;
    ControlButtonType type;

    public ControlButton() {
    }

    public void init(int x0, int y0, int buttonWidth, int buttonHeight, ControlButtonType controlButtonType) {
        this.x0 = x0;
        this.y0 = y0;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.type = controlButtonType;
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

    public void paint(Graphics g) {
        switch (type) {
            case FAST_BACKWARD:
                drawLineOnButton(g, 19, 30, 19, 70);
                drawLineOnButton(g, 20, 30, 20, 70);
                drawLineOnButton(g, 21, 30, 21, 70);
                drawTriangleOnButton(g, 20, 50, 50, 30, 50, 70);
                drawTriangleOnButton(g, 50, 50, 80, 30, 80, 70);
                break;
            case BACKWARD:
                drawTriangleOnButton(g, 35, 50, 65, 30, 65, 70);
                break;
            case FORWARD:
                drawTriangleOnButton(g, 35, 30, 65, 50, 35, 70);
                break;
            case FAST_FORWARD:
                drawLineOnButton(g, 80, 30, 80, 70);
                drawLineOnButton(g, 81, 30, 81, 70);
                drawLineOnButton(g, 82, 30, 82, 70);
                drawLineOnButton(g, 83, 30, 83, 70);
                drawTriangleOnButton(g, 20, 30, 50, 50, 20, 70);
                drawTriangleOnButton(g, 50, 30, 80, 50, 50, 70);
                break;
        }
    }

    private int convertCoordinate(int percent, int translation, int scale) {
        double retValue;
        retValue = percent / 100.0;
        retValue *= scale;
        retValue += translation;
        return (int) (retValue);
    }

    private void drawLineOnButton(Graphics g, int x1, int y1, int x2, int y2) {
        g.setColor(Color.BLACK);
        x1 = convertCoordinate(x1, this.x0, this.buttonWidth);
        y1 = convertCoordinate(y1, this.y0, this.buttonHeight);
        x2 = convertCoordinate(x2, this.x0, this.buttonWidth);
        y2 = convertCoordinate(y2, this.y0, this.buttonHeight);
        g.drawLine(x1, y1, x2, y2);
    }

    private void drawTriangleOnButton(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3) {
        g.setColor(Color.BLACK);
        x1 = convertCoordinate(x1, this.x0, this.buttonWidth);
        y1 = convertCoordinate(y1, this.y0, this.buttonHeight);
        x2 = convertCoordinate(x2, this.x0, this.buttonWidth);
        y2 = convertCoordinate(y2, this.y0, this.buttonHeight);
        x3 = convertCoordinate(x3, this.x0, this.buttonWidth);
        y3 = convertCoordinate(y3, this.y0, this.buttonHeight);
        int[] x = {x1, x2, x3};
        int[] y = {y1, y2, y3};
        g.fillPolygon(x, y, 3);

        g.setColor(new Color(0, 0, 0));
        g.drawPolygon(x, y, 3);

    }
}

enum ControlButtonType {
    FAST_BACKWARD,
    BACKWARD,
    FORWARD,
    FAST_FORWARD
}
