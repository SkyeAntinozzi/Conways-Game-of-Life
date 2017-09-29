/**
 * Created by Skye on 3/21/2016.
 */
import javax.swing.*;
import java.io.*;

public class FileHandler {

    public void saveGrid(CellGrid grid){
        PrintWriter writer;
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showSaveDialog(grid);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            try {
                String fileName = chooser.getSelectedFile().getAbsolutePath() + ".txt";
                File file = new File(fileName);
                writer = new PrintWriter(file);


                for(int i = 0; i < grid.getVisibleRows(); i++) {
                    for (int j = 0; j < grid.getVisibleColumns(); j++) {
                        writer.print(grid.getFullCell(i, j).filled() ? '1' : '0');
                    }
                    writer.println();
                }//end outer for
                writer.close();
            } catch(Exception ex){}
        }
    }//end saveGrid

    public void openGrid(CellGrid grid){
        FileInputStream stream;
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(grid);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            try{
                // Get the file path to open
                String filePath = chooser.getSelectedFile().getAbsolutePath();

                // Create a new file based on the file path
                File file = new File(filePath);
                int fileLength = (int) file.length();

                // Initialize a stream to the file to open
                stream = new FileInputStream(filePath);

                // Allocated space for the ASCII pattern
                byte[] pattern = new byte[fileLength];

                // Pull the ASCII pattern in from the file
                stream.read(pattern);

                // Now to convert the byte array into a 2D integer array
                // with a specified number of rows and columns

                // Temporary storage for the rows and columns
                int rows = 0,
                    columns = 0;

                // Determine the number of columns by counting until a newline is reached
                for(int i = 0; pattern[i] != '\n'; i++)
                    if(pattern[i] == '1' || pattern[i] == '0')
                        columns++;

                // Determine the number of rows by counting the number of newline characters
                for(int i = 0; i < fileLength; i++)
                    if(pattern[i] == '\n')
                        rows++;

                System.out.println("Rows: " + rows + " Cols: " + columns);

                // Create a new 2D integer array with the size of rows and columns
                int[][] newGrid = new int[rows][columns];

                // Temporary storage for the current character from the pattern array and
                // the current row and column for the 2D integer arrray
                int currByte = 0,
                        row = 0,
                        col = 0;

                // While we still haven't read all of the bytes
                while(currByte < fileLength){
                    // If input is a 1 or 0
                    if(pattern[currByte] == '1' || pattern[currByte] == '0')
                        // Then assign the next column the numeric value of the ASCII character
                        // by subtracting 48
                        newGrid[row][col++] = (pattern[currByte] - 48);
                    // If the input is a newline
                    else if(pattern[currByte] == '\n') {
                        // then move to the next row
                        row++;
                        // and move back to the first column
                        col = 0;
                    }
                    // And always move to the next character to read
                    currByte++;
                }//end while


                System.out.println("Read in");
                for(int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++)
                        System.out.print(newGrid[i][j]);
                    System.out.println();
                }

                grid.setVisibleGrid(newGrid);

                stream.close();
            } catch(Exception ex){}

        }
    }//end loadGrid


}//end class FileHandler
