package game;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Вадим on 10.03.2015.
 *
 * Game "X" and "O" on the 3x3 desk (console application)
 * Gamer plays with program and may start first or second turn.
 * Game is continue until all cells on the desk filled in.
 */
public class GameXO {
    static char[][] board = {{'*','*','*'},{'*','*','*'},{'*','*','*'}};

    public static void main(String[] args) throws IOException {
        int result = 0, person;
        // Start game
        person = whoMoveFirst();
        do {
            if (person == 1){
                gamerMove();
                result = checkBoardForWinner();
                if (result != 0) break;
                computerMove();
                result = checkBoardForWinner();
            } else if (person == 2) {
                computerMove();
                result = checkBoardForWinner();
                if (result != 0) break;
                gamerMove();
                result = checkBoardForWinner();
            }
        } while (!isBoardFull() && result == 0);
        // Game result
        showGameResult(result);
    }

    // Returns 1 if gamer is first and 2 if computer is
    public static int whoMoveFirst() {
        int person;
        do {
            System.out.print("Choose who move first\n1 - you, 2 - computer: ");
            Scanner scanner = new Scanner(System.in);
            person = scanner.nextInt();
        } while (person != 1 && person != 2);
        return person;
    }

    // Returns the number of selected cell on the board (1...9)
    public static int inputCellNumber() {
        Scanner scanner = new Scanner(System.in);
        int selectedCell;
        do {
            System.out.print("Enter cell number: ");
            selectedCell = scanner.nextInt();
            if (selectedCell < 1 || selectedCell > 9) {
                System.out.println("Wrong number! Try again.");
            }
        } while (selectedCell < 1 || selectedCell > 9);
        return selectedCell;
    }

    public static void gamerMove() {
        boolean isSet = false;
        int i=0, j=0;
        showBoard();
        do {
            int cellNumber = inputCellNumber();
            if (cellNumber > 0 && cellNumber < 4) {
                i = 0;
                j = cellNumber - 1;
            } else if (cellNumber > 3 && cellNumber < 7) {
                i = 1;
                j = cellNumber - 4;
            } else if (cellNumber > 6 && cellNumber < 10) {
                i = 2;
                j = cellNumber - 7;
            }
            if (board[i][j] != '*') {
                System.out.println("This cell is already used. Try another one.");
            } else {
                board[i][j] = 'X';
                isSet = true;
            }
        } while (!isSet);
    }

    public static void computerMove() {
        // 1st step
        boolean isEmpty = true;
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                if (board[i][j] == 'O') isEmpty = false;
        if (isEmpty) {
            if (board[1][1] == 'X') board[0][0] = 'O';
            else board[1][1] = 'O';     // the best cell on the board
            return;
        }
        // Next steps
        // Check for double "O" and then double "X" in lines
        char c = 'O';
        for (int n=1; n<=2; n++, c = 'X') {
            // Check 3 horizontal lines
            for (int i = 0; i < 3; i++) {
                if (board[i][0] == c && board[i][1] == c && board[i][2] == '*') {
                    board[i][2] = 'O';
                    return;
                }
                if (board[i][0] == c && board[i][2] == c && board[i][1] == '*') {
                    board[i][1] = 'O';
                    return;
                }
                if (board[i][1] == c && board[i][2] == c && board[i][0] == '*') {
                    board[i][0] = 'O';
                    return;
                }
            }
            // Check 3 vertical lines
            for (int j = 0; j < 3; j++) {
                if (board[0][j] == c && board[1][j] == c && board[2][j] == '*') {
                    board[2][j] = 'O';
                    return;
                }
                if (board[0][j] == c && board[2][j] == c && board[1][j] == '*') {
                    board[1][j] = 'O';
                    return;
                }
                if (board[1][j] == c && board[2][j] == c && board[0][j] == '*') {
                    board[0][j] = 'O';
                    return;
                }
            }
            // Check left -> right diagonal
            if (board[0][0] == c && board[1][1] == c && board[2][2] == '*') {
                board[2][2] = 'O';
                return;
            }
            if (board[0][0] == c && board[2][2] == c && board[1][1] == '*') {
                board[1][1] = 'O';
                return;
            }
            if (board[2][2] == c && board[1][1] == c && board[0][0] == '*') {
                board[0][0] = 'O';
                return;
            }
            // Check right -> left diagonal
            if (board[0][2] == c && board[1][1] == c && board[2][0] == '*') {
                board[2][0] = 'O';
                return;
            }
            if (board[2][0] == c && board[0][2] == c && board[1][1] == '*') {
                board[1][1] = 'O';
                return;
            }
            if (board[2][0] == c && board[1][1] == c && board[0][2] == '*') {
                board[0][2] = 'O';
                return;
            }
        }
        // Set "O" in the 1st found empty cell in order
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                if (board[i][j] == '*') {
                    // some special logic for 1st cell :)
                    if (i == 0 && j == 0) {
                        if (board[1][1] == 'O' && board[2][2] == '*') {
                            board[0][0] = 'O';
                            return;
                        }
                    }
                    else {
                        board[i][j] = 'O';
                        return;
                    }
                }

    }

    // Searches for 3 filled cell in some line and returns result: 1 - Gamer win, 2 - Computer win
    // If found nothing then returns 0
    public static int checkBoardForWinner() {
        // Horizontals
        for (int i=0; i<3; i++) {
            if (board[i][0] == 'X' && board[i][1] == 'X' && board[i][2] == 'X') return 1;
            if (board[i][0] == 'O' && board[i][1] == 'O' && board[i][2] == 'O') return 2;
        }
        // Verticals
        for (int j=0; j<3; j++) {
            if (board[0][j] == 'X' && board[1][j] == 'X' && board[2][j] == 'X') return 1;
            if (board[0][j] == 'O' && board[1][j] == 'O' && board[2][j] == 'O') return 2;
        }
        // Diagonals
        if ((board[0][0] == 'X' && board[1][1] == 'X'&& board[2][2] == 'X') ||
                (board[2][0] == 'X' && board[1][1] == 'X'&& board[0][2] == 'X')) return 1;
        if ((board[0][0] == 'O' && board[1][1] == 'O'&& board[2][2] == 'O') ||
                (board[2][0] == 'O' && board[1][1] == 'O'&& board[0][2] == 'O')) return 2;
        return 0;
    }

    public static void showGameResult(int result) {
        System.out.println("----------------------");
        showBoard();
        switch (result) {
            case 1: {
                System.out.println("Congratulations! You are win!");
                break;
            }
            case 2: {
                System.out.println("Sorry, but your computer bits you...");
                break;
            }
            case 0: {
                System.out.println("Game over. Nobody win.");
                break;
            }
        }
    }

    public static boolean isBoardFull() {
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                if (board[i][j] == '*') return false;
        return true;
    }

    public static void showBoard() {
        int n = 1;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++) {
                if (board[i][j] == 'X' || board[i][j] == 'O')
                    System.out.print("[" + board[i][j] + "]");
                else
                    System.out.print(" " + n + " ");
                n++;
            }
            System.out.println();
        }

    }

}
