import java.io.*;
import java.util.*;

public class Othello {

  int turn;
  int winner;
  int board[][];

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
    sc.close();
    winner = -1;
  }

  public int boardScore() {
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
    int bestScore = Integer.MIN_VALUE;
    int i = 0;
    int j = 0;
    int[][] processBoard = copyBoard(board);
    ArrayList<ArrayList<Integer>> possiblePositions = getAllPositions(
      processBoard,
      turn
    );
    System.out.println(possiblePositions);
    if (possiblePositions.size() == 0) {
      turn = getOpponent(turn);
      return bestMove(k);
    }

    for (ArrayList<Integer> position : possiblePositions) {
      processBoard = takeMove(position, processBoard, turn);
      int score = minimax(1, false, k, processBoard, getOpponent(turn));
      if (score > bestScore) {
        bestScore = score;
        i = position.get(0);
        j = position.get(1);
      } else if (score == bestScore) {
        if (position.get(0) * 8 + position.get(1) < i * 8 + j) {
          i = position.get(0);
          j = position.get(1);
        }
      }
      processBoard = copyBoard(board);
    }
    return i * 8 + j;
  }

  public ArrayList<Integer> fullGame(int k) {
    ArrayList<Integer> result = new ArrayList<>();
    while (true) {
      ArrayList<Integer> stats = sumBlacksAndWhites(board);
      int Blacks = stats.get(0);
      int Whites = stats.get(1);
      int Total = stats.get(2);

      if (Total == 64 || Blacks == 0 || Whites == 0) {
        setWinner(stats);
        break;
      }
      int b = bestMove(k);

      int j = b % 8;
      int i = (b - j) / 8;
      ArrayList<Integer> position = new ArrayList<>();
      position.add(i);
      position.add(j);
      takeMove(position, board, turn);
      result.add(b);
      turn = getOpponent(turn);
    }
    return result;
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

  private int currentBoardScore(int[][] currentBoard) {
    int white = 0;
    int black = 0;
    for (int i = 0; i < currentBoard.length; i++) {
      for (int j = 0; j < currentBoard.length; j++) {
        if (currentBoard[i][j] == 0) {
          black++;
        } else if (currentBoard[i][j] == 1) {
          white++;
        }
      }
    }
    return turn == 0 ? black - white : white - black;
  }

  private ArrayList<Integer> sumBlacksAndWhites(int[][] currentBoard) {
    ArrayList<Integer> result = new ArrayList<>();
    int white = 0;
    int black = 0;
    for (int i = 0; i < currentBoard.length; i++) {
      for (int j = 0; j < currentBoard.length; j++) {
        if (currentBoard[i][j] == 0) {
          black++;
        } else if (currentBoard[i][j] == 1) {
          white++;
        }
      }
    }
    result.add(black);
    result.add(white);
    result.add(white + black);
    return result;
  }

  private void setWinner(ArrayList<Integer> stats) {
    if (stats.get(0) > stats.get(1)) {
      winner = 0;
    } else if (stats.get(0) < stats.get(1)) {
      winner = 1;
    }
  }

  private int[][] copyBoard(int[][] currentBoard) {
    int copy[][] = new int[8][8];
    for (int i = 0; i < 8; ++i) System.arraycopy(
      currentBoard[i],
      0,
      copy[i],
      0,
      8
    );
    return copy;
  }

  private int[][] takeMove(
    ArrayList<Integer> position,
    int[][] currentBoard,
    int myturn
  ) {
    int r = position.get(0);
    int c = position.get(1);
    currentBoard[r][c] = myturn;
    int[][] directions = {
      { -1, -1 },
      { -1, 0 },
      { -1, 1 },
      { 0, -1 },
      { 0, 1 },
      { 1, -1 },
      { 1, 0 },
      { 1, 1 },
    };
    for (int[] direction : directions) {
      int r1 = r + direction[0];
      int c1 = c + direction[1];
      ArrayList<ArrayList<Integer>> toBeTurned = new ArrayList<>();
      if (
        checkBoardConditions(r1, c1, currentBoard) &&
        currentBoard[r1][c1] == getOpponent(myturn)
      ) {
        while (checkBoardConditions(r1, c1, currentBoard)) {
          if (currentBoard[r1][c1] == -1) {
            break;
          } else if (currentBoard[r1][c1] == myturn) {
            for (ArrayList<Integer> entry : toBeTurned) {
              currentBoard[entry.get(0)][entry.get(1)] = myturn;
            }
            break;
          }

          ArrayList<Integer> e = new ArrayList<>();
          e.add(r1);
          e.add(c1);
          toBeTurned.add(e);
          r1 += direction[0];
          c1 += direction[1];
        }
      }
    }

    return currentBoard;
  }

  private boolean checkWinner(int[][] board) {
    ArrayList<Integer> stats = sumBlacksAndWhites(board);

    int newTotal = stats.get(2);
    int newBlacks = stats.get(0);
    int newWhites = stats.get(1);
    if (newTotal == 64 || newBlacks == 0 || newWhites == 0) {
      return true;
    }
    return false;
  }

  private boolean checkBoardConditions(int r, int c, int[][] currentBoard) {
    return (
      r >= 0 && r < currentBoard.length && c >= 0 && c < currentBoard[0].length
    );
  }

  private ArrayList<ArrayList<Integer>> insertPossibleMove(
    ArrayList<ArrayList<Integer>> moves,
    int r,
    int c
  ) {
    ArrayList<Integer> validPosition = new ArrayList<>();
    validPosition.add(r);
    validPosition.add(c);
    if (!moves.contains(validPosition)) {
      moves.add(validPosition);
    }
    return moves;
  }

  private ArrayList<ArrayList<Integer>> getAllPositions(
    int[][] CurrentBoard,
    int turn
  ) {
    ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
    for (int row = 0; row < CurrentBoard.length; row++) {
      for (int col = 0; col < CurrentBoard[0].length; col++) {
        if (CurrentBoard[row][col] == turn) {
          processMoves(row, col, moves, CurrentBoard, turn);
        }
      }
    }
    return moves;
  }

  private int getOpponent(int turngiven) {
    return turngiven == 0 ? 1 : 0;
  }

  private ArrayList<ArrayList<Integer>> processMoves(
    int row,
    int col,
    ArrayList<ArrayList<Integer>> moves,
    int[][] currentBoard,
    int turn
  ) {
    int opponent = getOpponent(turn);
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (i == 0 && j == 0) {
          continue;
        }
        int r = row + i;
        int c = col + j;

        if (
          checkBoardConditions(r, c, currentBoard) && currentBoard[r][c] == -1
        ) {
          continue;
        }

        while (
          checkBoardConditions(r, c, currentBoard) &&
          currentBoard[r][c] == opponent &&
          currentBoard[r][c] != -1
        ) {
          r += i;
          c += j;
        }
        if (
          checkBoardConditions(r, c, currentBoard) && currentBoard[r][c] == -1
        ) {
          moves = insertPossibleMove(moves, r, c);
        }
      }
    }
    return moves;
  }

  private int minimax(
    int depth,
    boolean maximizingPlayer,
    int k,
    int[][] currentBoard,
    int turn
  ) {
    if (checkWinner(currentBoard) || depth == k) {
      int score = currentBoardScore(currentBoard);
      printBoard(currentBoard);
      return score;
    }

    if (maximizingPlayer) {
      int bestScore = Integer.MIN_VALUE;

      int[][] currentBoard2 = copyBoard(currentBoard);

      ArrayList<ArrayList<Integer>> possiblePositions = getAllPositions(
        currentBoard2,
        turn
      );
      if (possiblePositions.size() == 0) {
        int val = minimax(
          depth + 1,
          !maximizingPlayer,
          k,
          currentBoard,
          getOpponent(turn)
        );
        return val;
      }

      for (ArrayList<Integer> position : possiblePositions) {
        currentBoard2 = takeMove(position, currentBoard2, turn);

        int score = minimax(
          depth + 1,
          false,
          k,
          currentBoard2,
          getOpponent(turn)
        );
        bestScore = Math.max(score, bestScore);
        currentBoard2 = copyBoard(currentBoard);
      }

      return bestScore;
    } else {
      int bestScore = Integer.MAX_VALUE;
      int[][] currentBoard2 = copyBoard(currentBoard);

      ArrayList<ArrayList<Integer>> possiblePositions = getAllPositions(
        currentBoard2,
        turn
      );

      if (possiblePositions.size() == 0) {
        int val = minimax(
          depth + 1,
          !maximizingPlayer,
          k,
          currentBoard,
          getOpponent(turn)
        );
        return val;
      }

      for (ArrayList<Integer> position : possiblePositions) {
        currentBoard2 = takeMove(position, currentBoard2, turn);

        int score = minimax(
          depth + 1,
          true,
          k,
          currentBoard2,
          getOpponent(turn)
        );
        bestScore = Math.min(score, bestScore);

        currentBoard2 = copyBoard(currentBoard);
      }
      return bestScore;
    }
  }
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
