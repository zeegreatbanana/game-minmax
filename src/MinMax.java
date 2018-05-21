public class MinMax {

    public static final int MAX_DEPTH = 10;

    private static int n = 0;

    public static Node calculateTree(Game game, int currentColor, int depth) {
        n++;
        debugOutput(game);

        Node[] moves = new Node[Game.COLUMNS];
        boolean isDraw = true;
        for (int col = 0; col < Game.COLUMNS; col++) {
            if (!game.isColumnFull(col)) {
                isDraw = false;
                if (game.dropCoin(col, currentColor)) {
                    moves[col] = new Node(getScore(currentColor), depth + 1);
//                    Display.draw(game);
//                    System.out.println("^^ WIN ^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                    game.takeCoinBack(col);
                    break;
                } else {
                    if (depth < MAX_DEPTH) {
                        moves[col] = calculateTree(game, changeColor(currentColor), depth + 1);
                    } else {
                        moves[col] = new Node(0, depth + 1);
                    }
                }
                game.takeCoinBack(col);
            }
        }
        Node node = new Node();
        node.calculateBestMove(moves, isDraw, depth, currentColor);
        if (depth == 0) {
            node.setMoves(moves);
        }
        return node;
    }

    private static void debugOutput(Game game) {
        if (n % 1_000_000 == 0) {
            Display.draw(game);
            System.out.println("n = " + n);
            System.out.println("===========================================================");

        }
    }

    private static int changeColor(int currentColor) {
        if (currentColor == Game.BLUE) {
            return Game.RED;
        } else {
            return Game.BLUE;
        }
    }

    private static int getScore(int currentColor) {
        if (currentColor == Game.BLUE) {
            return 10;
        } else {
            return -10;
        }
    }

    private static void test_1() {
        Game game = new Game();

        game.dropCoin(0, Game.RED);
        game.dropCoin(0, Game.BLUE);
        game.dropCoin(1, Game.RED);

        Node node = calculateTree(game, Game.BLUE, 0);
        System.out.println(node.toString());
    }

    public static void main(String[] args) {
        test_1();
    }
}
