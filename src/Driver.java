import javax.swing.*;

public class Driver {

    public static void main(String[] args) {

        new Game();
    }//end main

    public static void clearScreen(){
        for(int i = 0; i < 30; i++)
            System.out.println();
    }

    public static int[][] getSampleGrid(){
        // 3x3 Blinker
        int[][] sample1 = {
                {0,1,0},
                {0,1,0},
                {0,1,0}
        };

        // 3x3 Glider
        int[][] sample2 = {
                {1,0,0},
                {0,1,1},
                {1,1,0}
        };

        // 5x5 Pattern
        int[][] sample3 = {
                {1,0,0,0,0},
                {0,1,0,0,0},
                {0,0,1,0,0},
                {0,0,0,1,0},
                {0,0,0,0,1}
        };

        // 2x3 Pattern
        int[][] sample4 = {
                {1,0,1},
                {1,1,1}
        };

        // 10x10 Blinker
        int[][] sample5 = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}
        };

        // 9x9 Full Border
        int[][] sample6 ={
                {1,1,1,1,1,1,1,1,1},
                {1,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,1},
                {1,1,1,1,1,1,1,1,1}
        };

        // 9x9 Full Border
        int[][] sample7 ={
                {0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0},
                {0,0,0,0,1,0,0,0,0},
                {0,0,0,0,1,0,0,0,0},
                {0,0,0,0,1,0,0,0,0},
                {0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0}
        };

        // 5x5 Pattern
        int[][] sample8 = {
                {0,0,0,0,0},
                {0,0,1,0,0},
                {0,0,1,0,0},
                {0,0,1,0,0},
                {0,0,0,0,0}
        };
        return sample8;
    }//end getSampleGrid

    public static int[][] getGliderGun(){

            // 20 x 38
           int[][] gliderGun = {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0},
                {0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,1,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}

            };

        return gliderGun;
    }//end getGliderGun

    public static int[][] getSpaceship(){
        // 7 x 9
        int[][] spaceship = {
                {0,0,0,0,0,0,0,0,0},
                {0,0,1,0,0,1,0,0,0},
                {0,0,0,0,0,0,1,0,0},
                {0,0,1,0,0,0,1,0,0},
                {0,0,0,1,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0},
        };

        return spaceship;
    }//end getSpaceship

}//end class Driver
