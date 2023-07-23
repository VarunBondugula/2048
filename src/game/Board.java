package game;

import java.util.ArrayList;


public class Board {
    private int[][] gameBoard;               // the game board array
    private ArrayList<BoardSpot> openSpaces; // the ArrayList of open spots: board cells without numbers.

    /**
     * Zero-argument Constructor: initializes a 4x4 game board.
     **/
    public Board() {
        gameBoard = new int[4][4];
        openSpaces = new ArrayList<>();
    }

    /**
     * One-argument Constructor: initializes a game board based on a given array.
     * 
     * @param board the board array with values to be passed through
     **/
    public Board ( int[][] board ) {
        gameBoard = new int[board.length][board[0].length];
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                gameBoard[r][c] = board[r][c];
            }
        }
        openSpaces = new ArrayList<>();
    }

    public void updateOpenSpaces() {
        openSpaces = new ArrayList<BoardSpot>();
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                if (gameBoard[r][c] == 0) {
                    openSpaces.add(new BoardSpot(r, c));
                }
            }
        }
    

    }

    public void addRandomTile() {
        int t = StdRandom.uniform(0, openSpaces.size());
        BoardSpot a = openSpaces.get(t);
        double v = StdRandom.uniform(0.0, 1.0);

        if (v < 0.10) {
            gameBoard[a.getRow()][a.getCol()] = 4;
        }
        else {
            gameBoard[a.getRow()][a.getCol()] = 2;
        }

        

    }

    public void swipeLeft() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length-1; c++ ) {
                if (gameBoard[r][c] == 0) {
                    for ( int a = c; a < gameBoard[r].length-1; a++ ) {

                        gameBoard[r][a] = gameBoard[r][a+1];
                        gameBoard[r][a+1] = 0;

                    }

                } 
            }
            for ( int a = 0; a < gameBoard[r].length-1; a++ ) {
                if (gameBoard[r][a] == 0) {
                    gameBoard[r][a] = gameBoard[r][a+1];
                    gameBoard[r][a+1] = 0;
                }
            }
        }

    }

    public void mergeLeft() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length-1; c++ ) {
                if (gameBoard[r][c] == gameBoard[r][c+1]) {
                    gameBoard[r][c] *= 2;
                    gameBoard[r][c+1] = 0;
                } 
            }
        }
    }

    public void rotateBoard() {
        transpose();
        flipRows();
    }

    public void transpose() {

        int[][] board = new int[gameBoard.length][gameBoard[0].length];

        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                board[c][r] = gameBoard[r][c];
            }
        }

        gameBoard = board;


    }


    public void flipRows() {
        
        int[][] board = new int[gameBoard.length][gameBoard[0].length];

        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                board[r][gameBoard[r].length-1-c] = gameBoard[r][c];
            }
        }

        gameBoard = board;

    }

    public void makeMove(char letter) {
        if (letter == 'L') {
            swipeLeft();
            mergeLeft();
            swipeLeft();
        }
        else if (letter == 'R') {
            flipRows();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            flipRows();
        }
        else if (letter == 'U') {
            transpose();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            transpose();
        }
        else if (letter == 'D') {
            transpose();
            flipRows();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            flipRows();
            transpose();
        }
        
        
    }

    /**
     * @return the status of the game -- lost or not lost
     **/
    public boolean isGameLost() {
        return openSpaces.size() == 0;
    }

    /**
     * Shows a final score when the game is lost.
     **/
    public int showScore() {
        int score = 0;
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                score += gameBoard[r][c];
            }
        }
        return score;
    }

    /**
     * Prints the board as integer values in the text window.
     **/
    public void print() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }
    /**
     * Used by TextDriver.
     **/
    public void printOpenSpaces() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                for ( BoardSpot bs : getOpenSpaces() ) {
                    if (r == bs.getRow() && c == bs.getCol()) {
                        g = "**";
                    }
                }
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }

    public Board(long seed) {
        StdRandom.setSeed(seed);
        gameBoard = new int[4][4];
    }

    public ArrayList<BoardSpot> getOpenSpaces() {
        return openSpaces;
    }

    public int[][] getBoard() {
        return gameBoard;
    }
}
