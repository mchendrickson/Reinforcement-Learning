public class Board {
    int height;
    int width;
    Integer[][] boardInt;
    Coordinate[][] board;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.boardInt = new Integer[height][width];
        this.board = new Coordinate[height][width];
    }

    public Board(int height, int width, Integer[][] board) {
        this.height = height;
        this.width = width;
        this.boardInt = board;
        this.board = new Coordinate[height][width];
        initializeBoard();
    }
    
    public Coordinate[][] getBoard(){
    	return board;
    }
    
    private void initializeBoard(){
    	for(int i = 0; i < height; i++) {
    		for(int j = 0; j < width; j++) {
    			int boardVal = boardInt[i][j];
    			CoordinateType type;
    			if(boardVal >= 1) { type = CoordinateType.TERMINAL;} else { type = CoordinateType.CURRENT;}
    			board[i][j] = new Coordinate(type, i, j, boardInt[i][j]);
    		}
    	}
    }

}
