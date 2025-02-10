package game;

public class Board {
    private int[][] board;
    private int size;
    private int numBombs;

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

    public int getSize() {
        return size;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public int evaluateTile(int x, int y) {
        // Error Handling
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw new IllegalArgumentException("Invalid coordinates");
        }

        int numBombs = 0;
        // if it is a bomb, return -1
        if (board[x][y] == 1) return -1;
        // otherwise, return the number of bombs around it
        // check the 3 to the left
        if (x > 0) {
            if (board[x-1][y] == 1) numBombs++;
            if (y > 0 && board[x-1][y-1] == 1) numBombs++;
            if (y < size-1 && board[x-1][y+1] == 1) numBombs++;
        }
        // check the 3 to the right
        if (x < size-1) {
            if (board[x+1][y] == 1) numBombs++;
            if (y > 0 && board[x+1][y-1] == 1) numBombs++;
            if (y < size-1 && board[x+1][y+1] == 1) numBombs++;
        }
        // check the 1 above
        if (y > 0 && board[x][y-1] == 1) numBombs++;
        // check the 1 below
        if (y < size-1 && board[x][y+1] == 1) numBombs++;

        return numBombs;
    }

    public void printCompleteBoard() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int thisTile = evaluateTile(x, y);
                if (thisTile == -1) {
                    System.out.print("X ");
                } else {
                    System.out.print(thisTile + " ");
                }
            }
            System.out.println();
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
