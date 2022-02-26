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
    	for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int boardVal = boardInt[i][j];
                System.out.print(boardVal + " 	");
                CoordinateType type;
                if (boardVal >= 1) {
                    type = CoordinateType.TERMINAL;
                    this.terminalStates.add(new Coordinate(CoordinateType.TERMINAL,i,j,boardVal));
                } else {
                    type = CoordinateType.CURRENT;
                }
                board[i][j] = new Coordinate(type, i, j, boardInt[i][j]);
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
