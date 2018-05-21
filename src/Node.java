public class Node {
    private int minMaxScore;
    private int depth;
    private int bestMove = -1;
    private Node[] moves;

    public Node(int score, int depth) {
        this.minMaxScore = score;
        this.depth = depth;
    }

    public Node() {
    }

    public int getMinMaxScore() {
        return minMaxScore;
    }

    public int getDepth() {
        return depth;
    }

    public int getBestMove() {
        return bestMove;
    }

    public void calculateBestMove(Node[] moves, boolean isDraw, int branchDepth, int currentColor) {
        if (isDraw) {
            this.minMaxScore = 0;
            this.depth = branchDepth;
        } else {
            if (currentColor == Game.RED) {
                this.minMaxScore = 20;
            } else {
                this.minMaxScore = -20;
            }
            for (int col = 0; col < Game.COLUMNS; col++) {
                if (moves[col] != null) {
                    if (currentColor == Game.RED) {
                        if (this.minMaxScore > moves[col].getMinMaxScore()) {
                            this.minMaxScore = moves[col].getMinMaxScore();
                            this.depth = moves[col].getDepth();
                            this.bestMove = col;
                        } else if (this.minMaxScore == moves[col].getMinMaxScore()) {
                            if (this.depth > moves[col].getDepth()) {
                                this.depth = moves[col].getDepth();
                                this.bestMove = col;
                            }
                        }
                    } else {
                        if (this.minMaxScore < moves[col].getMinMaxScore()) {
                            this.minMaxScore = moves[col].getMinMaxScore();
                            this.depth = moves[col].getDepth();
                            this.bestMove = col;
                        } else if (this.minMaxScore == moves[col].getMinMaxScore()) {
                            if (this.depth > moves[col].getDepth()) {
                                this.depth = moves[col].getDepth();
                                this.bestMove = col;
                            }
                        }
                    }
                }
            }
        }
    }

    public void setMoves(Node[] moves) {
        this.moves = moves;
    }

    public Node[] getMoves() {
        return moves;
    }

    public Node getMove(int col) {
        return moves[col];
    }
}
