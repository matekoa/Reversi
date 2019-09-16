
/**
 * This is the container panel for the Board panel and the MenuPanel. The board panel contains the logic of the game and draws the graphical component of the 
 * game while, MenuPanel displays all of the important information for the players such as the player scores and the player turn. It also contains
 * Jbuttons that control the state of the game like restarting the game and switching from Player vs Player to Player vs AI. The JButtons to switch from
 * Player vs Player to Player vs AI are disabled by default and this is because the different game modes do not work and the game only has one game mode as of
 * now which is only a game for 2 players.
 * 
 * @author (Matekoa) 
 * @version (May 2016)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.*;
public class GamePanel extends JPanel
{
    private final static int EMPTY = 0;
    private final static int BLACK = 1;
    private final static int WHITE= 2;
    private final static int VALID = 3;
    //added
    private JPanel updatePanel, buttonPanel;
    protected JButton newGame, PvP, PvAI;
    private static JLabel B_ScoreDisplay, W_ScoreDisplay, player_Turn;
    private int w_Score, b_Score;//Each player starts with 2 pieces
    private String w_Turn = "White";
    private String b_Turn = "Black";
    private String PlayerTurn = "Turn: ";
    private String currentTurn;
    private Font textFont;
    private boolean done;
    //end

    private JPanel menuPanel;
    private Board board;
    int turn;
    private int row, col;

    public GamePanel()
    {
        addMouseListener(new ReversiListener());
        setPreferredSize(new Dimension(800, 646));

        menuPanel = new JPanel();
        board = new Board();
        turn = board.getTurn();
        board.lookForMove(turn);

        //added
        done = false;
        w_Score =2;//The game starts with 2 pieces from each color on the board;
        b_Score = 2;
        menuPanel.setPreferredSize(new Dimension(145, 640));
        menuPanel.setLayout(new GridLayout(2,1));

        updatePanel = new JPanel();
        updatePanel.setPreferredSize(new Dimension(200, 320));
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(200, 320));
        buttonPanel.setLayout(new GridLayout(3,1));
        updatePanel.setLayout(new GridLayout(3,1));

        newGame = new JButton("New Game");
        PvP = new JButton("vs. Player");
        PvAI = new JButton("vs. Computer");

        B_ScoreDisplay = new JLabel(b_Turn + ": " + b_Score);
        W_ScoreDisplay = new JLabel(w_Turn + ": " + w_Score);
        player_Turn = new JLabel(PlayerTurn + b_Turn);

        B_ScoreDisplay.setFont(new Font("Courier", Font.PLAIN, 25));
        W_ScoreDisplay.setFont(new Font("Courier", Font.PLAIN, 25));
        player_Turn.setFont(new Font("Courier", Font.PLAIN, 20));

        //To Add borders around the JLabels
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);

        newGame.addActionListener(new ButtonListener());

        B_ScoreDisplay.setBorder(border);
        W_ScoreDisplay.setBorder(border);
        player_Turn.setBorder(border);
        //The buttons are disabled because the game only works in one game mode at the moment.
        PvP.setEnabled(false);
        PvAI.setEnabled(false);

        buttonPanel.add(newGame);
        buttonPanel.add(PvP);
        buttonPanel.add(PvAI);
        updatePanel.add(player_Turn);
        updatePanel.add(B_ScoreDisplay);
        updatePanel.add(W_ScoreDisplay);

        //Adds the components to the panel
        menuPanel.add(buttonPanel);
        menuPanel.add(updatePanel);
        //end

        add(menuPanel);
        add(board);

    }

    public void setTurn(int t)
    {
        if(t == 1)
        {
            player_Turn.setText(PlayerTurn + b_Turn);
        }
        else if(t == 2)
        {
            player_Turn.setText(PlayerTurn + w_Turn);
        }
    }

    public void setWhiteScore(int white)
    {
        w_Score = white;
    }

    public void setBlackScore(int black)
    { 
        b_Score = black;
    }

    public void updateScore()
    {
        W_ScoreDisplay .setText(w_Turn + ": " + w_Score);
        B_ScoreDisplay .setText(b_Turn + ": " + b_Score);
    }

    public void setFinished(boolean done)
    {
        this.done = done;

        if(done)
        {
            if(w_Score > b_Score)
            {
                JOptionPane.showMessageDialog(null, "White WINS");
            }
            else if(b_Score > w_Score)
            {
                JOptionPane.showMessageDialog(null, "BLACK WINS");
            }
        }
    }

    public void resetGame()
    {
        turn = board.getTurn();
        w_Score =2;//The game starts with 2 pieces from each color on the board;
        b_Score = 2;

        updateScore();
        setTurn(turn);
    }

    private class ButtonListener implements ActionListener
    { 
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == newGame)
            {
                board.resetGame();
                resetGame();
                board.lookForMove(turn);
            }
        }
    }

    private class ReversiListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            for(int r = 0; r < 8; r++)
            {
                for(int c = 0; c < 8; c++)
                {
                    if(e.getX() >= board.getSquare(r,c).getX()+145 && e.getY() >= board.getSquare(r,c).getY() &&
                    e.getX() <= board.getSquare(r,c).getX()+225 && e.getY() <= board.getSquare(r,c).getY()+80)
                    {
                        if(board.getSquare(r,c).getType() == VALID)
                        {
                            board.setValid(true);
                            board.placePiece(r, c, board.getValid());
                            board.flip(turn,r,c, -1, 0);
                            board.flip(turn, r,c, +1, 0);
                            board.flip(turn, r,c, 0, -1);
                            board.flip(turn, r,c, 0, +1);
                            board.flip(turn, r,c, -1, -1);
                            board.flip(turn, r,c, -1, +1);
                            board.flip(turn, r,c, +1, -1);
                            board.flip(turn, r,c, +1, +1);
                            board.setPieceNumber();
                            setWhiteScore(board.getWhitePieces());
                            setBlackScore(board.getBlackPieces());
                            updateScore();
                            board.changeTurn();
                            turn = board.getTurn();

                        }
                    }
                }
            }
            setTurn(turn);
            board.setValid(false);
            board.lookForMove(turn);
        }

        public void mousePressed(MouseEvent e)
        {}

        public void mouseEntered(MouseEvent e)
        {}

        public void mouseExited(MouseEvent e)
        {}

        public void mouseReleased(MouseEvent e)
        {
        }
    }
}
