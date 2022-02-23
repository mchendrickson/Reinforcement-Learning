public class Board {
    int height;
    int width;
    int[][] board;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new int[height][width];
    }
}
