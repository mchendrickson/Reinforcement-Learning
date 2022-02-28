import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Integer[][] boardFile;
    static int boardHeight, boardWidth;

    public static void main(String[] args) throws FileNotFoundException {
        float timeToRun = 0f, probDesiredDirection = 0f, constantReward = 0f;

        if (args.length != 4) {
        	errorAndExit();
        }

        try {
            timeToRun = Float.parseFloat(args[1]);
            probDesiredDirection = Float.parseFloat(args[2]);
            constantReward = Float.parseFloat(args[3]);
        } catch (NumberFormatException e) {
        	errorAndExit();
        }

        //Probability of moving a random direction
        float sigmaPercent = 0.1f;

        openFile(args[0]);
        printBoard(boardFile);
        
        System.out.println();
        
        Board b = new Board(boardHeight, boardWidth, boardFile);
        System.out.println("Running board of height: " + boardHeight + ", width " + boardWidth + " for " + timeToRun + " seconds ");
        System.out.println("Accuracy: " + probDesiredDirection * 100 + "%");
        System.out.println("Epsilon: " + sigmaPercent * 100 + "%"); 
        System.out.println("Constant reward: " + constantReward);
        
        System.out.println();
        
        ReinforcementLearning learning = new ReinforcementLearning(b, timeToRun, probDesiredDirection, constantReward, sigmaPercent);
        
        System.out.println();
    }

    /**
     * Print error messages then crash
     */
    private static void errorAndExit() {
    	 System.out.println("Invalid input.");
         System.out.println("Please input a filename, a learn time, the desired direction percentage, and a constant reward number. ");
         System.exit(1);
    }

    /**
     * Open and read the file, add it to a double array of Integers
     * @param fileName
     * @throws FileNotFoundException
     */
    private static void openFile(String fileName) throws FileNotFoundException {

        File myObj = new File(fileName);
        Scanner myReader = new Scanner(myObj);
        ArrayList<Integer[]> rows = new ArrayList<Integer[]>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] boardRowStr = data.split("\\t");
            Integer[] boardRow = new Integer[boardRowStr.length];
            boardWidth = boardRowStr.length;

            for (int i = 0; i < boardRowStr.length; i++) {
                boardRow[i] = Integer.parseInt(boardRowStr[i]);
            }
            boardHeight = rows.size()+1;
            rows.add(boardRow);
            boardFile = new Integer[rows.size()][boardRow.length];
           
            for (int i = 0; i < rows.size(); i++) {
                boardFile[i] = rows.get(i);
            }


        }

        myReader.close();
    }

    /**
     * Print the formatted board in text
     * @param boardFile
     */
    private static void printBoard(Integer[][] boardFile) {
        for (int i = 0; i < boardFile.length; i++) {
            for (int j = 0; j < boardFile[i].length; j++) {
                System.out.print(boardFile[i][j] + "	");
            }
            System.out.println();
        }
    }


}
