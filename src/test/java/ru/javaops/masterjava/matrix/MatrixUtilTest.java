package ru.javaops.masterjava.matrix;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ranges.Range;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.*;
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

    @Test
    public void compliteSquareTest(){
        Assert.assertEquals(5, max(2,max(3,max(4,5))));
        Assert.assertEquals(4, (int)ceil(pow(2.0, ceil(log(3)/log(2)))));
        int A [][] = {{1,2,2},{5,4 ,3}};
        int B [][] =  {{1,2,2},{5,4 ,3}};
        int[][][] res = MatrixUtil.completeToSquare(A,B);
        System.out.println(Arrays.deepToString(res));
        Assert.assertEquals(res[0].length, 4);
    }
}