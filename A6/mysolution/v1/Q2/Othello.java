import java.io.*;
import java.util.*;

public class Othello {

  int turn;
  int winner;
  int board[][];

  //add required class variables here

  public Othello(String filename) throws Exception {
    File file = new File(filename);
    Scanner sc = new Scanner(file);
    turn = sc.nextInt();
    board = new int[8][8];
    for (int i = 0; i < 8; ++i) {
      for (int j = 0; j < 8; ++j) {
        board[i][j] = sc.nextInt();
      }
    }
    winner = -1;
    //Student can choose to add preprocessing here
  }

  //add required helper functions here

  public int boardScore() {
    /* Complete this function to return num_black_tiles - num_white_tiles if turn = 0,
     * and num_white_tiles-num_black_tiles otherwise.
     */
    int white = 0;
    int black = 0;
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        if (board[i][j] == 0) {
          black++;
        } else if (board[i][j] == 1) {
          white++;
        }
      }
    }

    return turn == 0 ? black - white : white - black;
  }

  public int bestMove(int k) {
    /* Complete this function to build a Minimax tree of depth k (current board being at depth 0),
     * for the current player (siginified by the variable turn), and propagate scores upward to find
     * the best move. If the best move (move with max score at depth 0) is i,j; return i*8+j
     * In case of ties, return the smallest integer value representing the tile with best score.
     *
     * Note: Do not alter the turn variable in this function, so that the boardScore() is the score
     * for the same player throughout the Minimax tree.
     */
    return 0;
  }

  public ArrayList<Integer> fullGame(int k) {
    /* Complete this function to compute and execute the best move for each player starting from
     * the current turn using k-step look-ahead. Accordingly modify the board and the turn
     * at each step. In the end, modify the winner variable as required.
     */
    return new ArrayList<Integer>();
  }
}
