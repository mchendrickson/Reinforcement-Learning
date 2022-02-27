import java.text.DecimalFormat;
import java.util.ArrayList;

public class Board {
    int height;
    int width;
    Integer[][] boardInt;
    Coordinate[][] board;
    ArrayList<Coordinate> terminalStates;


    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.boardInt = new Integer[height][width];
        this.board = new Coordinate[height][width];
        this.terminalStates = new ArrayList<Coordinate>();
    }

    public Board(int height, int width, Integer[][] board) {
        this.height = height;
        this.width = width;
        this.boardInt = board;
        this.board = new Coordinate[height][width];
        initializeBoard();
    }

    public Coordinate[][] getBoard() {
        return board;
    }

    private void initializeBoard() {
        terminalStates = new ArrayList<Coordinate>();
    	for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int boardVal = boardInt[row][col];
                System.out.print(boardVal + " 	");
                CoordinateType type;
                if (boardVal >= 1) {
                    type = CoordinateType.TERMINAL;
                    this.terminalStates.add(new Coordinate(CoordinateType.TERMINAL,row,col,boardVal));
                } else {
                    type = CoordinateType.CURRENT;
                }
                board[row][col] = new Coordinate(type, col, row, boardInt[row][col]);
            }
            System.out.println();
        }
    }

    public void printBoard() {
    	DecimalFormat df = new DecimalFormat("#.##");
    	
    	
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(df.format(this.board[i][j].value) + "\t");
            }
            System.out.println();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


}
