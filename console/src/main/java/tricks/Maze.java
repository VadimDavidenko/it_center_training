package tricks;

import java.util.Random;

/**
 * Created by v.davidenko on 21.01.2016.
 *
 * Creates model of maze in random way
 */
public class Maze {

    private static final int MAZE_WIDTH = 30;  // maze width
    private static final int MAZE_LENGTH = 40;  // maze length
    private static final int STEPS_NUMBER = MAZE_WIDTH * MAZE_LENGTH / 4;  // min passing maze steps number
    private int[][] maze;

    public Maze() {
        maze = new int[MAZE_WIDTH][MAZE_LENGTH];
    }

    public static void main(String[] args) {
        Maze maze = new Maze();

        System.out.println("Please, wait...");
        maze.mazeGenerator();
        maze.printMaze();
    }

    public void mazeGenerator() {
        int rowIn = 0;
        int columnIn = 0;
        int rowOut, columnOut;
        int resultR = 0;
        int resultL = 0;

        Random rand = new Random();
        int randomBase = (int)System.currentTimeMillis();

        while(resultR < STEPS_NUMBER || resultL < STEPS_NUMBER) {

            // подготовка нового поля лабиринта
            for(int i=0; i < MAZE_WIDTH; i++) {
                for (int j=0; j < MAZE_LENGTH; j++) {
                    maze[i][j] = 1;
                }
            }
            // случайный выбор входа в лабиринт
            rowIn = rand.nextInt(randomBase) % MAZE_WIDTH;
            if(rowIn == 0 || rowIn == MAZE_WIDTH - 1)
            columnIn = 1 + rand.nextInt(randomBase) % (MAZE_LENGTH - 2);
            else columnIn = (MAZE_LENGTH - 1) * (rand.nextInt(randomBase) % 2);
            maze[rowIn][columnIn] = 3;

            // случайный выбор выхода из лабиринта
            do {
                rowOut = rand.nextInt(randomBase) % MAZE_WIDTH;
                if(rowOut == 0 || rowOut == MAZE_WIDTH - 1)
                columnOut = 1 + rand.nextInt(randomBase) % (MAZE_LENGTH - 2);
                else columnOut = (MAZE_LENGTH - 1) * (rand.nextInt(randomBase) % 2);
                maze[rowOut][columnOut] = 0;
            }
            while(rowOut == rowIn && columnOut == columnIn);

            // генерация лабиринта
            for(int i=1; i <= MAZE_WIDTH - 2; i++) {
                for (int j=1; j <= MAZE_LENGTH - 2; j++) {
                    int n = rand.nextInt(randomBase) % 5;
                    if (n < 3) maze[i][j] = 0;
                }
            }
            // проверка лабиринта на проходимость
            resultR = mazeTraverse(rowIn, columnIn, 1, false);		// по правой стене
            resultL = mazeTraverse(rowIn, columnIn, 0, false);		// по левой стене
        }
        // отметки пути через лабиринт
        if (resultL < resultR) {
            mazeTraverse(rowIn, columnIn, 0, true);
        } else {
            mazeTraverse(rowIn, columnIn, 1, true);
        }
    }

    public int mazeTraverse(int rowIn, int columnIn, int way, boolean printWay) {
        int row = rowIn;
        int column = columnIn;
        int k1, v_temp, h_temp;
        int v = 0, h = 0, steps = 0;

        // определение начальных координат направления
        if(column == 0) v = 1;               // на восток
        if(column == MAZE_LENGTH - 1) v = -1;      // на запад
        if(row == 0) h = 1;                  // на юг
        if(row == MAZE_WIDTH - 1) h = -1;         // на север

        do {                                              // цикл прохождения лабиринта
            for(int k=1; k<=4; k++) {                     // цикл 1-го шага
                if(way == 1) k1 = k;
                else k1 = k + 4;

                switch(k1) {
                    case 1:  case 7:                      // проверка справа (1-й этап)
                        if(h == 0 && v == 1) {            // поворот направо (c востока)
                            h_temp = 1;
                            v_temp = 0;
                            break;
                        }
                        if(h == 1 && v == 0) {            // с юга
                            h_temp = 0;
                            v_temp = -1;
                            break;
                        }
                        if(h == 0 && v == -1) {           // с запада
                            h_temp = -1;
                            v_temp = 0;
                            break;
                        }
                        if(h == -1 && v == 0) {           // с севера
                            h_temp = 0;
                            v_temp = 1;
                            break;
                        }
                    case 2:  case 6:                      // проверка впереди (2-й этап)
                        h_temp = h;
                        v_temp = v;
                        break;
                    case 3:  case 5:                      // проверка слева (3-й этап)
                        if(h == 0 && v == 1) {            // поворот налево (с востока)
                            h_temp = -1;
                            v_temp = 0;
                            break;
                        }
                        if(h == 1 && v == 0) {            // с юга
                            h_temp = 0;
                            v_temp = 1;
                            break;
                        }
                        if(h == 0 && v == -1) {           // с запада
                            h_temp = 1;
                            v_temp = 0;
                            break;
                        }
                        if(h == -1 && v == 0) {           // с севера
                            h_temp = 0;
                            v_temp = -1;
                            break;
                        }
                    default:                              // разворот назад (4-й этап)
                        h_temp = -h;
                        v_temp = -v;
                        break;
                }

                if(row + h_temp < 0 || row + h_temp > MAZE_WIDTH - 1) return 0;        // проверка
                if(column + v_temp < 0 || column + v_temp > MAZE_LENGTH - 1) return 0;  // границ

                if(maze[row + h_temp][column + v_temp] != 1) {      // проверка свободного
                    h = h_temp;                                     // места впереди
                    v = v_temp;
                    row += h;                                       // шаг вперед
                    column += v;
                    steps ++;
                    break;
                }
            }
            if (printWay) {
                if (maze[row][column] == 0) {
                    maze[row][column] = 3;
                }
            }
        }
        while((column != 0) && (column != MAZE_LENGTH - 1) && (row != 0) && (row != MAZE_WIDTH - 1));

        if(row != rowIn && column != columnIn) return steps;                    // кол-во шагов
        return 0;
    }

    public void printMaze(){
        System.out.println();
        for(int i=0; i < MAZE_WIDTH; i++) {
            System.out.print(" ");
            for(int j=0; j < MAZE_LENGTH; j++) {
                switch (maze[i][j]){
                    case 0:
                        System.out.print("   ");
                        break;
                    case 1:
                        System.out.print("XXX");
                        break;
                    case 3:
                        System.out.print(" . ");
                        break;
                    default:
                }
            }
            System.out.println();
        }
    }

}
