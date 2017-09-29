/**
 * Represents a grid of Cells. This grid follows the rules of Conway's
 * Driver of Life by "evolving" the Cell patterns into their next generation
 * state.
 * @author Skye Antinozzi
 */
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.nio.Buffer;

public class CellGrid extends Canvas{

    // !FIELDS! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // The Cells all belong to this grid. This grid represents the full grid
    // that also contains the visible grid. If we label cells outside of the
    // visible grid as H and we label cells within the visible grid as V
    // the full grid may be visualized as:
    // H H H H H
    // H V V V H
    // H V V V H
    // H V V V H
    // H H H H H
    private Cell[][] grid;


    // The user cannot see the Cell's within these rows and columns.
    // The hidden rows and columns will be added on BOTH sides of the
    // visible grid. For example, a value of 2 hidden rows
    // will add two rows above and two rows below the visible grid.

    /** The number of hidden rows on one side of the grid. */
    public static final int HIDDEN_ROWS = 3;

    /** The number of hidden columns on one side of the grid. */
    public static final int HIDDEN_COLUMNS = 3;

    // User can see these
    private int visibleRows,
                visibleColumns;

    // Visible row/columns + Hidden row/columns
    private int totalRows,
                totalColumns;

    // The starting indices of the visible grid
    private int visibleRowStart,
                visibleColumnStart;

    // The ending indices immediately OUTSIDE of the visible grid
    private int visibleRowEnd,
                visibleColumnEnd;

    // !CONSTRUCTORS! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Constructs a new CellGrid with the specified number of visible rows
     * and visible columns.
     * The rows and columns arguments will represent how many rows and columns
     * are within the visible grid. The full grid is determined by the sum of the
     * visible rows and columns and the hidden rows and columns, whereas the hidden
     * rows and columns are defined by the CellGrid's class' constants HIDDEN_ROWS
     * and HIDDEN_COLUMNS. For example, if a CellGrid is created with rows = 3 and
     * columns = 3 and the initialization of the constants are HIDDEN_ROWS = 5 and
     * HIDDEN_COLUMNS = 5 then the size of the full grid would be 13 x 13 where
     * the size of the visible grid would be 3 x 3.
     * @param rows the number of rows for the visible grid
     * @param columns the number of columns for the visible grid
     */
    public CellGrid(int rows, int columns){
        // the visible rows/columns are the values's passed
        visibleRows = rows;
        visibleColumns = columns;

        // the hidden row/columns are multiplied by 2 to add
        // row/columns on all 4 sides of the visible grid
        // the total rows/columns are given by twice the hidden row/columns
        // and adding the number of visible row/columns
        totalRows = 2 * HIDDEN_ROWS + visibleRows;
        totalColumns = 2 * HIDDEN_COLUMNS + visibleColumns;

        // The visible grid DOES NOT start in the
        // upper left corner or end in the lower right corner of the full grid.
        // Instead, the starting and ending indices must be calculated
        // and stored so we can conveniently assign elements to the visible grid

        // It happens to be that the number of hidden rows or columns is
        // equal to the starting index of the visible grid
        visibleRowStart = HIDDEN_ROWS;
        visibleColumnStart = HIDDEN_COLUMNS;

        // By adding the row/column start and number of visible
        // rows/columns the value retrieved is the immediate cell
        // OUTSIDE of the visible grid
        visibleRowEnd = visibleRowStart + visibleRows;
        visibleColumnEnd = visibleColumnStart + visibleColumns;

        // Allocate space for the visible and non visible Cells
        // which compose the full grid
        grid = new Cell[totalRows][totalColumns];

        // Fill the full grid with empty cells
        for(int i = 0; i < totalRows; i++)
            for(int j = 0; j < totalColumns; j++) {
                // Place a blank cell down
                grid[i][j] = new Cell(this, i, j);
                // Only if the cell is within the visible area should it be visible
                if( ((i >= visibleRowStart && i < visibleRowEnd) && (j >= visibleColumnStart && j < visibleColumnEnd)))
                    grid[i][j].setVisible(true);
            }//end inner for

        // Setup the graphical coordinates for all the visible cells
        assignVisibleCellCoordinates();
        System.out.println(getLabeledGridString());
    }//end CellGrid

