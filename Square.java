
/**
 * The square class give the square object all of its behavious and attributes. This class contains all of
 * the methods needed for the square object to operate. The squares have different type according to
 * whether they are empty, holding a piece of a certain color or are valid squares where the next piece
 * can be placed.
 * 
 * @author (Matekoa Motsoasele) 
 * @version (April 2016)
 */
import java.awt.*;

public class Square
{
    //Instance fields
    private int x, y;
    private static final int SIZE = 80;
    private Color green;
    private int type;
    public int count = 0;
    private static final int EMPTY = 0;
    private static final int BLACK = 1;
    private static final int WHITE = 2;
    private static final int VALID = 3;
    
    /**
     *Constructor, takes in the x and y coordinates and the square type
     */
    public Square(int x, int y, int type)
    {
        this.x = x;
        this.y = y;
        green = new Color(5, 145, 10);
    }
    
    /**
     * Sets the x and y location of the square.
     */
    public void setXY(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Accessor method for the x coordinate
     */
    public int getX()
    {
        return x;
    }

    /**
     * Accessor method for the y coordinate
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * Sets the square type
     */
    public void setType(int t)
    {
        type = t;
    }
    
    
    public int getType()
    {
        return type;
    }
    
    /**
     * Draws the square according to what type it is, a square of a piece type contains a piece of
     * that color in it, while a square of a valid type just draws an oval in the valid square to 
     * distinguish it from the other types.
     */
    public void draw(Graphics g)
    {
        if(type == EMPTY)
        {
            g.setColor(green);
            g.fillRect(x, y, SIZE, SIZE);
            g.setColor(Color.black);
            g.drawRect(x, y, SIZE, SIZE);
        }
        else if(type == BLACK)
        {
            g.setColor(green);
            g.fillRect(x, y, SIZE, SIZE);
            g.setColor(Color.black);
            g.fillOval(x, y, SIZE, SIZE);
            g.drawRect(x, y, SIZE, SIZE);
        }
        else if (type == WHITE)
        {
            g.setColor(green);
            g.fillRect(x, y, SIZE, SIZE);
            g.setColor(Color.black);
            g.drawRect(x, y, SIZE, SIZE);
            g.setColor(Color.WHITE);
            g.fillOval(x, y, SIZE, SIZE);
        }
        
         else if (type == VALID)
        {
            g.setColor(green);
            g.fillRect(x, y, SIZE, SIZE);
            g.setColor(Color.black);
            g.drawRect(x, y, SIZE, SIZE);
            g.setColor(Color.WHITE);
            g.drawOval(x, y, SIZE, SIZE);
        }
    }
}
