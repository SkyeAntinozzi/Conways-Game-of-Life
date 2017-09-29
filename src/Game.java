/**
 * Created by Lumine on 3/20/2016.
 */
import sun.reflect.annotation.ExceptionProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    /** The default width and height of the JFrame's content pane. */
    public static final int DEFAULT_WIDTH = 800,
                            DEFAULT_HEIGHT = 800;

    /** The default number of rows and columns for the CellGrid */
    public static final int DEFAULT_ROWS = 50,
                            DEFAULT_COLUMNS = 50;


    /** Title of the JFrame */
    public static final String FRAME_TITLE = "The Game of Life - v1.1" + " Rows: " + DEFAULT_ROWS +
                                                                         " Cols: " + DEFAULT_COLUMNS;
    // Top level frame container for the Game
    private JFrame frame;

    // The content pane for this frame
    private JPanel contentPane;

    // The canvas which contains the CellGrid
    private CellGrid canvas;

    // The size of the content pane and canvas
    private Dimension size;

    // The running state of the game loop
    private volatile boolean running;

    // The running state of evolutions
    private volatile boolean shouldEvolve;

    // The thread this game will run on
    private Thread gameThread;

    // The running status label
    private JLabel statusLbl;

    /**
     * Creates a new Game of Life.
     */
    public Game() {

        setupGraphics();
        //setupStartingGrid();


        System.out.println(canvas);
        // Start up the game on its own thread
        startGame();
    }//end Game

    /**
     *
     */
    private void startGame(){
        // Create a new thread for the game
        gameThread = new Thread(this);

        // And start up the new thread
        gameThread.start();
    }//end startGame

    /**
     *
     */
    public void run(){

        //System.out.println(canvas.getFullGridString());

        running = true;
        shouldEvolve = false;
        System.out.println("Ready");

        while(running){
            if(shouldEvolve) {
                //System.out.println("Evolving");
                System.out.println(canvas.getVisibleGridString());
                canvas.evolve();
                canvas.requestDraw();
            }
            try{ Thread.sleep(100); } catch(Exception ex){}

        }
    }//end run

    /**
     *
     */
    private void setupStartingGrid(){
        canvas.setVisibleGrid(Driver.getGliderGun());
        canvas.requestDraw();
    }//end setupStartingGrid

    /**
     * Helper method.
     * This sets up the graphical user interface and the Canvas's double buffer.
     */
    private void setupGraphics(){

        // Set the look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ex){}

        // Setup the frame and content pane
        frame = new JFrame(FRAME_TITLE);
        contentPane = (JPanel) frame.getContentPane();

        // Prepare the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JButton startBtn = new JButton("Start/Resume");
        JButton pauseBtn = new JButton("Pause");
        JButton clearBtn = new JButton("Clear");
        statusLbl = new JLabel("Paused");

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        menuBar.add(startBtn);
        menuBar.add(pauseBtn);
        menuBar.add(clearBtn);
        menuBar.add(statusLbl);

        // Determine the side length of each Cell based on the size of the square
        // content pane and canvas
        //Cell.setSideLength(DEFAULT_HEIGHT / DEFAULT_ROWS);
        Cell.setSideLength(20);

        // Create a new CellGrid with the default rows and columns
        canvas = new CellGrid(DEFAULT_ROWS, DEFAULT_COLUMNS);

        // Create a new size for the content pane
        size = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // Set the content pane's preferred size to the square dimension
        contentPane.setPreferredSize(size);

        // Set the background color of the canvas
        canvas.setBackground(new Color(79,76,92));

        // Add the canvas to the content pane
        contentPane.add(canvas);

        // Add the mouse listener for the canvas
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = CellGrid.pointToIndex(e.getY());
                int col = CellGrid.pointToIndex(e.getX());
                System.out.println(canvas.getFullCell(row, col).visible());
                canvas.toggleCell(row, col);
            }//end mousePressed
        });//end addMouseListener

        // Add the start button listener for the menu bar
        startBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                System.out.println("Start");
                shouldEvolve = true;
                toggleStatusLabel();
            }//end actionPerformed
        });//end addActionListener

        // Add the pause button listener for the menu bar
        pauseBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                System.out.println("Pause");
                shouldEvolve = false;
                toggleStatusLabel();
            }//end actionPerformed
        });//end addActionListener

        // Add the clear button listener for the menu bar
        clearBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                System.out.println("Clear");
                canvas.clear();
                canvas.requestDraw();
            }//end actionPerformed
        });//end addActionListener

        // Add the open button listener for the menu bar
        openItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                System.out.println("Open");
                canvas.openGrid();
                canvas.requestDraw();
            }//end actionPerformed
        });//end addActionListener

        // Add the clear button listener for the menu bar
        saveItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                System.out.println("Save");
                canvas.saveGrid();
            }//end actionPerformed
        });//end addActionListener

        // Set some final attributes for the frame
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Create the Canvas's double buffer
        canvas.createBufferStrategy(2);

    }//end class Game

    private void toggleStatusLabel(){
        if(shouldEvolve){
            statusLbl.setForeground(new Color(0, 150, 0));
            statusLbl.setText("Running");
        }
        else {
            statusLbl.setForeground(Color.black);
            statusLbl.setText("Paused");
        }
    }//end toggleStatusLabel
}//end class Game
