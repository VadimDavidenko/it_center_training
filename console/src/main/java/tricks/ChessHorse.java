package tricks;

/**
 * Created by v.davidenko on 20.03.2015.
 */

/**
 * Путешествие коня по шахматной доске (с усовершенствованной
 * эвристикой доступности (рассчет на ход вперед)) для всех
 * 64-х начальных положений коня.

 * Задание:
 * Одной из наиболее интересных шахматных головоломок является задача
 * о путешествии коня, впервые предложенная математиком Эйлером.
 * Вопрос заключается в следующем: может ли шахматная фигура, называемая
 * конем, обойти все 64 клетки шахматной доски, побывав на каждой из них
 * только один раз.
 *
 * Мы можем разработать "эвристику доступности" путем классификации
 * каждой клетки в соответствии с ее доступностью (в терминах хода конем,
 * конечно) и перемещения коня на наиболее недоступную клетку. Мы пометим
 * двумерный массив accessibility числами, указывающими, со скольких
 * клеток доступна каждая клетка.
 *
 * Напишите вариант программы Путешествия Коня, используя эвристику
 * доступности. В любом случае конь должен ходить на клетку с наименьшим
 * числом доступности. В случае равенства чисел доступности для разных
 * клеток, программа решала бы,какую клетку выбрать, просматривая вперед
 * достижимость клеток из этих альтернативных клеток.
 * Ваша программа должна ходить на клетку, с которой
 * следующий ход достигал бы клетки с наименьшим числом доступности.
 */

public class ChessHorse {
    private static int[][]  board = new int[8][8];                   // шахматная доска
    private static int[] horizontal = {2,1,-1,-2,-2,-1,1,2};         // горизонтальные перемещения 8-ми ходов
    private static int[] vertical = {-1,-2,-2,-1,1,2,2,1};           // вертикальные перемещения 8-ми ходов
    private static int[][] accessibility = {                         // значения доступности клеток доски
            {2,3,4,4,4,4,3,2},
            {3,4,6,6,6,6,4,3},
            {4,6,8,8,8,8,6,4},
            {4,6,8,8,8,8,6,4},
            {4,6,8,8,8,8,6,4},
            {4,6,8,8,8,8,6,4},
            {3,4,6,6,6,6,4,3},
            {2,3,4,4,4,4,3,2}};
    private static int numberOfAttempts = 0;

    public static void main(String[] args) {
        moveHorseOnTheBoard();
        printBoard();
    }

    // Обход конем всей доски
    public static void moveHorseOnTheBoard() {
        int i, j, k, n, m, last = 0, min, min1, min2, row = 0, column = 0, currentRow, currentColumn;

        for(k=0; k<8; k++) {                             // цикл для 64-х начальных положений коня
            for(n=0; n<8 && last != 64; n++){

                for(i=0; i<8; i++)
                    for(j=0; j<8; j++) board[i][j] = 0;
                currentRow = k;
                currentColumn = n;
                board[currentRow][currentColumn] = 1;

                for(i=1; i<=64; i++) {                   // цикл для всего пути коня по доске
                    min = 9;
                    min1 = 10;

                    for(j=0; j<8; j++) {                 // выбор хода из 8-ми возможных 1-й раз для
                        currentRow += vertical[j];       // определение значения наименьшей доступности
                        currentColumn += horizontal[j];

                        if(currentRow >= 0  && currentRow <= 7)           // проверка возможности хода
                            if(currentColumn >= 0 && currentColumn <= 7)
                                if(board[currentRow][currentColumn] == 0)

                                    if(accessibility[currentRow][currentColumn] < min)
                                        min = accessibility[currentRow][currentColumn];

                        currentRow -= vertical[j];
                        currentColumn -= horizontal[j];
                    }

                    for(j=0; j<8; j++) {                      // выбор хода из 8-ми возможных 2-й раз
                        currentRow += vertical[j];             // для определения оптимальной следующей
                        currentColumn += horizontal[j];        // клетки из нескольких альтернативных

                        if(currentRow >= 0  && currentRow <= 7)
                            if(currentColumn >= 0 && currentColumn <= 7)
                                if(board[currentRow][currentColumn] == 0)

                                    if(accessibility[currentRow][currentColumn] == min) {
                                        min2 = 9;

                                        for(m=0; m<8; m++) {               // выбор хода из 8-ми возможных
                                            currentRow += vertical[m];      // для рассчета на ход вперед
                                            currentColumn += horizontal[m];

                                            if(currentRow >= 0  && currentRow <= 7)
                                                if(currentColumn >= 0 && currentColumn <= 7)
                                                    if(board[currentRow][currentColumn] == 0)

                                                        if(accessibility[currentRow][currentColumn] < min2)
                                                            min2 = accessibility[currentRow][currentColumn];  						                                                      // доступности

                                            currentRow -= vertical[m];
                                            currentColumn -= horizontal[m];
                                        }

                                        if(min2 < min1) {
                                            min1 = min2;
                                            row = currentRow;              // координаты
                                            column = currentColumn;        // выбранной клетки
                                        }
                                    }
                        currentRow -= vertical[j];
                        currentColumn -= horizontal[j];
                    }
                    if(currentRow != row) {                  // проверка: был ли сделан ход
                        currentRow = row;
                        currentColumn = column;
                        board[currentRow][currentColumn] = i+1;
                        last = i+1;
                    }
                }
            }
        }
    }

    // печать шахматной доски
    public static void printBoard() {
        System.out.println();
        System.out.println(" Horse travel on the tricks board");
        System.out.println("---------------------------------------");
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++)
                if(board[i][j] != 0)
                    if (board[i][j] < 10)
                        System.out.print(" 0" + board[i][j] + "  ");
                    else
                        System.out.print(" " + board[i][j] + "  ");
                else
                    System.out.print(" **  ");
            if (i != 7) System.out.print("\n\n");
        }
        System.out.println("\n---------------------------------------");
    }


}


