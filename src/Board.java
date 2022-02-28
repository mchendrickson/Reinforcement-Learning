import java.text.DecimalFormat;
import java.util.ArrayList;

public class Board {
    int height;
    int width;
    Integer[][] boardInt;
    Coordinate[][] board;
    ArrayList<Coordinate> terminalStates;

    /**
     * Board constructor
     * @param height
     * @param width
     * @param board
     */
    public Board(int height, int width, Integer[][] board) {
        this.height = height;
        this.width = width;
        this.boardInt = board;
        this.board = new Coordinate[height][width];
        initializeBoard();
    }

    /**
     * Getter for board
     * @return board
     */
    public Coordinate[][] getBoard() {
        return board;
    }

    /**
     * Convert the integer double array to a coordinate double array
     */
    private void initializeBoard() {
        terminalStates = new ArrayList<Coordinate>();
    	for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int boardVal = boardInt[row][col];
                CoordinateType type;
                if (boardVal != 0) {
                    type = CoordinateType.TERMINAL;
                    this.terminalStates.add(new Coordinate(CoordinateType.TERMINAL,col,row,boardVal));
                } else {
                    type = CoordinateType.CURRENT;
                }
                board[row][col] = new Coordinate(type, col, row, boardVal);
            }
            
        }
    }

    /**
     * A debugger method to print out the current values of the coordinate board
     */
    public void printBoard() {
    	DecimalFormat df = new DecimalFormat("#.##");


        for (int i = 0; i < this.height ; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(df.format(this.board[i][j].value) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Getter for height
     * @return height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Getter for width
     * @return height
     */
    public int getWidth() {
        return width;
    }


}
