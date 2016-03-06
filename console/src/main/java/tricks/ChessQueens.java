package tricks;
import java.util.Random;

/**
 * Created by v.davidenko on 24.02.2015.
 */

/**
 * Задача о расстановке 8-ми Ферзей на шахматной доске.
 * Нужно расставить на пустой шахматной доске восемь ферзей так,
 * чтобы ни один из них не "атаковал" другого, т.е. никакие два
 * ферзя ни стояли бы на одном и том же столбце или на одной и той
 * же строке или на одной и той же диагонали.
 * Программа выполнятся до первой расстановки всех восьми ферзей
 * (cо случайным выбором следующей клетки для каждого из 8-ми Ферзей).
*/

public class ChessQueens {
    private static int[][] board = new int[8][8];    // шахматная доска
    private static int numberOfAttempts = 0;

    public static void main(String[] args) {
        PlaceQueensOnChessBoard();
        PrintChessBoard();
    }

    // Расстановка 8-ми Ферзей на шахматной доске
    public static void PlaceQueensOnChessBoard() {
        int k, i, j, n, x, last, number, row, column;

        last = 0;
        for(numberOfAttempts = 1; last != 8; numberOfAttempts++) {
            // очистка доски
            for(i=0; i<8; i++) {
                for (j = 0; j < 8; j++) {
                    board[i][j] = 0;
                }
            }
            // одна расстановка Ферзей
            last = 0;
            x = -1;
            for(k=1; k<=8; k++) {
                // определение кол-ва свободных клеток
                n = 0;
                for (i = 0; i < 8; i++) {
                    for (j = 0; j < 8; j++) {
                        if (board[i][j] == 0) n++;
                    }
                }
                if(n == 0) break;
                // случайный выбор свободной клетки
                Random rand = new Random();
                number = rand.nextInt(n) + 1;
                row = 0;
                column = 0;
                n = 0;
                for(i=0; i<8; i++) {
                    for (j = 0; j < 8; j++) {
                        if (board[i][j] == 0) {
                            n++;
                            if (n == number) {
                                row = i;
                                column = j;
                            }
                        }
                    }
                }
                // проверка: был ли сделан ход
                if(x != row) {
                    last ++;
                    // установка следующего ферзя
                    board[row][column] = last;
                    x = row;
                }
                // исключение клеток доски
                // по вертикали
                i = row;
                j = column;
                for(i=0; i<8; i++) {
                    if (board[i][column] <= 0) {
                        board[i][column] = -1;
                    }
                }
                // по горизонтали
                for(j=0; j<8; j++) {
                    if (board[row][j] <= 0) {
                        board[row][j] = -1;
                    }
                }
                // налево вверх
                i = row;
                j = column;
                while(i != 0 && j != 0) {
                    i --;
                    j --;
                    if(board[i][j] <= 0) board[i][j] = -1;
                }
                // направо вниз
                i = row;
                j = column;
                while(i != 7 && j != 7) {
                    i ++;
                    j ++;
                    if(board[i][j] <= 0) board[i][j] = -1;
                }
                // направо вверх
                i = row;
                j = column;
                while(i != 0 && j != 7) {
                    i --;
                    j ++;
                    if(board[i][j] <= 0) board[i][j] = -1;
                }
                // налево вниз
                i = row;
                j = column;
                while(i != 7 && j != 0) {
                    i ++;
                    j --;
                    if(board[i][j] <= 0) board[i][j] = -1;
                }
            }
        }
        numberOfAttempts--;
    }

    // Печать шахматной доски с расположением 8-ми Ферзей
    public static void PrintChessBoard() {
        System.out.println();
        System.out.println(" 8 Queens");
        System.out.println("--------------------------");
        for(int i=0; i<8; i++) {
            System.out.print("|");
            for(int j=0; j<8; j++) {
                if (board[i][j] > 0) {
                    System.out.print(" " + board[i][j] + " ");  //  Ферзь
                }
                else {
                    System.out.print(" * ");    // пустая клетка
                }
            }
            System.out.println("|");
        }
        System.out.println("--------------------------");
        System.out.print(" Number of attempts:  " + numberOfAttempts);
        System.out.println();
    }
}
