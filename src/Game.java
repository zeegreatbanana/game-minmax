public class Game {
    public static final int EMPTY = 0;
    public static final int RED = 1;
    public static final int BLUE = 2;

    public static final int COLUMNS = 7;
    public static final int LAST_COLUMN = COLUMNS - 1;
    public static final int ROWS = 6;
    public static final int LAST_ROW = ROWS - 1;
    public static final int CELLS_TO_WIN = 4;

    /**
     * Board:
     *    rows:
     *        5 []
     *        4 []
     *           ......
     *        1 []
     *        0 [] [] [] ..... []
     * columns: 0  1  2        6
     */
    private int[][] cells = new int[COLUMNS][ROWS];
    private int[] emptyTops = new int[COLUMNS];

    public boolean isColumnFull(int col) {
        return emptyTops[col] == ROWS;
    }

    public void takeCoinBack(int col) {
        cells[col][emptyTops[col] - 1] = EMPTY;
        emptyTops[col]--;
    }

    /**
     * dropCoin modifies the game board by dropping a coin to a column
     * THis assumes that the column is not full
     * @param col
     * @return true if color wins after the move
     */
    public boolean dropCoin(int col, int color) {
        int row = emptyTops[col];
        cells[col][row] = color;
        emptyTops[col]++;

        // check horizontally
        // go from the new cell to the left
        int hCount = 0;
        if (col > 0) {
            // can go left
            for (int cc = col - 1; cc >= 0 && hCount < CELLS_TO_WIN - 1; cc--) {
                if (getCell(cc, row) == color) {
                    hCount++;
                } else {
                    break;
                }
            }
            if (hCount >= CELLS_TO_WIN - 1) {
                return true;
            }
        }
        // gro from the new cell to the right
        if (col < LAST_COLUMN) {
            // can go right
            for (int cc = col + 1; cc < COLUMNS && hCount < CELLS_TO_WIN - 1; cc++) {
                if (getCell(cc, row) == color) {
                    hCount++;
                } else {
                    break;
                }
            }
        }
        if (hCount >= CELLS_TO_WIN - 1) {
            return true;
        }


        // check vertically
        if (row > CELLS_TO_WIN - 2) {
            // can go down
            int vCount = 0;
            for (int rr = row - 1; rr >= 0 && vCount < 3; rr--) {
                if (getCell(col, rr) == color) {
                    vCount++;
                } else {
                    break;
                }
            }
            if (vCount >= CELLS_TO_WIN - 1) {
                return true;
            }
        }

        // check top left to bottom right
        int tlCount = 0;
        // got down
        if (col < LAST_COLUMN && row > 0) {
            for (int cc = col + 1, rr = row - 1; cc < COLUMNS && rr >= 0 && tlCount < 3; cc++, rr--) {
                if (getCell(cc, rr) == color) {
                    tlCount++;
                } else {
                    break;
                }
            }
            if (tlCount >= CELLS_TO_WIN - 1) {
                return true;
            }
        }
        // up
        if (col > 0 && row < LAST_ROW) {
            for (int cc = col - 1, rr = row + 1; cc >= 0 && rr < ROWS && tlCount < 3; cc--, rr++) {
                if (getCell(cc, rr) == color) {
                    tlCount++;
                } else {
                    break;
                }
            }
        }
        if (tlCount >= CELLS_TO_WIN - 1) {
            return true;
        }

        // check top right to botoom left
        int trCount = 0;
        // got down
        if (col > 0 && row > 0) {
            for (int cc = col - 1, rr = row - 1; cc >= 0 && rr >= 0 && trCount < 3; cc--, rr--) {
                if (getCell(cc, rr) == color) {
                    trCount++;
                } else {
                    break;
                }
            }
            if (trCount >= CELLS_TO_WIN - 1) {
                return true;
            }
        }
        // up
        if (col < LAST_COLUMN && row < LAST_ROW) {
            for (int cc = col + 1, rr = row + 1; cc < COLUMNS && rr < ROWS && trCount < 3; cc++, rr++) {
                if (getCell(cc, rr) == color) {
                    trCount++;
                } else {
                    break;
                }
            }
        }
        if (trCount >= CELLS_TO_WIN - 1) {
            return true;
        }

        return false;
    }

    public int getCell(int col, int row) {
        return cells[col][row];
    }

    public static void testDropCell_vert_1() {

        System.out.println("testDropCell_vert_1");

        Game game = new Game();
        game.cells[2][0] = 1;
        game.cells[2][1] = 2;
        game.cells[2][2] = 2;
        game.cells[2][3] = 2;

        game.emptyTops[2] = 4;

        if (!game.dropCoin(2, 2)) {
            Display.draw(game);
            throw new Error("error");
        }

        Display.draw(game);
    }

    public static void testDropCell_vert_2() {
        System.out.println("testDropCell_vert_2");

        Game game = new Game();

        if (game.dropCoin(2, 2)) {
            Display.draw(game);
            throw new Error("error");
        }

        Display.draw(game);
    }

    public static void testDropCell_vert_3() {
        System.out.println("testDropCell_vert_3");
        Game game = new Game();
        game.cells[2][0] = 1;
        game.cells[2][1] = 2;
        game.cells[2][2] = 2;

        game.emptyTops[2] = 3;

        if (game.dropCoin(2, 2)) {
            Display.draw(game);
            throw new Error("error");
        }

        Display.draw(game);
    }

    public static void testDropCell_hor_1() {
        System.out.println("testDropCell_hor_1");
        Game game = new Game();
        game.cells[2][0] = 1;
        game.cells[2][1] = 1;
        game.cells[2][2] = 2;
        game.cells[3][0] = 1;
        game.cells[3][1] = 1;
        game.cells[3][2] = 2;
        game.cells[4][0] = 1;
        game.cells[4][1] = 1;
        game.cells[5][0] = 1;
        game.cells[5][1] = 1;
        game.cells[5][2] = 2;

        game.emptyTops[2] = 3;
        game.emptyTops[3] = 3;
        game.emptyTops[4] = 2;
        game.emptyTops[5] = 3;

        if (!game.dropCoin(4, 2)) {
            Display.draw(game);
            throw new Error("error");
        }
        Display.draw(game);
    }

    public static void testDropCell_hor_2() {
        System.out.println("testDropCell_hor_2");
        Game game = new Game();
        game.cells[0][0] = 1;
        game.cells[0][1] = 1;

        game.emptyTops[0] = 2;

        if (game.dropCoin(0, 2)) {
            Display.draw(game);
            throw new Error("error");
        }
        Display.draw(game);
    }

    public static void testDropCell_hor_3() {
        System.out.println("testDropCell_hor_3");
        Game game = new Game();
        game.cells[LAST_COLUMN][0] = 1;
        game.cells[LAST_COLUMN][1] = 1;

        game.emptyTops[LAST_COLUMN] = 2;

        if (game.dropCoin(LAST_COLUMN, 2)) {
            Display.draw(game);
            throw new Error("error");
        }

        Display.draw(game);
    }

    public static void testDropCell_hor_4() {
        System.out.println("testDropCell_hor_4");
        Game game = new Game();
        game.cells[0][0] = 1;
        game.cells[0][1] = 1;
        game.cells[1][0] = 1;
        game.cells[1][1] = 1;
        game.cells[1][2] = 2;
        game.cells[2][0] = 1;
        game.cells[2][1] = 1;
        game.cells[2][2] = 2;

        game.emptyTops[0] = 2;
        game.emptyTops[1] = 3;
        game.emptyTops[2] = 3;

        if (game.dropCoin(0, 2)) {
            Display.draw(game);
            throw new Error("error");
        }
        Display.draw(game);
    }

    public static void testDropCell_hor_5() {
        System.out.println("testDropCell_hor_5");
        Game game = new Game();
        game.cells[0][0] = 1;
        game.cells[0][1] = 1;
        game.cells[1][0] = 1;
        game.cells[1][1] = 1;
        game.cells[1][2] = 2;
        game.cells[2][0] = 1;
        game.cells[2][1] = 1;
        game.cells[2][2] = 2;
        game.cells[3][0] = 1;
        game.cells[3][1] = 1;
        game.cells[3][2] = 2;

        game.emptyTops[0] = 2;
        game.emptyTops[1] = 3;
        game.emptyTops[2] = 3;
        game.emptyTops[3] = 3;

        if (!game.dropCoin(0, 2)) {
            Display.draw(game);
            throw new Error("error");
        }
        Display.draw(game);
    }

    public static void testDropCell_hor_6() {
        System.out.println("testDropCell_hor_6");
        Game game = new Game();
        game.cells[0][0] = 2;
        game.cells[1][0] = 2;
        game.cells[2][0] = 2;

        game.emptyTops[0] = 1;
        game.emptyTops[1] = 1;
        game.emptyTops[2] = 1;

        if (!game.dropCoin(3, 2)) {
            Display.draw(game);
            throw new Error("error");
        }
        Display.draw(game);
    }

    public static void testDropCell_hor_7() {
        System.out.println("testDropCell_hor_7");
        Game game = new Game();
        game.cells[0][0] = 1;
        game.cells[0][1] = 2;
        game.cells[0][2] = 1;
        game.cells[0][3] = 2;
        game.cells[0][4] = 1;
        game.cells[0][5] = 2;

        game.cells[1][0] = 1;
        game.cells[1][1] = 2;
        game.cells[1][2] = 1;
        game.cells[1][3] = 2;
        game.cells[1][4] = 1;
        game.cells[1][5] = 2;

        game.cells[2][0] = 1;
        game.cells[2][1] = 2;
        game.cells[2][2] = 1;
        game.cells[2][3] = 2;
        game.cells[2][4] = 1;
        game.cells[2][5] = 1;

        game.emptyTops[0] = 6;
        game.emptyTops[1] = 6;
        game.emptyTops[2] = 6;

        boolean isWin = game.dropCoin(3, 2);
        System.out.println("isWin: " + isWin);
        if (isWin) {
            Display.draw(game);
            throw new Error("error");
        }
        Display.draw(game);
    }

    public static void testDropCell_hor_8() {
        System.out.println("testDropCell_hor_8");
        Game game = new Game();
        game.cells[0][0] = 1;
        game.cells[0][1] = 2;
        game.cells[0][2] = 1;
        game.cells[0][3] = 2;
        game.cells[0][4] = 1;
        game.cells[0][5] = 2;

        game.cells[1][0] = 1;
        game.cells[1][1] = 2;
        game.cells[1][2] = 1;
        game.cells[1][3] = 2;
        game.cells[1][4] = 1;
        game.cells[1][5] = 2;

        game.cells[2][0] = 1;
        game.cells[2][1] = 2;
        game.cells[2][2] = 1;
        game.cells[2][3] = 2;
        game.cells[2][4] = 1;

        game.cells[3][0] = 2;

        game.emptyTops[0] = 6;
        game.emptyTops[1] = 6;
        game.emptyTops[2] = 5;
        game.emptyTops[3] = 1;

        boolean isWin = game.dropCoin(2, 1);
        System.out.println("isWin: " + isWin);
        if (isWin) {
            Display.draw(game);
            throw new Error("error");
        }
        Display.draw(game);
    }

    public static void testDropCell_diag_44() {
        System.out.println("testDropCell_diag_44");
        Game game = new Game();
        game.cells[0][0] = 1;
        game.cells[0][1] = 2;

        game.cells[1][0] = 2;
        game.cells[1][1] = 1;
        game.cells[1][2] = 2;
        game.cells[1][3] = 2;

        game.cells[2][0] = 1;
        game.cells[2][1] = 2;

        game.cells[3][0] = 1;
        game.cells[3][1] = 1;

        game.emptyTops[0] = 2;
        game.emptyTops[1] = 4;
        game.emptyTops[2] = 2;
        game.emptyTops[3] = 2;

        boolean isWin = game.dropCoin(2, 1);
        System.out.println("isWin: " + isWin);
        if (!isWin) {
            Display.draw(game);
            throw new Error("error");
        }
        Display.draw(game);
    }

    public static void main(String[] args) {
//        testDropCell_vert_1();
//        testDropCell_vert_2();
//        testDropCell_vert_3();
//        testDropCell_hor_1();
//        testDropCell_hor_2();
//        testDropCell_hor_3();
//        testDropCell_hor_4();
//        testDropCell_hor_5();
//        testDropCell_hor_6();
//        testDropCell_hor_7();
//        testDropCell_hor_8();
        testDropCell_diag_44();
        System.out.println("OK");
    }
}

