// Nicholas Ross
// 1214952
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private int boardArray[][];
    public Board parent = null;

    public int x = -1;
    public int y = -1;
    public int score = 0;

    public Board(int score){
        this.score = score;
    }

    public Board(int[][] board){
        boardArray = board;
        score = score();
    }

    public Board(int[][] board, Board parent, int x, int y){
        boardArray = board;
        this.parent = parent;
        score = score();
        this.x = x;
        this.y = y+1;
    }

    public int[][] copyBoard(int[][] board) {
        int[][] copy = new int[board.length][];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return copy;
    }


    public static void printBoard(int[][] board) {
        for (int[] row : board) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }


    public boolean isPossibleMove(int x, int y, int[][] board, int player) {
        if (board[x][y] != 0) return false;
        for (int deltaX = -1; deltaX < 2; deltaX++) {
            for (int deltaY = -1; deltaY < 2; deltaY++) {
                if (deltaX == 0 && deltaY == 0) continue;
                if (isPossibleMove(x, y, board, player, deltaX, deltaY)) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isPossibleMove(int x, int y, int[][] board, int player, int deltaX, int deltaY) {
        int other = -player;
        //if (board[x][y] != 0) return false;
        x += deltaX;
        y += deltaY;
        if (x < 0 || y < 0 || x >= board.length || y >= board.length) return false;
        if (board[x][y] != other) return false;
        while (true) {
            x += deltaX;
            y += deltaY;
            if (x < 0 || y < 0 || x >= board.length || y >= board.length) return false;
            if (board[x][y] == player) return true;
            if (board[x][y] == 0) return false;
        }
    }


    public void capture(int x, int y, int[][] board, int player) {
        for (int deltaX = -1; deltaX < 2; deltaX++) {
            for (int deltaY = -1; deltaY < 2; deltaY++) {
                if (deltaX == 0 && deltaY == 0) continue;
                if (isPossibleMove(x, y, board, player, deltaX, deltaY)) {
                    capture(x, y, board, player, deltaX, deltaY);
                }
            }
        }
    }


    public void capture(int x, int y, int[][] board, int player, int deltaX, int deltaY) {
        board[x][y] = player;
        while (true) {
            x += deltaX;
            y += deltaY;
            if (board[x][y] == player) return;
            board[x][y] = player;
        }
    }


    // Return next possible moves
    public List<Board> expand(int player) {
        List<Board> nextBoards = new ArrayList<Board>();

        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                if (isPossibleMove(i, j, boardArray, player)) {
                    int[][] nextBoard = copyBoard(boardArray);
                    capture(i, j, nextBoard, player);
                    nextBoards.add(new Board(nextBoard,this, j, i));
                }
            }
        }
        return nextBoards;
    }


    private int score() {
        int[][] board = this.boardArray;
        int b = 0, o = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j]==1)
                    b++;
                if(board[i][j]==-1)
                    o++;
            }

        }
        return b-o;
    }

    public int[][] boardArrays(){
        return boardArray;
    }

    public boolean isValid(){
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                    if(!(boardArray[i][j]==1||boardArray[i][j]==0||boardArray[i][j]==-1))
                       return false;
                }
            }
        return true;
    }
}
