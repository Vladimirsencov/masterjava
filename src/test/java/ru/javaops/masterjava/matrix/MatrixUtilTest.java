package ru.javaops.masterjava.matrix;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ranges.Range;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Vladimir_Sentso on 17.08.2016.
 */
public class MatrixUtilTest {

    int matrixA[][];
    int matrixB[][];
    @Before
    public void matrixInitialization(){
        int matrixARows = 1025;
        int matrixAColumns = 1025;
        int matrixBRows = 1025;
        int matrixBColumns = matrixARows;
        Random random  = new Random();
        matrixA=new int[matrixARows][matrixAColumns];
        matrixB = new int[matrixBRows][matrixBColumns];
        for (int i = 0; i < matrixARows; i++) {
            for (int j = 0; j < matrixAColumns; j++) {
                matrixA[i][j]=random.nextInt();
            }
        }
        for (int i = 0; i < matrixBRows; i++) {
            for (int j = 0; j < matrixBColumns; j++) {
                matrixB[i][j]=random.nextInt();
            }
        }

    }

    @Test
    public void singleThreadMultiplyWithOptimization() throws Exception {
        int matrixC[][] = MatrixUtil.singleThreadMultiply(matrixA,matrixB);
        int matrixC1[][] = MatrixUtil.singleThreadMultiplyWithOptimization(matrixA,matrixB);
        for (int i = 0; i < matrixC.length; i++) {
            Assert.assertArrayEquals(matrixC[i],matrixC1[i]);
        }

    }

    @Test
    public void multiThreadMultiply() throws Exception {
        int matrixC[][] = MatrixUtil.multiThreadMultiply(matrixA,matrixB);
        int matrixC1[][] = MatrixUtil.singleThreadMultiply(matrixA,matrixB);
        for (int i = 0; i < matrixC.length; i++) {
            Assert.assertArrayEquals(matrixC[i],matrixC1[i]);
        }
    }

}