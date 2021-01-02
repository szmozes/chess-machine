package machine;

public class TestWriter {
    static boolean isWriting = false;

    static void writeTestTitle(String title) {
        if (isWriting) {
            System.out.println("\n-- Test: " + title + " --");
        }
    }

    static void writeTable(Table table) {
        if (isWriting) {
            for (int i = 0; i < table.height; i++) {
                for (int j = 0; j < table.width; j++) {
                    if (table.fields[i][j].piece != null) {
                        writePieceShortName(table.fields[i][j].piece);
                        System.out.print("|");
                    } else {
                        System.out.print("__|");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    static void writeString(String string) {
        if (isWriting) {
            System.out.println(string);
        }
    }

    private static void writePieceShortName(Piece piece) {
        System.out.print(piece.getClass().getSimpleName().subSequence(0, 2));
    }
}
