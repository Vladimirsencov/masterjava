package ru.javaops.masterjava.matrix;

import java.util.Arrays;

/**
 * gkislin
 * 03.07.2016
 */
public class MainMatrix {
    // Multiplex matrix
    private static final int MATRIX_SIZE = 1000;
    private static final int THREAD_NUMBER = 10;

    public static void main(String[] args) {
        int matrixA1[][]={{1,2,4},{5,6,7},{8,9,1}};
        int matrixB1[][]={{1,5,6},{7,9,8},{7,6,1}};
        System.out.println(Arrays.deepToString( MatrixUtil.singleThreadMultiply(matrixA1, matrixB1)));
        //[[43, 47, 26], [96, 121, 85], [78, 127, 121]]
        //from MathCad
        //43 47	  26
        //96 121  85
        //78 127 121

        final int[][] matrixA = new int[MATRIX_SIZE][MATRIX_SIZE];
        final int[][] matrixB = new int[MATRIX_SIZE][MATRIX_SIZE];

        long start = System.currentTimeMillis();
        int[][] matrixC =  MatrixUtil.singleThreadMultiply(matrixA, matrixB);
        System.out.println("Single thread multiplication time, sec: " + (System.currentTimeMillis() - start)/1000.);
        start = System.currentTimeMillis();
        matrixC =  MatrixUtil.singleThreadMultiplyWithOptimization(matrixA, matrixB);
        System.out.println("Single thread multiplicationWithOptimization time, sec: " + (System.currentTimeMillis() - start)/1000.);

        matrixC=MatrixUtil.multiThreadMultiply(matrixA, matrixB);
        System.out.println("Multi thread multiplication time, sec: " + (System.currentTimeMillis() - start)/1000.);
        // TODO compare with matrixC;


    }
}