    // !METHODS! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     *
     */
    public void toggleCell(int row, int column){
        // Get the visible Cell to toggle
        Cell c = getVisibleCell(row, column);

        // Toggle the filled status of the Cell
        c.setFilled(c.filled() ? false : true);

        // Redraw the Grid
        BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        drawGrid(g);
        g.dispose();
        bs.show();
    }//end toggleCell

    /**
     *
     */
    private void assignVisibleCellCoordinates(){
        int counter = 0;
        for(int i = visibleRowStart; i < visibleRowEnd; i++){
            for(int j = visibleColumnStart; j < visibleColumnEnd; j++){
                grid[i][j].setX((j - HIDDEN_COLUMNS) * Cell.getSideLength());
                grid[i][j].setY((i - HIDDEN_ROWS) * Cell.getSideLength());
                ///if(grid[i][j])
            }//end inner for

        }//end visibleRow for
    }//end assignVisibleCellCoordinates

    /**
     * Returns a formatted String representing the visible grid.
     * @return the formatted String that represents the visible grid
     */
    public String getVisibleGridString(){
        // Store the visible grid string here
        String visibleGrid = "";

        // iterate over the visible grid
        // the start position is given by visibleRowStart/visibleColumnStart
        // the end position is just the visibleRowEnd/visibleColumnEnd
        for(int i = visibleRowStart; i < visibleRowEnd; i++) {
            for (int j = visibleColumnStart; j < visibleColumnEnd; j++) {
                visibleGrid += grid[i][j];
                visibleGrid += ' ';
            }//end inner for
            visibleGrid += '\n';
        }//end outer for

        // Return the visible grid as a formatted String
        return visibleGrid;
    }//end getVisibleGridString

    /**
     * Returns a formatted String representing the full grid.
     * @return the formatted String that represents the full grid
     */
    public String getFullGridString(){
        // Store the full grid string here
        String fullGrid = "";

        // Iterate over the full grid
        for(int i = 0; i < totalRows; i++){
            for(int j = 0; j < totalColumns; j++){
                fullGrid += grid[i][j];
                fullGrid += ' ';
            }//end inner for
            fullGrid += '\n';
        }//end outer for

        // Return the full grid as a formatted String
        return fullGrid;
    }//end getFullGridString

    /**
     * Returns a formatted String representing the full grid labeled with
     * visible and hidden cells.
     * @return the formatted String that represents the labeled full grid
     */
    public String getLabeledGridString(){
        // Store the labeled grid String here
        String labeledGrid = "";

        // Iterate over the full grid
        for(int i = 0; i < totalRows; i++){
            for(int j = 0; j < totalColumns; j++){
                labeledGrid += (grid[i][j].visible() ? "V" : "H");
                labeledGrid += ' ';
            }//end inner for
            labeledGrid += '\n';
        }//end outer for

        return labeledGrid;
    }//end getLabeledGridString

    /**
     * Sets the visible grid to the specified grid. The new grid must be the
     * same size as the visible grid for this method to properly set
     * the visible grid.
     * @param newGrid the new visible grid configuration to use
     */
    public void setVisibleGrid(int[][] newGrid){
        // Temporarily store the filled status described by the newGrid
        // for each cell
        boolean filled;

        // Iterate through the visible grid
        for(int i = 0; i < visibleRows; i++)
            for(int j = 0; j < visibleColumns; j++) {

                // If the current value is 1 then turn the Cell on,
                // otherwise turn it off
                filled = (newGrid[i][j] == 1 ? true : false);

                // We're starting at i=0 and j=0 to index into the
                // newGrid. To fill the correct cells in the visible grid
                // we need to offset i and j by the amount of HIDDEN_ROWS
                // and HIDDEN_COLUMNS
                grid[i + HIDDEN_ROWS][j + HIDDEN_COLUMNS].setFilled(filled);
            }//end inner for

        // The grid has now been set to the newGrid
    }//end setVisibleGrid

    /**
     * Sets the full grid to the specified grid. The new grid must be the
     * same size as the full grid for this method to properly set the
     * visible grid.
     * @param newGrid the new full grid configuration to use
     */
    public void setFullGrid(int[][] newGrid){
        // Temporarily store the filled status described by the newGrid
        // for each cell here
        boolean filled;

        // Iterate through the full grid
        for(int i = 0; i < totalRows; i++)
            for(int j = 0; j < totalColumns; j++) {
                // If the current value is 1 then turn the Cell on,
                // otherwise turn it off
                filled = (newGrid[i][j] == 1 ? true : false);
                grid[i][j].setFilled(filled);
            }//end inner for
    }//end setFullGrid

