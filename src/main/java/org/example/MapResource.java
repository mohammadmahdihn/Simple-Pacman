package org.example;

import java.util.Random;

public class MapResource {

    private static final String MAP_1_TO_STRING =
            "1111111111111111111111111111111" +
                    "1*0*0*1*0*1*0*0*0*0*0*0*0*0*0*1" +
                    "1111101010111011111110101111101" +
                    "1*0*1*0*1*0*0*1*0*0*1*0*0*0*0*1" +
                    "1011101011111110111010111110101" +
                    "1*0*0*1*0*0*1*0*1*0*1*0*1*0*0*1" +
                    "1110101011101010101111101011101" +
                    "1*0*0*1*1*0*0*0*1*0*0*1*0*1*0*1" +
                    "1010111010111010111010111110101" +
                    "1*1*1*0*1*0*0*0*1*0*0*1*0*1*0*1" +
                    "1010101111101010111010101010101" +
                    "1*1*0*0*0*0*1*1*0*1*0*0*1*0*0*1" +
                    "1010111010101011101010111111111" +
                    "1*0*0*0*0*0*1*0*0*1*0*0*1*0*0*1" +
                    "1010101110101010111010101011101" +
                    "1*0*1*0*0*1*1*1X0*0*0*0*1*0*0*1" +
                    "1110111110101010111111101010101" +
                    "1*1*1*0*0*1*0*1*0*0*0*0*1*1*0*1" +
                    "1010101110101011101010101010101" +
                    "1*0*1*0*0*1*1*0*0*1*1*0*0*1*0*1" +
                    "1011101011101111101010101110101" +
                    "1*1*0*0*0*0*1*0*1*0*1*1*0*1*1*1" +
                    "1010101010111010101110111010101" +
                    "1*0*1*1*1*0*1*0*0*0*1*0*1*1*1*1" +
                    "1110101011101010101011101010101" +
                    "1*0*0*1*0*1*0*0*1*0*0*0*0*1*1*1" +
                    "1011101011111111111010101010101" +
                    "1*1*0*0*0*0*0*0*0*0*1*0*0*0*1*1" +
                    "1010111111111110101011101111101" +
                    "1*0*1*0*0*0*0*0*0*0*0*0*0*0*0*1" +
                    "1111111111111111111111111111111";

    private static final String MAP_2_TO_STRING =
            "1111111111111111111111111111111" +
                    "1*1*1*0*0*0*0*0*1*1*0*0*0*0*0*1" +
                    "1010101011111110101010111110101" +
                    "1*1*1*1*0*1*0*0*1*0*1*0*0*0*1*1" +
                    "1010101110111011101111111110111" +
                    "1*0*0*0*1*0*1*1*0*0*0*1*0*1*0*1" +
                    "1011111011101010111010101011101" +
                    "1*1*0*0*1*0*0*1*0*1*1*0*1*0*0*1" +
                    "1010101010111010111011111010101" +
                    "1*0*0*1*1*1*1*0*0*1*1*0*0*0*1*1" +
                    "1011101010101110101010101011101" +
                    "1*0*0*0*1*1*0*0*1*1*1*0*0*1*1*1" +
                    "1011101010111110101010111010101" +
                    "1*0*1*0*1*1*0*0*0*1*0*1*1*1*0*1" +
                    "1010101110101010101011101011111" +
                    "1*0*0*0*1*1*1*1X0*1*0*0*1*0*0*1" +
                    "1011111110101110111010111010101" +
                    "1*1*0*1*0*0*1*0*1*0*1*0*0*0*0*1" +
                    "1010101010101011101110101010101" +
                    "1*0*1*0*1*1*0*1*0*0*0*0*1*1*1*1" +
                    "1111111110101110111010101010101" +
                    "1*0*0*0*0*0*0*0*0*0*1*1*0*0*0*1" +
                    "1010111110111111101011101011101" +
                    "1*1*0*0*0*0*0*1*0*1*0*0*1*0*1*1" +
                    "1011111110111010101111111011101" +
                    "1*0*0*0*1*1*0*1*1*0*0*0*1*0*0*1" +
                    "1010111010101110101011101011111" +
                    "1*1*0*1*0*1*0*1*0*0*0*0*1*0*0*1" +
                    "1011101111111111101011111011101" +
                    "1*0*0*0*0*0*0*0*0*1*0*0*0*0*0*1" +
                    "1111111111111111111111111111111";

