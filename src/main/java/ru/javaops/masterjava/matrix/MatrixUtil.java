package ru.javaops.masterjava.matrix;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;
import static java.lang.Runtime.getRuntime;

/**
 * gkislin
 * 03.07.2016
 */
final public class MatrixUtil {
    private MatrixUtil() {
    }

    public static int[][] singleThreadMultiply(final int[][] matrixA, final int[][] matrixB) {
        if (matrixA.length != matrixB[0].length) {
            throw new IllegalArgumentException();
        }

        int matrixARows = matrixA.length;
        int matrixBColumns = matrixB[0].length;
        int matrixC[][] = new int[matrixARows][matrixBColumns];
        int matrixBRows = matrixB.length;

        for (int i = 0; i < matrixARows; i++) {
            for (int j = 0; j < matrixBColumns; j++) {
                int sum = 0;
                for (int k = 0; k < matrixBRows; k++) {
                    sum += matrixA[i][k]
                            * matrixB[k][j];
                }
                matrixC[i][j] = sum;
            }
        }
        return matrixC;
    }

    public static int[][] singleThreadMultiplyWithOptimization(int[][] matrixA, int[][] matrixB) {
        if (matrixA.length != matrixB[0].length) throw new IllegalArgumentException();
        final int matrixARows = matrixA.length;
        final int matrixBColumns = matrixB[0].length;
        final int matrixBRows = matrixB.length;
        final int buffer[] = new int[matrixBRows];
        final int matrixC[][] = new int[matrixARows][matrixBColumns];

        for (int j = 0; j < matrixBColumns; j++) {
            for (int q = 0; q < matrixBRows; q++) {
                buffer[q] = matrixB[q][j];
            }
            for (int i = 0; i < matrixARows; i++) {
                int sum = 0;
                for (int k = 0; k < matrixBRows; k++) {
                    sum += matrixA[i][k] * buffer[k];
                }
                matrixC[i][j] = sum;
            }
        }
        return matrixC;
    }

    public static int[][] multiThreadMultiply(int[][] matrixA, int[][] matrixB) {
        if (matrixA.length != matrixB[0].length) {
            throw new IllegalArgumentException();
        }
        final int matrixARows = matrixA.length;
        final int matrixBColumns = matrixB[0].length;
        final int matrixC[][] = new int[matrixARows][matrixBColumns];

        if (matrixB[0].length < getRuntime().availableProcessors()) {
            return singleThreadMultiplyWithOptimization(matrixA, matrixB);
        }

        Thread[] threads;

        if (matrixB[0].length < getRuntime().availableProcessors() * 4) {
            {
                if (matrixB[0].length % getRuntime().availableProcessors() == 0) {
                    threads = new Thread[getRuntime().availableProcessors()];
                } else {
                    threads = new Thread[getRuntime().availableProcessors() + 1];
                }
            }
        } else {
            if (matrixB[0].length % (getRuntime().availableProcessors() * 4) == 0) {
                threads = new Thread[getRuntime().availableProcessors() * 4];
            } else {
                threads = new Thread[(getRuntime().availableProcessors() * 4) + 1];
            }
        }

        final Map<Integer, Map<Integer, Integer>> indexes = getIndexes(threads, matrixB);

        for (int i = 0; i < threads.length; i++) {
            final int j = i;
            threads[j] = new Thread(() -> matrixMultiply(matrixA, matrixB, matrixC, indexes.get(j).keySet().iterator().next(), indexes.get(j).values().iterator().next()));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

        return matrixC;

    }

    private static void matrixMultiply(final int[][] matrixA, final int[][] matrixB, final int[][] matrixRes, final int startIndex, final int endIndex) {
        if (matrixA.length != matrixB[0].length) {
            throw new IllegalArgumentException();
        }
        System.out.println(startIndex + " " + endIndex);
        final int matrixARows = matrixA.length;
        final int matrixBRows = matrixB.length;
        final int[] buffer = new int[matrixBRows];

        for (int j = startIndex; j < endIndex; j++) {
            for (int q = 0; q < matrixBRows; q++) {
                buffer[q] = matrixB[q][j];
            }
            for (int i = 0; i < matrixARows; i++) {
                int sum = 0;
                for (int k = 0; k < matrixBRows; k++) {
                    sum += matrixA[i][k] * buffer[k];
                }
                matrixRes[i][j] = sum;
            }
        }

    }

    private static Map<Integer, Map<Integer, Integer>> getIndexes(Thread[] threads, int[][] matrix) {
        final Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        int matrixSize = matrix[0].length;
        int poolSize = threads.length;
        if (matrixSize % poolSize == 0) {
            for (int i = 0; i < poolSize; i++) {
                map.put(i, Collections.singletonMap(matrixSize / poolSize * i, matrixSize / poolSize * (i + 1)));
            }
        } else {
            int lastIndex = 0;
            for (int i = 0; i < poolSize - 1; i++) {
                lastIndex = matrixSize / poolSize * (i + 1);
                map.put(i, Collections.singletonMap(matrixSize / poolSize * i, lastIndex));
            }
            map.put(poolSize - 1, Collections.singletonMap(lastIndex, matrixSize));
        }
        return map;
    }

    public static int[][] shtrassenMatrixMultiply(int [][] matrixA, int [][] matrixB){
        if (matrixA.length != matrixB[0].length) {
            throw new IllegalArgumentException();
        }
        int [][][] square = completeToSquare(matrixA,matrixB);
        int [][]squareA = square[0];
        int [][]squareB = square[1];
        //int result = new [int ]
        return null;
    }


     static int[][][] completeToSquare(int[][]matrixA, int[][]matrixB){
        int maxLength = max(matrixA.length,max(matrixA[0].length,max(matrixB.length,matrixB[0].length)));
        int completeToPow2 = (int)ceil(pow(2.0, ceil(log(maxLength)/log(2))));
        int[][][] result = new int[2][completeToPow2][completeToPow2];

            for (int i = 0; i < matrixA.length; i++) {
                System.arraycopy(matrixA[i],0,result[0][i],0,matrixA[i].length);
            }
            for (int i = 0; i < matrixB.length; i++) {
            System.arraycopy(matrixB[i],0,result[1][i],0,matrixB[i].length);
            }
        return result;
    }

}



