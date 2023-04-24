import java.io.*;
import java.util.*;

class Move {

  int turn;
  int row;
  int col;

  public Move(int turn, int row, int col) {
    this.turn = turn;
    this.row = row;
    this.col = col;
  }
}

public class Othello {

  int turn;
  int winner;
  int board[][];
  // add required class variables here

  private int EMPTY = -1;
  private int BLACK_PIECE = 0;
  private int WHITE_PIECE = 1;

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
    printBoard(board);
    // Student can choose to add preprocessing here

  }

  // add required helper functions here

  private int[][] takeTurn(int[][] tryboard, Move mov, int turn) {
    if (mov != null) {
      int row = mov.row;
      int col = mov.col;

      if (tryboard[row][col] == EMPTY) {
        tryboard[row][col] = turn;
        for (int i = -1; i < 2; i++) {
          for (int j = -1; j < 2; j++) {
            if (i == 0 && j == 0) {
              continue;
            } else {
              tryboard = dirnvectorfn(tryboard, row, col, turn, i, j, turn);
            }
          }
        }
      }
    }

    return tryboard;
  }

  private int[][] dirnvectorfn(
    int[][] board,
    int row,
    int column,
    int colour,
    int colDir,
    int rowDir,
    int turn
  ) {
    int currentRow = row + rowDir;
    int currentCol = column + colDir;
    if (!checker(currentRow, currentCol)) {
      return board;
    }

    // Valid Move
    if (
      directionmovecheckerfn(new Move(turn, row, column), board, rowDir, colDir)
    ) {
      while (board[currentRow][currentCol] == opponent(colour)) {
        board[currentRow][currentCol] = colour;
        currentRow = currentRow + rowDir;
        currentCol = currentCol + colDir;
        if (!checker(currentRow, currentCol)) {
          break;
        }
      }
    }
    return board;
  }

  private int opponent(int turn) {
    if (turn == 0) {
      return 1;
    }
    return 0;
  }

  private boolean checker(int row, int col) {
    return !(row < 0 || row >= 8 || col < 0 || col >= 8);
  }

  // Wrong
  private boolean movecheckerfn(Move mov, int[][] board) {
    int otherscolor = opponent(mov.turn);
    int myturn = mov.turn;
    int row = mov.row;
    int col = mov.col;
    boolean result = false;

    if (board[row][col] == -1) {
      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          row = mov.row;
          col = mov.col;
          if (i == 0 && j == 0) {
            continue;
          }
          // Code
          if (checker(row + i, col + j)) {
            if (board[row + i][col + j] == otherscolor) {
              while (board[row + i][col + j] == otherscolor) {
                row = row + i;
                col = col + j;
                if (!checker(row + i, col + j)) {
                  break;
                }
              }

              if (!checker(row, col)) {
                continue;
              }

              row = row + i;
              col = col + j;
              if (checker(row, col)) {
                if (board[row][col] == myturn) {
                  result = true;
                  return result;
                }
              }
            }
          }
        }
      }
    }
    return result;
  }

  private boolean directionmovecheckerfn(
    Move mov,
    int[][] board,
    int i,
    int j
  ) {
    int otherscolor = opponent(mov.turn);
    int myturn = mov.turn;
    int row = mov.row;
    int col = mov.col;
    boolean result = false;

    row = mov.row;
    col = mov.col;
    // Code
    if (checker(row + i, col + j)) {
      if (board[row + i][col + j] == otherscolor) {
        while (board[row + i][col + j] == otherscolor) {
          row = row + i;
          col = col + j;
          if (!checker(row + i, col + j)) {
            break;
          }
        }

        row = row + i;
        col = col + j;
        if (checker(row, col)) {
          if (board[row][col] == myturn) {
            result = true;
            return result;
          }
        }
      }
    }

    return result;
  }

  private ArrayList<Move> allpossiblemovesfn(int[][] board, int tryturn) {
    ArrayList<Move> possmoves = new ArrayList<Move>();
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board[i][j] == -1) {
          Move move = new Move(tryturn, i, j);
          if (movecheckerfn(move, board)) {
            possmoves.add(move);
          }
        }
      }
    }

    return possmoves;
  }

  private int boardScorefn(int[][] board) {
    int blacknum = 0;
    int whitenum = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board[i][j] == 0) {
          blacknum++;
        } else if (board[i][j] == 1) {
          whitenum++;
        }
      }
    }
    return turn == 0 ? blacknum - whitenum : whitenum - blacknum;
  }

  private boolean gameoverfn(int[][] board) {
    boolean boardhasnospace = false;
    int check64 = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board[i][j] != -1) {
          check64++;
        }
      }
    }
    if (check64 == 64) {
      boardhasnospace = true;
    }
    return boardhasnospace;
  }

  private void setwinnerfn(int[][] board) {
    int whitenum = 0;
    int blacknum = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board[i][j] == BLACK_PIECE) {
          blacknum++;
        } else if (board[i][j] == WHITE_PIECE) {
          whitenum++;
        }
      }
    }
    if (blacknum > whitenum) {
      winner = 0; // changes global variable
    } else if (whitenum > blacknum) {
      winner = 1; // changes global variable
    }
  }

  public int boardScore() {
    return boardScorefn(board);
  }

  public int miniMax(
    int[][] board,
    int k,
    boolean maximizingPlayer,
    int tryturn,
    int depth
  ) {
    if (depth == k || gameoverfn(board)) {
      return boardScorefn(board);
    }
    if (allpossiblemovesfn(board, tryturn).size() == 0) {
      return miniMax(board, k, !maximizingPlayer, opponent(tryturn), depth + 1);
    }
    if (maximizingPlayer) {
      int maxEvaluation = Integer.MIN_VALUE;
      ArrayList<Move> possibleMoves = allpossiblemovesfn(board, tryturn);
      if (!possibleMoves.isEmpty()) {
        for (Move move : possibleMoves) {
          int[][] newBoard = getBoardCopyfn(board);
          newBoard = takeTurn(newBoard, move, tryturn);
          int evaluation = miniMax(
            newBoard,
            k,
            false,
            opponent(tryturn),
            depth + 1
          );
          maxEvaluation = Math.max(maxEvaluation, evaluation);
        }
      }
      return maxEvaluation;
    } else {
      int minEvaluation = Integer.MAX_VALUE;
      ArrayList<Move> possibleMoves = allpossiblemovesfn(board, tryturn);
      if (!possibleMoves.isEmpty()) {
        for (Move move : possibleMoves) {
          int[][] newBoard = getBoardCopyfn(board);
          newBoard = takeTurn(newBoard, move, tryturn);

          int evaluation = miniMax(
            newBoard,
            k,
            true,
            opponent(tryturn),
            depth + 1
          );
          minEvaluation = Math.min(minEvaluation, evaluation);
        }
      }
      return minEvaluation;
    }
  }

  public Move getBestMove(int[][] board, int k, int tryturn) {
    boolean many = false;
    ArrayList<Move> manymov = new ArrayList<Move>();
    ArrayList<Move> possibleMoves = allpossiblemovesfn(board, tryturn);
    int bestEvaluation = Integer.MIN_VALUE;
    Move bestMove = null;
    Move bestEvalMove = null;
    if (possibleMoves.size() == 0) {
      turn = opponent(tryturn);
      return new Move(-1, tryturn, bestEvaluation);
    }

    if (!possibleMoves.isEmpty()) {
      for (Move move : possibleMoves) {
        int[][] newBoard = getBoardCopyfn(board);
        newBoard = takeTurn(newBoard, move, tryturn);

        int evaluation = miniMax(newBoard, k, false, opponent(tryturn), 1);

        if (evaluation > bestEvaluation) {
          bestEvaluation = evaluation;
          bestEvalMove = move;
          bestMove = move;

          many = false;
          manymov = new ArrayList<Move>();
        } else if (
          evaluation == bestEvaluation && evaluation != Integer.MIN_VALUE
        ) {
          many = true;
          manymov.add(bestEvalMove);
          manymov.add(move);
        }
      }
    }

    if (many) {
      int val = Integer.MAX_VALUE;
      for (int i = 0; i < manymov.size(); i++) {
        Move temp = manymov.get(i);

        if (temp.row * 8 + temp.col < val) {
          bestMove = temp;
          val = temp.row * 8 + temp.col;
        }
      }
    }

    return bestMove;
  }

  public int bestMove(int k) {
    Move bestmove = getBestMove(board, k, turn);
    return bestmove == null ? 0 : bestmove.row * 8 + bestmove.col;
  }

  public ArrayList<Integer> fullGame(int k) {
    ArrayList<Integer> result = new ArrayList<Integer>();

    while (true) {
      Move move = getBestMove(board, k, turn);
      if (gameoverfn(board)) {
        break;
      }

      if (move.turn == -1) {
        continue;
      }
      board = takeTurn(board, move, turn);
      turn = opponent(turn);
      result.add(move.row * 8 + move.col);
      printBoard(board);
      System.out.println();
    }
    setwinnerfn(board);
    return result;
  }

  public int[][] getBoardCopyfn(int[][] board) {
    int copy[][] = new int[8][8];
    for (int i = 0; i < 8; i++) for (int j = 0; j < 8; j++) {
      copy[i][j] = board[i][j];
    }
    return copy;
  }

  public int[][] getBoardCopy() {
    int copy[][] = new int[8][8];
    for (int i = 0; i < 8; ++i) System.arraycopy(board[i], 0, copy[i], 0, 8);
    return copy;
  }

  public int getWinner() {
    return winner;
  }

  public int getTurn() {
    return turn;
  }

  //   Developer Functions
  private void printBoard(int[][] board) {
    for (int i = 0; i < 8; ++i) {
      for (int j = 0; j < 8; ++j) {
        System.out.print(board[i][j] + "\t");
      }
      System.out.println();
    }
  }

  public static void main(String[] args) throws Exception {
    Othello obj = new Othello("input.txt");
    System.out.println(obj.fullGame(2));
  }
}
