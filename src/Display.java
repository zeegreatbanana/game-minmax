public class Display {
    public static void draw(Game game) {
        for (int row = Game.LAST_ROW; row >= 0; row--) {
            for (int cc = 0; cc < Game.COLUMNS; cc++) {
                int cell = game.getCell(cc, row);
                switch (cell) {
                    case Game.RED:
                        System.out.print("X|");
                        break;
                    case Game.BLUE:
                        System.out.print("O|");
                        break;
                    default:
                        System.out.print(" |");
                        break;
                }
            }
            System.out.println("\n--------------");
        }
        System.out.println("0 1 2 3 4 5 6");
    }
}
