public class Board {
    int height;
    int width;
    Integer[][] board;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new Integer[height][width];
    }
}
