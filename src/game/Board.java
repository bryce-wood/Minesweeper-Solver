package game;

public class Board {
    private int[][] board;
    public int size;
    public int numBombs;

    public Board(int size, int numBombs) {
        // Error Handling
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
        if (numBombs <= 0 || numBombs > size*size) {
            throw new IllegalArgumentException("Number of bombs must be between 1 and size^2");
        }

        this.size = size;
        this.numBombs = numBombs;
        this.board = new int[size][size];
        generateBoard();
    }

    private void generateBoard() {
        // put bombs ('1's) in the board
        int bombsLeftToAdd = numBombs;
        int fails = 0;

        while (bombsLeftToAdd > 0 && fails < numBombs*10) {
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            if (board[x][y] != 1) {
                board[x][y] = 1;
                bombsLeftToAdd--;
            } else {
                fails++;
            }
        }

        // very very rarely called, if too many fail attempts, just fill in from start
        // technically this makes the bottom right positon on the board ever so slightly safer than the top left
        if (bombsLeftToAdd > 0) {
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    if (board[x][y] == 0) {
                        board[x][y] = 1;
                        bombsLeftToAdd--;
                        if (bombsLeftToAdd <= 0) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public String toString() {
        String returnString = "";
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                returnString += board[x][y] + " ";
            }
            returnString += "\n";
        }
        return returnString;
    }
}
