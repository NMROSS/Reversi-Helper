//Nicholas Ross
//1214952
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Move {

    int nodes = 0;

    // alpha beta pruning search
    public Board ABP(Board startBoard, int depth, int player, Board a, Board b) {
        boolean maxPlayer = false;
        // get potential moves
        List<Board> potentialMoves = startBoard.expand(player);
        if (player == 1)
            maxPlayer = true;

        if (depth == 0)
            return startBoard;

        // no potential moves
        if (potentialMoves.size() == 0){
            startBoard.x = 0;
            startBoard.y = -1;
            return startBoard;
        }

        if (maxPlayer) {
            Board tmp;
            for (Board board : potentialMoves) {
                nodes++;
                tmp = ABP(board, depth - 1, -player, a, b);
                if (a.score < tmp.score)
                    a = tmp;
                if (b.score <= a.score)
                    break;
            }
            return a;

        } else { // Min player
            Board tmp;
            for (Board board : potentialMoves) {
                nodes++;
                tmp = ABP(board, depth - 1, -player, a, b);
                if (b.score >= tmp.score)
                    b = tmp;

                if (b.score <= a.score)
                    break;
            }
            return b;
        }


    }

    // Iterative Deepening Alpha Beta Pruning Search
    public void IDABP(Board startBoard, int player, int runTime) {
        Board result = new Board(0);
        long sTime = System.nanoTime();
        int depth = 1;
        // increase depth by 1
        for (; ; depth++) {
            if ((System.nanoTime() - sTime) / 1000000000.0 >= runTime)
                break;

            // set alpha beta values
            Board alpha = new Board(Integer.MIN_VALUE);
            Board beta = new Board(Integer.MAX_VALUE);

            result = ABP(startBoard, depth, player, alpha, beta);
            // Check if no more possible moves available on board
            if (result.x ==0 && result.y==-1) {
                System.out.println("Move " + (char) (result.x + 97) + ", " + result.y + " Nodes " +
                        nodes + " Depth " + depth + " minmax " + result.score);
                break;
            }
        }
        // get the next best move and print stats for it
        while (result.parent != null) {
            if (result.parent.parent == null) {
                System.out.println("Move " + (char) (result.x + 97) + ", " + result.y + " Nodes " +
                        nodes + " Depth " + depth + " minmax " + result.score);
            }
            result = result.parent;
        }
    }


    public static void main(String[] args) {

        try {
            InputStreamReader ir = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(ir);
            String line = null;
            int[][] newBoard = new int[8][8];
            int j = 0;
            int player = 0;
            int time = 0;
            while ((line = br.readLine()) != null) {

                // Extract player
                if (line.matches("^(B|O)\\s\\d+$")) {
                    if (line.toCharArray()[0] == 'B') {
                        player = 1;
                    } else
                        player = -1;
                    // get time to run search
                    time = Integer.parseInt(line.substring(2, line.length()));
                }

                // Attempt to generate board from given data
                if (line.length() == 10 && !line.contains("abc")) {
                    for (int i = 2; i < line.length(); i++) {
                        char element = line.toCharArray()[i];
                        if (element == '.')
                            newBoard[j][i - 2] = 0;
                        else if (element == 'B')
                            newBoard[j][i - 2] = 1;
                        else if (element == 'O')
                            newBoard[j][i - 2] = -1;

                    }
                    j++;
                }
            }

            Board startBoard = new Board(newBoard);

            Move calculateNextMove = new Move();

            // start alpha beta pruning search
            calculateNextMove.IDABP(startBoard, player, time);

        } catch (Exception e) {
            System.err.println(e);
        }


/*
- abcdefgh
1 ........
2 ........
3 ........
4 ..BO....
5 ..OB....
6 ........
7 ........
8 ........
O 10

*/

    }
}