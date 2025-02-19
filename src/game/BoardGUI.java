package game;

import javax.swing.JFrame;

public class BoardGUI extends JFrame {
    private final int TILE_SIZE = 40;
    public BoardGUI(Board board) {
        // Initialize GUI components and layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int numTilesX = board.getSize();
        setSize(TILE_SIZE*numTilesX+20, TILE_SIZE*numTilesX+20);
        setTitle("Minesweeper");

        // Add components to the frame

        setVisible(true);
    }
}
