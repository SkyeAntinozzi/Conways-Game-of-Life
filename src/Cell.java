/**
 * Represents a Cell that may belong to a CellGrid object. A Cell has a number
 * attributes to denote its state such as: if a Cell is filled or not,
 * if a Cell is within the visible grid, the column and row location of a
 * Cell within a full grid and so on.
 * @author Skye Antinozzi
 */
import java.awt.*;

public class Cell {

    // !FIELDS! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private int row,
                column;                 // Row and column location on the full grid

    private int xLoc,
                yLoc;                   // X and Y location on the graphical grid
    private static int sideLength;      // Side length of a Cell in pixels
    private boolean filled;             // Is this Cell filled?
    private boolean visible;            // Is this Cell visible?
    private boolean flagged;            // Is this Cell flagged for clearing?
    private static CellGrid grid;       // Cell lives here. Each Cell uses the same
                                        // reference to a single CellGrid.

    // !CONSTRUCTORS! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Constructs a new Cell that belongs to the specified grid and has a location
     * indicated by the specified row and column arguments. More specifically,
     * the row and column represent the Cell's location within the full grid
     * rather than the visible grid.
     * @param grid the CellGrid this Cell belongs to
     * @param row the row location of this Cell within the full grid
     * @param column the column location of this Cell within the full grid
     */
    public Cell(CellGrid grid, int row, int column){
        // If the grid that is shared amongst all cells is not assigned
        // then assign it
        if(this.grid == null)
            this.grid = grid;

        // Assign this Cell's row and column within the full grid
        this.row = row;
        this.column = column;

        // Compute the X and Y locations of this Cell
//        xLoc = column * sideLength;
//        yLoc = row * sideLength;
    }//end Cell

