  private void printBoard(int board[][]) {
    System.out.println();
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        System.out.print(board[i][j] + "\t");
      }
      System.out.println();
    }
    System.out.println();
  }