    /**
     * Returns the visible Cell located at the specified row and column.
     * The row and column are relative to the visible grid. For instance,
     * if the visible grid size is 3 x 3 then this method has a
     * domain of argument values of [0,2] for both row and column.
     * @param row the row location of the visible Cell to return
     * @param column the column location of the visible Cell to return
     * @return the visible Cell located at the row and column
     */
    public Cell getVisibleCell(int row, int column){
        return grid[row+ HIDDEN_ROWS][column+ HIDDEN_COLUMNS];
    }//end getVisibleCell

    /**
     * Returns the full Cell located at the specified row and column.
     * The row and column are relative to the full grid. For instance,
     * if the full grid size is 5 x 5 then this method has a
     * domain of argument values of [0,4] for both row and column.
     * @param row the row location of the full Cell to return
     * @param column the column location of the full Cell to return
     * @return the full Cell located at the row and column
     */
    public Cell getFullCell(int row, int column){
        return grid[row][column];
    }//end getFullCell

    /**
     * Returns the total number of rows this CellGrid contains.
     * This total is the sum of the total number of visible rows
     * and the total number of hidden rows.
     * @return the total number of rows for this CellGrid
     */
    public int getTotalRows(){
        return totalRows;
    }//end getTotalRows

    /**
     * Returns the number of visible rows for this CellGrid.
     * @return the number of visible rows
     */
    public int getVisibleRows(){
        return visibleRows;
    }//end getVisibleRows

    /**
     * Returns the total number of columns this CellGrid contains.
     * This total is the sum of the total number of visible columns
     * and the total number of hidden columns.
     * @return the total number of columns for this CellGrid
     */
    public int getTotalColumns(){
        return totalColumns;
    }//end getTotalColumns

    /**
     * Returns the number of visible columns for this CellGrid.
     * @return the number of visible columns
     */
    public int getVisibleColumns(){
        return visibleColumns;
    }//end getVisibleColumns

    /**
     * Returns a formatted String representing a very useful table of
     * information for the CellGrid. An example output may be:
     * CellGrid Statistics <br>
     * Total Size          : [7 x 7] <br>
     * Visible Size        : [3 x 3] <br>
     * Hidden Rows         : 2   Total Hidden Rows   : 4 <br>
     * Hidden Columns      : 2   Total Hidden Columns: 4 <br>
     * Visible Row Start   : 2   Visible Row End     : 5 <br>
     * Visible Column Start: 2   Visible Column End  : 5 <br>
     * @return a formatted String representing a table of information
     *         for the CellGrid
     */
    @Override
    public String toString(){
        String s =
        "CellGrid Statistics" + '\n' +
        "Total Size          : [" + totalRows          + " x "                       + totalColumns       + "]\n" +
        "Visible Size        : [" + visibleRows        + " x "                       + visibleColumns     + "]\n" +
        "Hidden Rows         : "  + HIDDEN_ROWS        + "   Total Hidden Rows   : " + HIDDEN_ROWS * 2    + '\n'  +
        "Hidden Columns      : "  + HIDDEN_COLUMNS     + "   Total Hidden Columns: " + HIDDEN_COLUMNS * 2 + '\n'  +
        "Visible Row Start   : "  + visibleRowStart    + "   Visible Row End     : " + visibleRowEnd      + '\n'  +
        "Visible Column Start: "  + visibleColumnStart + "   Visible Column End  : " + visibleColumnEnd;

        return s;
    }//end toString

    /**
     * This method causes the CellGrid to move into its next generation state.
     * This will cause each Cell to be evaluated and a state to be returned
     * as to whether they will live on or die in the next generation. Once
     * each Cell's next generation state is determined the current grid will
     * be overwritten with a new grid representing the next generation. This
     * method is dependent upon the nextGenState() method within the Cell class.
     * @see Cell
     */
    public void evolve(){

        // The next generation state of the cell currently being looked at
        boolean nextState;

        // The next generations grid will be copied and stored here at the end of evolve()
        int[][] nextGenGrid = new int[totalRows][totalColumns];

        // Iterate over the grid
        for(int i = 0; i < totalRows; i++)
            for(int j = 0; j < totalColumns; j++){
                // Get the Cell's next state
                nextState = grid[i][j].nextGenState();

                // Assign the state's value to the next gen grid
                nextGenGrid[i][j] = (nextState ? 1 : 0);
            }

        // Copy the nextGenGrid to overwrite the GridWorld's grid
        setFullGrid(nextGenGrid);
    }//end evolve