    private static final String MAP_3_TO_STRING =
            "1111111111111111111111111111111" +
                    "1*0*1*0*0*1*0*0*0*0*0*0*1*0*0*1" +
                    "1110101010111010101111101010101" +
                    "1*0*1*1*1*0*1*1*1*0*0*1*0*1*1*1" +
                    "1011111011101010101110101010101" +
                    "1*0*0*1*0*1*0*1*1*1*0*1*1*0*1*1" +
                    "1110101010111110111011101011101" +
                    "1*0*1*0*0*1*0*0*0*0*0*0*0*1*0*1" +
                    "1011101011101011111010111011111" +
                    "1*0*1*1*1*0*0*0*0*0*0*0*1*1*0*1" +
                    "1010101010101110101010101010101" +
                    "1*1*0*0*0*1*0*0*0*1*0*1*1*0*1*1" +
                    "1011101010101011101011101011101" +
                    "1*1*0*0*1*0*1*0*0*0*0*0*1*0*0*1" +
                    "1011111110101110101010101011101" +
                    "1*0*0*1*0*1*0*1X0*1*0*1*0*1*0*1" +
                    "1111101011101010101010101110111" +
                    "1*1*0*0*1*0*1*0*0*0*0*1*1*0*0*1" +
                    "1010111010111111111111101010101" +
                    "1*1*0*1*1*0*0*0*0*0*0*0*1*0*0*1" +
                    "1011101011111110101111101010101" +
                    "1*0*1*1*0*0*0*0*1*1*0*0*0*0*1*1" +
                    "1010101011111111101010101110101" +
                    "1*1*1*0*0*0*1*0*0*1*0*0*1*0*0*1" +
                    "1010101111101011111011101011111" +
                    "1*1*1*0*0*0*1*1*0*0*1*0*0*0*0*1" +
                    "1110101111111010101110111010101" +
                    "1*0*1*0*1*0*0*0*1*0*0*1*0*1*0*1" +
                    "1011111011111010101010101011101" +
                    "1*0*0*0*0*0*0*0*1*0*0*0*0*0*0*1" +
                    "1111111111111111111111111111111";

    private static final char[][] MAP_1 = stringTo2DChar(MAP_1_TO_STRING);
    private static final char[][] MAP_2 = stringTo2DChar(MAP_2_TO_STRING);
    private static final char[][] MAP_3 = stringTo2DChar(MAP_3_TO_STRING);

    public static char[][] getMap1() {
        return MAP_1;
    }

    public static char[][] getMap2() {
        return MAP_2;
    }

    public static char[][] getMap3() {
        return MAP_3;
    }

