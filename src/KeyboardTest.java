import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class KeyboardTest {
    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Game game = new Game();

        Display.draw(game);

        int col = askForColumn();
        game.dropCoin(col, Game.RED);

        Display.draw(game);

        col = randomMove();
        game.dropCoin(col, Game.BLUE);

        Display.draw(game);

        col = askForColumn();
        game.dropCoin(col, Game.RED);

        Node node = MinMax.calculateTree(game, Game.BLUE, 0);

        while (true) {
            printMoves(node);
            if (node.getBestMove() == -1) {
                System.out.println("Draw!");
                break;
            } else {
                int bestMove = node.getBestMove();
                if (node.getMove(bestMove).getMinMaxScore() == 0) {
                    bestMove = randomDrawMove(node.getMoves());
                }
                if (game.dropCoin(bestMove, Game.BLUE)) {
                    Display.draw(game);
                    System.out.println("I win! You lose!");
                    break;
                } else {
                    Display.draw(game);
                }
            }

            col = askForColumn();
            if (game.isColumnFull(col)) {
                System.out.println("Column " + col + " is full");
            } else {
                if (game.dropCoin(col, Game.RED)) {
                    Display.draw(game);
                    System.out.println("You win!");
                    break;
                }
                node = MinMax.calculateTree(game, Game.BLUE, 0);
            }
        }
    }

    private static int randomDrawMove(Node[] moves) {
        ArrayList<Integer> drawMoves = new ArrayList<Integer>();
        for (int c = 0; c < moves.length; c++) {
            if (moves[c].getMinMaxScore() == 0) {
                drawMoves.add(c);
            }
        }
        return drawMoves.get(random.nextInt(drawMoves.size()));
    }

    private static int randomMove() {
        return random.nextInt(5) + 1;
    }

    private static void printMoves(Node node) {
        System.out.println("Best move: " + node.getBestMove());
        for (int c = 0; c < Game.COLUMNS; c++) {
            Node move = node.getMove(c);
            if (move != null) {
                System.out.println(String.valueOf(c) + ": minMax: " + move.getMinMaxScore() + ", depth: " + node.getDepth());
            } else {
                System.out.println(String.valueOf(c) + ": no move");
            }
        }
    }

    private static int askForColumn() {
        Reader br = new InputStreamReader(System.in); //Here you declare your BufferedReader object and instance it.
        while (true) {
            System.out.print("You move: ");
            try {
                int ch = br.read();
                if (!Character.isDigit(ch)) {
                    System.out.println("\nPlease enter digin 0-6\n");
                } else {
                    int col = ch - 48;
                    if (col > 6) {
                        System.out.println("\nPlease enter digin 0-6\n");
                    } else {
                        System.out.println("");
                        return col;
                    }
                }
            } catch (Exception e) {
                System.out.println("\nError");
            }
        }
    }
}
