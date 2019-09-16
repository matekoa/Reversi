
/**
 * This class manages the game logic. Some of the code has been borrowed from the Maze program in Chapter 12
 * of Java Software solutions by Lewis & Loftus.
 * 
 * @author (Matekoa Motsoasele) 
 * @version (May 2016)
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

public class Board extends JPanel
{
    //Instance fields
    private final static int SIZE = 641;
    private final static int EMPTY = 0;
    private final static int BLACK = 1;
    private final static int WHITE= 2;
    private final static int VALID = 3;
    private Square[][] square;
    private static int turn;
    private RenderingHints render;
    private int rows = 8, col = 8;
    private int x, y;
    private boolean valid, done;
    private static int whitePieces, blackPieces;

    public Board()
    {
        whitePieces= 0;
        blackPieces = 0;
        render = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        //Creates a 2 dimensional array of squares
        square = new Square[rows][col];
        turn = 1;//black starts
        x = 0;
        y = 0;
        setBackground(Color.blue);
        setPreferredSize(new Dimension(SIZE, SIZE));
        valid = false;
        done = false;

        //Instantiates the squares and places them according to the specified x and y coordinates and sets them all to type EMPTY.
        for(int r = 0 ; r < rows; r++)
        {
            for(int c = 0; c < col; c++)
            {
                square[r][c] = new Square(x,y, EMPTY);
                x += 80;
            }
            x = 0;
            y += 80;
        }

        //Changes the square types to types of Black and white pieces at specified rows and columns.
        square[3][3].setType(BLACK);
        square[4][3].setType(WHITE);
        square[4][4].setType(BLACK);
        square[3][4].setType(WHITE);

    }

    /**
     *Draws the board and the pieces onto the screen. 
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHints(render);

        for(int r = 0 ; r < rows; r++)
        {
            for(int c = 0; c < col; c++)
            {
                square[r][c].draw(g);
            }
        }
    }

    /**
     * Makes sure that the move is valid. 
     */
    public boolean validMove(int r, int c)
    {
        boolean valid = false;

        if(inBounds(r, c) && square[r][c].getType() == EMPTY)
        {
            valid = false;
        }

        else
        {
            valid = true;
        }

        return valid;
    }

    /**
     * Always checks to make sure that the piece in a certain row and column is still in bounds.
     */
    public boolean inBounds(int r, int c)
    {
        boolean result = false;
        if(r >= 0 && r <= 7 && c >= 0 && c <= 7)
        {
            result = true;
        }
        return result;
    }

    /***
     *The piece number is reseted to zero everytime since there is always a possibility that the scores have changed. Since the whitePieces,
     *and blackPieces are static values, this allows to alter with them starting our count at zero everytime.
     */
    public void setPieceNumber()
    {
        whitePieces= 0;
        blackPieces = 0;

        for(int r = 0 ; r < rows; r++)
        {
            for(int c = 0; c < col; c++)
            {
                if(square[r][c].getType() == BLACK)
                {
                    blackPieces++;
                }
                else if(square[r][c].getType() == WHITE)
                {
                    whitePieces++;
                }
            }
        }
    }

    /**
     *Returns the number of white pieces. 
     */
    public int getWhitePieces()
    {
        return whitePieces;
    }

    /**
     *Returns the number of black pieces. 
     */
    public int getBlackPieces()
    {
        return blackPieces;
    }

    public boolean getDone()
    {
        return done;
    }

    /**
     *Changes the current turn and also updates the valid squares for the next turn.
     */
    public void changeTurn()
    {
        if(turn == BLACK )
        {
            turn = WHITE;
        }
        else if(turn == WHITE)
        {
            turn = BLACK;
        }

        for(int r = 0 ; r < rows; r++)
        {
            for(int c = 0; c < col; c++)
            {
                if(square[r][c].getType() == VALID)
                {
                    square[r][c].setType(EMPTY);
                }
            }
        }
        repaint();
    }

    /**
     * Returns the current turn
     */
    public int getTurn()
    {
        return turn;
    }

    /**
     *Allows the player to place a piece onto the board only if the square that is clicked is of a valid type 
     */
    public void placePiece(int r, int c, boolean v)
    {
        valid = v;
        if(valid)
        {
            if(turn == 1)
            {
                square[r][c].setType(1);
            }
            else if(turn == 2)
            {
                square[r][c].setType(2);
            }
        }

        repaint();
    }

    /**
     * Returns a square at a specified row and column
     */
    public Square getSquare(int r, int c)
    {
        return square[r][c];
    }

    /**
     * Mutator method to alter with valid at any specified moment
     */
    public void setValid(boolean v)
    {
        valid = v;
    }

    /**
     * Returns true if there is a valid move.
     */
    public boolean getValid()
    {
        return valid;
    }

    /**
     * Flips the pieces of a different type after a piece is placed. This recursive method starts from the... and ends when it is runs into a piece
     * that has just been placed.
     */
    public boolean flip(int turn, int ro, int co, int yDir, int xDir)
    {
        int r,c;
        r = ro;
        c = co;
        r += yDir;
        c += xDir;
        boolean flip = false;

        if(inBounds(r, c) && square[r][c].getType() != EMPTY && square[r][c].getType() != VALID)
        {
            if(square[r][c].getType() != turn)
            {
                flip = flip(turn, r,c ,yDir,xDir);
            }

            if(square[r][c].getType() == turn)
            {
                flip = true;
            }

            if(flip)
            {
                square[r][c].setType(turn);
            } 
        }

        repaint();
        return flip;
    }

    /**
     *Looks through the whole direction to check if there is a valid move to be made and if the space at the end of the look method is empty
     *Since the square types are a 2 dimensional array of squares in a certain row and column the yDirection acts in the vertical
     *direction meaning that it changes the row, while the xDirection(xDir) changes the column. This method is recursive and is always 
     *checking as long as the next piece in the direction of checking is of a type that allows it to become flipped. The base case is 
     *When the method gets to the end of the pieces to be flipped and the next square is empty.
     *
     */
    public boolean look(int turn, int ro, int co, int yDir, int xDir)
    {
        boolean found = false;
        int r = ro;
        int c = co;
        r += yDir;
        c +=  xDir;

        if(inBounds(r, c) && square[r][c].getType() != EMPTY && square[r][c].getType() != VALID)
        {
            if(square[r][c].getType() != turn)
            {
                if(inBounds(r+yDir,c+xDir)){
                    //Base case if it gets to the empty space.
                    if(square[r+yDir][c+xDir].getType() == EMPTY)
                    {
                        square[r+yDir][c+xDir].setType(VALID);
                        found = true;
                    }
                    //Keeps looking as long as the piece found is to be flipped.
                    else{
                        look(turn, r,c ,yDir,xDir);
                    }
                }
            }
        }

        repaint();
        return found;
    }

    /**
     * Looks for a legal move in all of the directions.
     */
    public void lookForMove(int turn)
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(square[i][j].getType() == turn){

                    look(turn,i,j, -1, 0);//LOOKS UP

                    //looks down
                    look(turn, i,j, +1, 0);

                    //looks left
                    look(turn, i,j, 0, -1);

                    //looks right
                    look(turn, i,j, 0, +1);

                    //looks diagonally
                    look(turn, i,j, -1, -1);
                    look(turn, i,j, -1, +1);
                    look(turn, i,j, +1, -1);
                    look(turn, i,j, +1, +1);
                }
            }
        }
    }

    /**
     * Resets the game and sets everything to the default setting.
     */
    public void resetGame()
    {
        for(int r = 0 ; r < rows; r++)
        {
            for(int c = 0; c < col; c++)
            {
                square[r][c].setType(EMPTY);
            }
        }

        square[3][3].setType(BLACK);
        square[4][3].setType(WHITE);
        square[4][4].setType(BLACK);
        square[3][4].setType(WHITE);

        turn = 1;
        repaint();
    }
}

