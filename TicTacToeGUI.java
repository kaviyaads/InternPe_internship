import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private JLabel statusLabel;

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(Color.decode("#0c111f"));

        // Status label
        statusLabel = new JLabel("Player X's turn");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(Color.decode("#ed9e6f"));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.decode("#0c111f"));
        add(statusLabel, BorderLayout.NORTH);

        // Game board
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(3, 3));
        board.setBackground(Color.decode("#0c111f"));
        Font font = new Font("Arial", Font.BOLD, 48);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(font);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setOpaque(true);
                buttons[i][j].setContentAreaFilled(true);
                buttons[i][j].setBackground(Color.decode("#512f5c")); // purple tile
                buttons[i][j].setForeground(Color.decode("#2d1f44")); // deep bluish-purple text
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.decode("#80466e"), 2));
                buttons[i][j].addActionListener(this);
                board.add(buttons[i][j]);
            }
        }

        add(board, BorderLayout.CENTER);

        // Reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 16));
        resetButton.setBackground(Color.decode("#2d1f44"));
        resetButton.setForeground(Color.decode("#ed9e6f"));
        resetButton.setFocusPainted(false);
        resetButton.setOpaque(true);
        resetButton.setBorder(BorderFactory.createLineBorder(Color.decode("#80466e"), 1));
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (!clicked.getText().equals("")) return;

        clicked.setText(String.valueOf(currentPlayer));
        clicked.setEnabled(false);
        clicked.setForeground(Color.decode("#2d1f44"));

        if (checkWin()) {
            statusLabel.setText("Player " + currentPlayer + " wins!");
            disableBoard();
        } else if (checkDraw()) {
            statusLabel.setText("It's a draw!");
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + "'s turn");
        }
    }

    private boolean checkWin() {
        String p = String.valueOf(currentPlayer);

        // Rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(p) &&
                buttons[i][1].getText().equals(p) &&
                buttons[i][2].getText().equals(p)) {
                highlightWin(buttons[i][0], buttons[i][1], buttons[i][2]);
                return true;
            }
        }

        // Columns
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().equals(p) &&
                buttons[1][i].getText().equals(p) &&
                buttons[2][i].getText().equals(p)) {
                highlightWin(buttons[0][i], buttons[1][i], buttons[2][i]);
                return true;
            }
        }

        // Diagonals
        if (buttons[0][0].getText().equals(p) &&
            buttons[1][1].getText().equals(p) &&
            buttons[2][2].getText().equals(p)) {
            highlightWin(buttons[0][0], buttons[1][1], buttons[2][2]);
            return true;
        }

        if (buttons[0][2].getText().equals(p) &&
            buttons[1][1].getText().equals(p) &&
            buttons[2][0].getText().equals(p)) {
            highlightWin(buttons[0][2], buttons[1][1], buttons[2][0]);
            return true;
        }

        return false;
    }

    private void highlightWin(JButton b1, JButton b2, JButton b3) {
        Color winHighlight = Color.decode("#ed9e6f"); // peachy highlight
        Color winText = Color.decode("#2d1f44");      // deep purple text
        b1.setBackground(winHighlight);
        b2.setBackground(winHighlight);
        b3.setBackground(winHighlight);
        b1.setForeground(winText);
        b2.setForeground(winText);
        b3.setForeground(winText);
    }

    private boolean checkDraw() {
        for (JButton[] row : buttons) {
            for (JButton cell : row) {
                if (cell.getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableBoard() {
        for (JButton[] row : buttons) {
            for (JButton cell : row) {
                cell.setEnabled(false);
            }
        }
    }

    private void resetGame() {
        currentPlayer = 'X';
        statusLabel.setText("Player X's turn");
        for (JButton[] row : buttons) {
            for (JButton cell : row) {
                cell.setText("");
                cell.setEnabled(true);
                cell.setBackground(Color.decode("#512f5c")); // Reset tile
                cell.setForeground(Color.decode("#2d1f44")); // Reset text
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        new TicTacToeGUI();
    }
}