    private static char[][] stringTo2DChar(String mapToString) {
        int index = 0;
        char[][] maze = new char[31][31];
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 31; j++) {
                maze[i][j] = mapToString.charAt(index);
                index++;
            }
        }
        return maze;
    }

    public static char[][] getRandomMap(int sizeOf2DArray) {
        char[][] maze = new char[sizeOf2DArray][sizeOf2DArray];
        char[][] mazeBody = mazeBodyMaker(sizeOf2DArray - 2, sizeOf2DArray - 2);
        for (int i = 0; i < sizeOf2DArray; i++) {
            for (int j = 0; j < sizeOf2DArray; j++) {
                if (i == 0 || i == sizeOf2DArray - 1 || j == 0 || j == sizeOf2DArray - 1) {
                    maze[i][j] = '1';
                } else if(i==15 && j==15){
                    maze[i][j] = 'X';
                } else {
                    maze[i][j] = mazeBody[i - 1][j - 1];
                }
            }
        }
        deleteRandomWallsFromMaze(maze, sizeOf2DArray);
        return maze;
    }

    private static char[][] mazeBodyMaker(int rows, int columns) {
        char[][] littleMaze = new char[rows][columns];
        if (rows == 1) {
            for (int i = 0; i < columns; i++) {
                if (i % 2 == 0) {
                    littleMaze[0][i] = '*';
                } else {
                    littleMaze[0][i] = '0';
                }
            }
            return littleMaze;
        } else if (columns == 1) {
            for (int i = 0; i < rows; i++) {
                if (i % 2 == 0) {
                    littleMaze[i][0] = '*';
                } else {
                    littleMaze[i][0] = '0';
                }
            }
            return littleMaze;
        }
        return randomMazeSplitter(rows, columns, littleMaze);
    }

    private static char[][] randomMazeSplitter(int rows, int columns, char[][] littleMaze) {
        Random randomInt = new Random();
        int rowOrColumnToSplit = Math.abs(randomInt.nextInt()) % 2;
        if (rowOrColumnToSplit == 0) {
            return splitWithARow(rows, columns, littleMaze, randomInt);
        } else {
            return splitWithAColumn(rows, columns, littleMaze, randomInt);
        }
    }

    private static char[][] splitWithAColumn(int rows, int columns, char[][] littleMaze, Random randomInt) {
        int numOfConditions = (columns - 1) / 2;
        int columnToSplit = Math.abs(2 * (randomInt.nextInt() % numOfConditions) + 1);
        char[][] leftMaze = mazeBodyMaker(rows, columnToSplit);
        char[][] rightMaze = mazeBodyMaker(rows, columns - (columnToSplit + 1));
        int numOfWallsInAColumn = (rows - 1) / 2;
        int whichWallDoesNotExist = Math.abs(2 * (randomInt.nextInt() % numOfWallsInAColumn));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j < columnToSplit) {
                    littleMaze[i][j] = leftMaze[i][j];
                } else if (j == columnToSplit) {
                    if (i == whichWallDoesNotExist) littleMaze[i][j] = '0';
                    else littleMaze[i][j] = '1';
                } else {
                    littleMaze[i][j] = rightMaze[i][j - (columnToSplit + 1)];
                }
            }
        }
        return littleMaze;
    }

    private static char[][] splitWithARow(int rows, int columns, char[][] littleMaze, Random randomInt) {
        int numOfConditions = (rows - 1) / 2;
        int rowToSplit = Math.abs(2 * (randomInt.nextInt() % numOfConditions) + 1);
        char[][] upperMaze = mazeBodyMaker(rowToSplit, columns);
        char[][] lowerMaze = mazeBodyMaker(rows - (rowToSplit + 1), columns);
        int numOfWallsInARow = (columns - 1) / 2;
        int whichWallDoesNotExist = Math.abs(2 * (randomInt.nextInt() % numOfWallsInARow));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i < rowToSplit) {
                    littleMaze[i][j] = upperMaze[i][j];
                } else if (i == rowToSplit) {

                    if (j == whichWallDoesNotExist) littleMaze[i][j] = '0';
                    else littleMaze[i][j] = '1';
                } else {
                    littleMaze[i][j] = lowerMaze[i - (rowToSplit + 1)][j];
                }
            }
        }
        return littleMaze;
    }

    private static void deleteRandomWallsFromMaze(char[][] maze, int sizeOf2DArray) {
        for (int i = 1; i < sizeOf2DArray - 1; i++) {
            for (int j = 1; j < sizeOf2DArray - 1; j++) {
                if (j % 2 + i % 2 == 1) {
                    Random random = new Random();
                    int randomInt = Math.abs(random.nextInt() % 4);
                    if (randomInt == 0) {
                        maze[i][j] = '0';
                    }
                }
            }
        }
    }

    public static char[][] getCopyOfThisMap(char[][] map, int sizeOf2DArray) {
        char[][] newMap = new char[sizeOf2DArray][sizeOf2DArray];
        for (int i = 0; i < sizeOf2DArray; i++) {
            System.arraycopy(map[i], 0, newMap[i], 0, sizeOf2DArray);
        }
        return newMap;
    }
}
