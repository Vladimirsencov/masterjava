package ru.javaops.masterjava.matrix;


import static java.lang.Runtime.getRuntime;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {
    private MatrixUtil() {
    }

    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
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
                    sum += matrixA[i][k] * matrixB[k][j];
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
        if (matrixA.length != matrixB[0].length) throw new IllegalArgumentException();
        final int matrixARows = matrixA.length;
        final int matrixBColumns = matrixB[0].length;
        final int matrixBRows = matrixB.length;
        final int matrixC[][] = new int[matrixARows][matrixBColumns];

        if (matrixB[0].length < getRuntime().availableProcessors()) {
            return singleThreadMultiplyWithOptimization(matrixA, matrixB);
        }

        Thread[] threads = null;

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
                threads = new Thread[getRuntime().availableProcessors() * 4 + 1];
            }
        }



        for (int i = 0; i < threads.length; i++) {
            final int j = i;
            threads[i] = new Thread(() -> matrixMultiply(matrixA, matrixB, matrixC, j, 1));
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

    private static void matrixMultiply(int[][] matrixA, int[][] matrixB, int[][] matrixRes, int startIndex, int endIndex) {
        if (matrixA.length != matrixB[0].length) throw new IllegalArgumentException();
        final int matrixARows = matrixA.length;
        final int matrixBRows = matrixB.length;
        final int buffer[] = new int[matrixBRows];

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

}



