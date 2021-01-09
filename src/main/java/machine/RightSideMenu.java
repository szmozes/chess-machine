package machine;

import java.awt.*;
import java.awt.Color;

public class RightSideMenu {

    static class ControlButton {
        int x0, y0;
        int buttonWidth, buttonHeight;
        ControlButtonType type;

        public ControlButton() {
        }

        public void init(int x0, int y0, int buttonWidth, int buttonHeight, ControlButtonType type) {
            this.x0 = x0;
            this.y0 = y0;
            this.buttonWidth = buttonWidth;
            this.buttonHeight = buttonHeight;
            this.type = type;
        }

        public boolean isButtonClicked(int xPixel, int yPixel) {
            return (this.x0 <= xPixel) &&
                    (xPixel <= (this.x0 + this.buttonWidth)) &&
                    (this.y0 <= yPixel) &&
                    (yPixel <= (this.y0 + this.buttonHeight));
        }

        public void paint(Graphics g) {
            g.setColor(new Color(225, 225, 170));
            g.fillRect(this.x0, this.y0, this.buttonWidth, this.buttonHeight);
            switch (type) {
                case FAST_BACKWARD:
                    drawPolygonOnButton(
                            g,
                            new int[]{19, 21, 21, 19},
                            new int[]{30, 30, 70, 70}
                    );
                    drawPolygonOnButton(
                            g,
                            new int[]{20, 50, 50},
                            new int[]{50, 30, 70}
                    );
                    drawPolygonOnButton(
                            g,
                            new int[]{50, 80, 80},
                            new int[]{50, 30, 70}
                    );
                    break;
                case BACKWARD:
                    drawPolygonOnButton(
                            g,
                            new int[]{35, 65, 65},
                            new int[]{50, 30, 70}
                    );
                    break;
                case FORWARD:
                    drawPolygonOnButton(
                            g,
                            new int[]{35, 65, 35},
                            new int[]{30, 50, 70}
                    );
                    break;
                case FAST_FORWARD:
                    drawPolygonOnButton(
                            g,
                            new int[]{80, 83, 83, 80},
                            new int[]{30, 30, 70, 70}
                    );
                    drawPolygonOnButton(
                            g,
                            new int[]{20, 50, 20},
                            new int[]{30, 50, 70}
                    );
                    drawPolygonOnButton(
                            g,
                            new int[]{50, 80, 50},
                            new int[]{30, 50, 70}
                    );
                    break;
            }
        }

        private int convertCoordinate(int percent, int scale, int translation) {
            double retValue;
            retValue = percent / 100.0;
            retValue *= scale;
            retValue += translation;
            return (int) (retValue);
        }

        private void drawLineOnButton(Graphics g, int x1, int y1, int x2, int y2) {
            x1 = convertCoordinate(x1, this.buttonWidth, this.x0);
            y1 = convertCoordinate(y1, this.buttonHeight, this.y0);
            x2 = convertCoordinate(x2, this.buttonWidth, this.x0);
            y2 = convertCoordinate(y2, this.buttonHeight, this.y0);

            g.setColor(Color.BLACK);
            g.drawLine(x1, y1, x2, y2);
        }

        private void drawPolygonOnButton(Graphics g, int[] xPoints, int[] yPoints) {
            for (int i = 0; i < xPoints.length; i++) {
                xPoints[i] = convertCoordinate(xPoints[i], this.buttonWidth, this.x0);
            }
            for (int i = 0; i < yPoints.length; i++) {
                yPoints[i] = convertCoordinate(yPoints[i], this.buttonHeight, this.y0);
            }
            g.setColor(Color.BLACK);
            g.fillPolygon(xPoints, yPoints, xPoints.length);
            g.drawPolygon(xPoints, yPoints, xPoints.length);
        }
    }

    class MenuHandler {

        public void onBtnClicked(ControlButtonType controlButtonType) {
            switch (controlButtonType) {
                case FAST_BACKWARD:
                    buttonFastBackward();
                    break;
                case BACKWARD:
                    buttonBackward();
                    break;
                case FORWARD:
                    buttonForward();
                    break;
                case FAST_FORWARD:
                    buttonFastForward();
                    break;
            }
        }

        private void buttonFastBackward() {
            System.out.println("Fast Backward button clicked");
        }

        private void buttonBackward() {
            System.out.println("Backward button clicked");
            view.controller.game.undoMove();
        }

        private void buttonForward() {
            System.out.println("Forward button clicked");
        }

        private void buttonFastForward() {
            System.out.println("Fast Forward button clicked");
        }
    }

    View view;
    int widthInBoardSquare = 4;     // Width  of the menu = 4 * squareSize (4 chessboard square)
    int buttonCount = 4;     // forward, backward, fast-forward, fast-backward
    int x0, y0;             // menu top-left coordinates
    ControlButton buttonFastBackward;
    ControlButton buttonBackward;
    ControlButton buttonForward;
    ControlButton buttonFastForward;
    MenuHandler handler;

    public RightSideMenu(View view) {
        this.view = view;
        buttonFastBackward = new ControlButton();
        buttonBackward = new ControlButton();
        buttonForward = new ControlButton();
        buttonFastForward = new ControlButton();
        handler = new MenuHandler();
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

        buttonFastBackward.init(
                this.x0,
                this.y0,
                buttonWidth,
                buttonHeight,
                ControlButtonType.FAST_BACKWARD);
        buttonFastBackward.paint(g);

        buttonBackward.init(
                this.x0 + buttonWidth,
                this.y0,
                buttonWidth,
                buttonHeight,
                ControlButtonType.BACKWARD);
        buttonBackward.paint(g);

        buttonForward.init(
                this.x0 + 2 * buttonWidth,
                this.y0,
                buttonWidth,
                buttonHeight,
                ControlButtonType.FORWARD);
        buttonForward.paint(g);

        buttonFastForward.init(
                this.x0 + 3 * buttonWidth,
                this.y0,
                buttonWidth,
                buttonHeight,
                ControlButtonType.FAST_FORWARD);
        buttonFastForward.paint(g);
    }
}
