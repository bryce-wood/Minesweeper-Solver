package runnable;

import game.Board;
import game.BoardGUI;

public class Play {
    public static void main(String[] args) {
        Board exampleBoard = new Board(5, 5);
        exampleBoard.printCompleteBoard();

        BoardGUI gui = new BoardGUI(exampleBoard);
    }
}