    // !METHODS! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     *
     * @return
     */
    public int getX() {
        return xLoc;
    }//end getX

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.xLoc = x;
    }//end setX

    /**
     *
     * @return
     */
    public int getY() {
        return yLoc;
    }//end getY

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.yLoc = y;
    }//end setY

    /**
     * Returns the side length that all Cells have.
     * @return side length of a Cell
     */
    public static int getSideLength(){
        return sideLength;
    }//end getSideLength

    /**
     * Sets the side length for all Cells.
     * @param length the new side length for all Cells
     */
    public static void setSideLength(int length){
        sideLength = length;
    }//end setSideLength

    /**
     * Returns a String literal "1" if this Cell is filled, otherwise
     * returns a String literal "0".
     * @return a String representing this Cell's filled status as
     *         "0" or "1"
     */
    @Override
    public String toString(){
        // return "1" for filled and "0" for not filled
        return (filled ? "1" : "0");
    }//end toString

    /**
     * Sets the filled state of this Cell to the provided boolean argument.
     * If the filled argument is true then this Cell will be filled.
     * If the filled argument is false then this cell will be considered as
     * not filled.
     * @param filled the boolean value to set this Cell's filled state to
     */
    public void setFilled(boolean filled){
        this.filled = filled;
    }//end setFilled

    /**
     * Returns a boolean value representing this Cell's filled state.
     * A value of true is returned if this Cell is filled.
     * A value of false is returned if this Cell is not filled.
     * @return true if this Cell is filled
     */
    public boolean filled(){
        return filled;
    }//end filled

    /**
     * Sets the visible state of this Cell to the provided boolean argument.
     * If the visible argument is true then this Cell should be within
     * the visible grid.
     * If the visible argument is false then this Cell should not be within
     * the visible grid.
     * @param visible the boolean value to set this Cell's visible state to
     */
    public void setVisible(boolean visible){
        this.visible = visible;
    }//ene setVisible

    /**
     * Returns a boolean value representing this Cell's visible state.
     * A boolean value of true is returned if this Cell is visible.
     * A boolean value of false is returned if this Cell is not visible.
     * @return true if this Cell is visible
     */
    public boolean visible(){
        return visible;
    }//end visible

    /**
     * Sets the flagged status of this Cell.
     * A Cell that is flagged is said to be "marked for deletion"
     * or "marked for clearing".
     * @param flagged the flagged status to set this Cell to
     */
    public void setFlagged(boolean flagged){
        this.flagged = flagged;
    }//end setFlagged

    /**
     * Returns the flagged status of this Cell.
     * A Cell that is flagged is said to be "marked for deletion"
     * or "marked for clearing".
     * @return the flagged status of this Cell
     */
    public boolean flagged(){
        return flagged;
    }//end flagged

    /**
     * Returns an integer value representing this Cell's row location
     * within the visible grid.
     * @return an integer representing this Cell's visible row location
     */
    public int getVisibleRow(){
        return (row - CellGrid.HIDDEN_ROWS);
    }//end getVisibleRow

    /**
     * Returns an integer value representing this Cell's column location
     * within the visible grid.
     * @return an integer representing this Cell's visible column location
     */
    public int getVisibleColumn(){
        return (column - CellGrid.HIDDEN_COLUMNS);
    }//end getVisibleColumn

    /**
     * Returns an integer value representing this Cell's row location
     * within the full grid.
     * @return an integer representing this Cell's full row location
     */
    public int getFullRow(){
        return row;
    }//end getFullRow

    /**
     * Returns an integer value representing this Cell's column location
     * within the full grid.
     * @return an integer representing this Cell's full column location
     */
    public int getFullColumn(){
        return column;
    }//end getFullColumn

    /**
     * Returns the next generation state for this cell with respect
     * to the grid it belongs to.
     * True means this Cell will be alive in the next generation.
     * False means this Cell will be dead in the next generation.
     * @return the next generation state of this Cell
     */
    public boolean nextGenState(){

        boolean returnState = false;            // Whether this cell will be filled or not

        int neighbors = 0,  // Counter for total number of live neighbors around this Cell
            currRow = -1,   // Row of the neighbor Cell we're comparing to this Cell
            currCol = -1;   // Column of the neighbor Cell we're comparing to this Cell

        // Storage for neighbor cells we will be comparing to this Cell
        Cell currCell = null;

        // Compare the 8 surrounding Cells to this Cell
        // Comparison order:
        // | 1 2 3 |
        // | 4   5 |
        // | 6 7 8 |
        for(int i = -1; i <= 1; i++)
            for(int j = -1; j <= 1; j++){
                // Get current neighbor Cell's row and column
                currRow = row + i;
                currCol = column + j;

                // Check if the current Cell exists
                // If it doesn't exist then skip that neighbor and continue to check for the next neighbor
                if(grid.fullCellExists(currRow, currCol)){
                    // If the neighbor cell exists then
                    // Get the current neighbor Cell
                    currCell = grid.getFullCell(currRow, currCol);

                    // If the cell is not the Cell we're calling this
                    // method from and if it is filled then increment
                    // neighbors
                    if(currCell != this && currCell.filled())
                        neighbors++;
                }//end if
            }//end inner for

        // Once all surrounding cells have been checked

        // If 3 neighbors and this cell is empty then the Cell will be born
        // Leave this test in here in case a cell state of Born needs to be used
        // Currently, true is alive and false is dead. There is no unique cell state for born
        if(neighbors == 3 && !filled())
            returnState = true;

        // If 2 or 3 neighbors and the Cell is filled then the Cell stays alive
        else if((neighbors == 2 || neighbors == 3) && filled())
            returnState = true;

        // Any other case and the Cell will be dead

        // Return the state of this Cell for the next generation
        return returnState;
    }//end nextGenState

    /**
     * Draws the Cell onto the provided graphics context at the Cell's
     * X and Y locations along with its side length.
     * @param g
     */
    public void drawCell(Graphics g){
        // Graphics2D for setStroke
        Graphics2D g2d = (Graphics2D) g;

        // If the Cell is filled then draw a filled rectangle
        if(filled) {
            Color inside = new Color(108, 77, 235);
            Color outline = new Color(71,33,226);

            // Draw inside
            g2d.setColor(inside);
            g2d.fillRect(xLoc, yLoc, sideLength, sideLength);

            // Draw outline
            g2d.setColor(outline);
            g2d.setStroke(new BasicStroke(2));
            g.drawRect(xLoc, yLoc, sideLength, sideLength);
        }//end if(filled)

        // Otherwise draw blank out the space
        else {
            Color outline = new Color(71,33,226);
            Color inside = new Color(79,76,92);

            // Draw inside
            g.setColor(inside);
            g.fillRect(xLoc, yLoc, sideLength, sideLength);

            // Draw outline
            g2d.setColor(outline);
            g2d.setStroke(new BasicStroke(2));
            g.drawRect(xLoc, yLoc, sideLength, sideLength);
        }
    }//end drawCell
}//end class Cell