    /**
     * Checks the full grid's border for any filled Cells.
     * If any of the border Cells are filled then this method returns
     * a boolean value of true.
     * @return true if any of the border Cells are filled
     */
    public boolean filledCellOnBorder(){


        // This method makes use of a sort of short-circuit logic.
        // At the first occurrence of a filled Cell this method
        // will immediately return a value of true.
        // If no filled Cell is found the final return will
        // cause this method to return false.

        // Checks the border in the following order
        // |1  11  12  13  6 |
        // |2  0   0   0   7 |
        // |3  0   0   0   8 |
        // |4  0   0   0   9 |
        // |5  14  15  16  10|

        // Check Left Border
        for(int i = 0; i < totalRows; i++)
            if(grid[i][0].filled())
                return true;

        // Check Right Border
        for(int i = 0; i < totalRows; i++)
            if(grid[i][totalColumns - 1].filled())
                return true;

        // Check Top Border without corners
        for(int j = 1; j < totalColumns - 2; j++)
            if(grid[0][j].filled())
                return true;

        // Check Bottom Border without corners
        for(int j = 1; j < totalColumns - 2; j++)
            if(grid[totalRows-1][j].filled())
                return true;

        return false;
    }//end filledCellOnBorder

    /**
     * Clears all patterns that have at least one filled Cell occupying a
     * border Cell. For instance, if a Glider has at least one of its
     * Cells within the border the entire Glider will be cleared.
     * The same is true for any number of patterns that may have at least
     * one cell occupying the full grid's border in any given generation.
     */
    public void clearBorder(){
        // Iterate over the entire border
        // We don't know which Cell is filled so we must check them all

        // Checks the border in the following order:
        // |1  11  12  13  6 |
        // |2  0   0   0   7 |
        // |3  0   0   0   8 |
        // |4  0   0   0   9 |
        // |5  14  15  16  10|

        // Check Left Border
        for(int i = 0; i < totalRows; i++) {
            if (grid[i][0].filled()) {
                // If the current border Cell is filled then clear it
                // and any connected Cells
                clearPattern(i, 0);
            }//end if
        }//end for

        // Check Right Border
        for(int i = 0; i < totalRows; i++){
            if(grid[i][totalColumns - 1].filled()) {
                // If the current border Cell is filled then clear it
                // and any connected Cells
                clearPattern(i, totalColumns - 1);
            }//end if
        }//end for

        // Check Top Border without corners
        for(int j = 1; j < totalColumns - 1; j++) {
            if (grid[0][j].filled()){
                // If the current border Cell is filled then clear it
                // and any connected Cells
                clearPattern(0, j);
            }//end if
        }//end for

        // Check Bottom Border without corners
        for(int j = 1; j < totalColumns - 1; j++) {
            if (grid[totalRows - 1][j].filled()){
                // If the current border Cell is filled then clear it
                // and any connected Cells
                clearPattern(totalRows - 1, j);
            }//end if
        }//end for
    }//end clearBorder

