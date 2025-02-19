package game;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.GridLayout;

public class BoardGUI extends JFrame {
    private final int TILE_SIZE = 40;
    private int remainingTiles;
    private Board board;
    private JButton[][] buttons;

    public BoardGUI(Board board) {
        this.board = board;
        buttons = new JButton[board.getSize()][board.getSize()];
        // Initialize GUI components and layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(board.getSize(), board.getSize(), 0, 0));
        int numTilesX = board.getSize();
        setSize(TILE_SIZE*numTilesX+20, TILE_SIZE*numTilesX+20);
        remainingTiles = board.getSize() * board.getSize() - board.getNumBombs();
        updateRemainingTiles();

        // Add components to the frame
        addButtons();

        setVisible(true);
    }

    private void updateRemainingTiles() {
        setTitle("Minesweeper: " + remainingTiles + " Tiles Remain");
        if (remainingTiles <= 0) {
            JOptionPane.showOptionDialog(this, "You won!\nWant to play again?", "You won!", JOptionPane.YES_NO_OPTION, 1, null, null, null);
        }

    }

    private void addButtons() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                final int x = i;
                final int y = j;
                JButton newButton = new JButton();
                newButton.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                newButton.addActionListener(e -> {
                    pressButton(x, y);
                });
                buttons[i][j] = newButton;
                add(newButton);
            }
        }
    }

    private void pressButton(int x, int y) {
        JButton button = buttons[x][y];
        int tileValue = board.evaluateTile(x, y);
        if (tileValue == -1) {
            JOptionPane.showOptionDialog(this, "You blew up!\nWant to play again?", "You died!", JOptionPane.YES_NO_OPTION, 3, null, null, null);
        }
        remainingTiles--;
        if (tileValue == 0) {
            // if there are no bombs around, activate those tiles too
            for (int offsetX = -1; offsetX <= 1; offsetX++) {
                for (int offsetY = -1; offsetY <= 1; offsetY++) {
                    int newX = x + offsetX;
                    int newY = y + offsetY;
                    if (newX >= 0 && newX < board.getSize() && newY >= 0 && newY < board.getSize() && buttons[newX][newY].getText().equals("")) {
                        button.setText("0");
                        pressButton(newX, newY);
                    }
                }
            }
        } else {
            button.setText("" + tileValue);
        }
        updateRemainingTiles();
    }
}
