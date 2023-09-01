package io.szmozes.chessmachine;

public class TestWriter {
    static boolean isWriting = true;

    static void writeTestTitle(String title) {
        if (isWriting) {
            System.out.println("\n-- Test: " + title + " --");
        }
    }

    static void writeTable(Table table) {
        if (isWriting) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (table.fields[i][j] != null) {
                        writePieceShortName(table.fields[i][j]);
                        System.out.print("|");
                    } else {
                        System.out.print("_|");
                    }
                }
                System.out.println();
            }
            System.out.println("who turns: " + table.whoTurns);
            System.out.print("castles: ");
            if (table.bq) System.out.print("bq ");
            if (table.bk) System.out.print("bk ");
            if (table.wq) System.out.print("wq ");
            if (table.wk) System.out.print("wk ");
            System.out.println();
        }
    }

    static void writeString(String string) {
        if (isWriting) {
            System.out.println(string);
        }
    }

    private static void writePieceShortName(Piece piece) {
        String name;
        switch (piece.kind) {
            case BISHOP:
                name = (piece.color == Color.BLACK) ? "b" : "B";
                break;
            case KING:
                name = (piece.color == Color.BLACK) ? "k" : "K";
                break;
            case KNIGHT:
                name = (piece.color == Color.BLACK) ? "n" : "N";
                break;
            case PAWN:
                name = (piece.color == Color.BLACK) ? "p" : "P";
                break;
            case QUEEN:
                name = (piece.color == Color.BLACK) ? "q" : "Q";
                break;
            case ROOK:
                name = (piece.color == Color.BLACK) ? "r" : "R";
                break;
            default:
                name = "";
                break;
        }
        System.out.print(name);
    }
}