    /**
     * Clears the pattern which contains this Cell. In other words, by providing
     * some Cell that is a component of any arbitrary pattern all connected cells
     * and the base cell will be cleared.
     * @param baseCellRow the row location of the base cell for the pattern
     * @param baseCellCol the column location of the base cell for the pattern
     */
    public void clearPattern(int baseCellRow, int baseCellCol){

        // The base cell will be cleared at the end of this method so flag it for clearing
        grid[baseCellRow][baseCellCol].setFlagged(true);

        int neighborRow,       // Row of the neighbor Cell we're comparing to this Cell
            neighborCol;       // Column of the neighbor Cell we're comparing to this Cell

        Cell neighborCell;     // Temporary storage for neighbor cells we will be comparing to this Cell

        // Compare the 8 surrounding Cells to this Cell
        // Comparison order:
        // | 1 2 3 |
        // | 4   5 |
        // | 6 7 8 |
        for(int i = -1; i <= 1; i++)
            for(int j = -1; j <= 1; j++) {
                // Get the neighbor Cell's row and column
                neighborRow = baseCellRow + i;
                neighborCol = baseCellCol + j;

                // If the cell exists within the full grid
                if ( fullCellExists(neighborRow, neighborCol) ) {

                    // Then get a reference to the current neighbor Cell
                    neighborCell = getFullCell(neighborRow, neighborCol);

                    // Check if the neighbor cell is full and
                    // if the neighbor cell is marked for clearing and
                    // make sure the neighbor cell is not the base cell
                    if ( neighborCell.filled() &&                           // Neighbor Cell is filled?
                         !(neighborCell.flagged()) &&                       // Neighbor Cell is not already flagged for deletion
                         (neighborCell != grid[baseCellRow][baseCellCol]))    // Neighbor Cell is not current Cell?
                            // If all 3 above conditions are true then proceed to delete the pattern
                            // recursively calling clearPattern
                            clearPattern(neighborRow, neighborCol);
                }//end fullCellExists if
            }//end inner for

        // Once all the connected cells have been cleared then finally clear this one
        grid[baseCellRow][baseCellCol].setFilled(false);

        // And also unflag this cell for clearing since this cell is now cleared
        grid[baseCellRow][baseCellCol].setFlagged(false);
    }//end clearPattern

    /**
     * Returns whether the specified row and column represent a visible Cell's
     * location that would exist within the visible grid. If the Cell exists
     * but is outside of the visible grid then this method would return false.
     * @param row the visible row to check for Cell existence
     * @param column the visible column to check for Cell existence
     * @return true if the visible cell at the row and column exists
     */
    public boolean visibleCellExists(int row, int column){
        // If the row and column are each within the bounds of the visible grid
        // Then the cell exists within the visible grid
        return (row < visibleRows && row >= 0) && (column < visibleColumns && column >= 0) ? true : false;
    }//end visibleCellExists

    /**
     * Returns whether the specified row and column represent a full Cell's location
     * that would exist within the full grid.
     * @param row the full row to check for Cell existence
     * @param column the full column to check for Cell existence
     * @return a boolean representing if a full Cell exists at the
     *         specified row and column
     */
    public boolean fullCellExists(int row, int column){
        // If the row and column are each within the bounds of the full grid
        // Then the cell exists within the full grid
        return (row < totalRows && row >= 0) && (column < totalColumns && column >= 0) ? true : false;
    }//end fullCellExists

    /**
     * Checks if there are any filled Cells within the full grid.
     * @return true if there is at least one filled Cell within the full grid
     */
    public boolean empty(){
        // Iterate over the grid
        for(int i = 0; i < totalRows; i++)
            for(int j = 0; j < totalColumns; j++)
                // If a single Cell is filled then immediately return false
                if(grid[i][j].filled())
                    return false;

        // Otherwise, the grid is empty so return true
        return true;
    }//end empty

    /**
     * Draws the grid onto the provided graphics context.
     * @param g the graphics context to draw the grid onto
     */
    public void drawGrid(Graphics g){
        for(int i = visibleRowStart; i < visibleRowEnd; i++)
            for(int j = visibleColumnStart; j < visibleColumnEnd; j++)
                grid[i][j].drawCell(g);
        System.out.println(getVisibleGridString());
    }//end drawGrid

    /**
     * Allows AWT to repaint the canvas for us. Used on startup and
     * if the canvas state changes.
     * @param g the graphics context to draw onto
     */
    @Override
    public void paint(Graphics g){
        drawGrid(g);
    }//end paint

    /**
     * Converts the X or Y component of an ordered pair to its respective
     * index location within a zero based grid.
     * @param point the point to convert to a zero based index
     * @return the zero based index of the point argument
     */
    public static int pointToIndex(int point){
        return point / Cell.getSideLength();
    }//end pointToIndex

    /**
     *
     */
    public void requestDraw(){
        BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        drawGrid(g);
        g.dispose();
        bs.show();
    }//end requestDraw

    /**
     *
     */
    public void clear(){
        for(int i = 0; i < totalRows; i++)
            for(int j = 0; j < totalColumns; j++)
                grid[i][j].setFilled(false);

    }//end clear

    public void saveGrid(){
        FileHandler fileHandler = new FileHandler();
        fileHandler.saveGrid(this);
    }//end saveGrid

    public void openGrid(){
        FileHandler fileHandler = new FileHandler();
        fileHandler.openGrid(this);
    }//end openGrid

}//end class CellGrid
