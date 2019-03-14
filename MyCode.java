/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazesolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A program that can solve mazes
 *
 * @author Mohammed Jakir
 * @version 0.1
 */
public class MazeSolver {
        private static int rows;
        private static int columns; 
        private static int startR;
        private static int startC;
        private static int endR;
        private static int endC;
        private static char[][] finalMaze;
    
     /**
     * createMaze takes in the file-path and creates it into a char[][]
     *
     * @param path Full file-path for the file that contains the maze
     * @return char[][]
     */    
    public static char[][] createMaze(String path) throws FileNotFoundException{
        File file = new File(path);
        // go through file
        Scanner sc = new Scanner(file);
        columns = sc.nextInt();
        rows = sc.nextInt();
        startC = sc.nextInt();
        startR = sc.nextInt();
        endC = sc.nextInt();
        endR = sc.nextInt();
        
        
        //creates empty maze
        int[][] maze = new int[rows][columns];
        
        //fills in maze
        for (int i = 0 ; i<rows; i++){
            for (int j = 0 ; j<columns ; j++){
                maze[i][j] = sc.nextInt();
            }
        }
        
        //converts maze in desired printable format
        char[][] finalMaze = new char[rows][columns]; 
        for (int i = 0 ; i<rows; i++){
            for (int j = 0 ; j<columns ; j++){
                if (maze[i][j]==1){ finalMaze[i][j] ='#';}
                if (maze[i][j]==0){ finalMaze[i][j] =' ';}
            }
        }
        finalMaze[startR][startC] = 'S';
        finalMaze[endR][endC] = 'E';
        
        return finalMaze;
        
    }
     /**
     * solveMaze traverses the maze till it reaches the end (if possible)
     *
     * @param r the row number of the maze
     * @param c the column number of the maze
     * @return boolean
     */ 
    public static boolean solveMaze(int r, int c){  
    //if r is over, minus rows to support wrapping around
    if (r >= rows){
        r=r-rows;
    }
    
    //if less then zero, add rows to support wrapping
    if (r<0){
        r=r+rows;
    }
    
    //if c is over, minus columns to support wrapping
    if (c >= columns){
        c=c-columns;
    }
    
    //if less then zero, add columns to support wrapping
    if (c<0){
        c=c+columns;
    }
     
    //check if you hit a wall
    if (finalMaze[r][c]=='#'){
        return false;
    }
    
    //check if you hit a path you already came through
    if (finalMaze[r][c]=='X'){
        return false;
    }
    
    //check if you are at the end
    if (finalMaze[r][c]=='E'){
        return true;
    }

    //if none of those hit than this is a new path, mark it with X
    finalMaze[r][c]='X';
    
    //check surrounding areas now 
    
    // check west
    if (solveMaze(r,c-1)){
        return true;
    }
    
    // check South
    if (solveMaze(r+1,c)){
        return true;
    }
    
    // check East
    if (solveMaze(r,c+1)){
        return true;
    }
    
    // check North
    if (solveMaze(r-1,c)){
        return true;
    }
    
    // if it gets to here then it is not a correct path, so reset iot back to 
    // empty and return false
    finalMaze[r][c]=' ';
    return false;
        
    }
    
    /**
    * printMaze fully prints the maze
    *
    * @param finalMaze takes in the char matrix and prints it
    */      
    public static void printMaze(char[][] finalMaze){
        //overwrite overwritten S
        finalMaze[startR][startC] = 'S';
        
        //prints final maze   
        for (int i = 0; i < finalMaze.length; i++) {
            System.out.println(finalMaze[i]);
        } 
        
    }

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        //read in filepath at runtime
        Scanner in = new Scanner(System.in);
        System.out.println("Enter full file path:");
        String path = in.nextLine();
        
        // reads in maze and creates printable Maze
        finalMaze = createMaze(path);
        
        //solve maze prgram
        if (solveMaze(startR,startC)){
            printMaze(finalMaze);
        }
        else{
            System.out.println("Maze is unsolvable");
        }
    }   
   
}
