package domain;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author joj on 11/1/2019
 **/
public class Matrix {
    int rows, columns;
    int[][] matrix;
    int noOfThreads;

    public Matrix( final int rows, final int columns, final int noOfThreads ) {
        this.rows = rows;
        this.columns = columns;
        this.noOfThreads = noOfThreads;
        this.matrix = new int[this.rows][this.columns];
    }

    public Matrix(int rows, int columns, int[][] matrix, final int noOfThreads) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = matrix;
        this.noOfThreads = noOfThreads;
    }

    public Matrix add( final Matrix matrix ) {
        int rows = Math.max(this.rows, matrix.rows);
        int columns = Math.max(this.columns, matrix.columns);
        Matrix sum_matrix = new Matrix(rows, columns, this.noOfThreads);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        long startTime = System.nanoTime();
        for( int i = 0; i < rows * columns; i++ ){
            SumTask sumTask = new SumTask(sum_matrix, this, matrix, i);
            executor.execute(sumTask);
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Total execution time: " + elapsedTime/1000000 + " millis.");

        return sum_matrix;
    }

    public Matrix multiply( final Matrix matrix ) {
        int rows = this.rows;
        int columns = matrix.columns;
        Matrix product_matrix = new Matrix(rows, columns, this.noOfThreads);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        long startTime = System.nanoTime();
        for( int i = 0; i < rows * columns; i++ ){
            ProductTask productTask = new ProductTask(product_matrix, this, matrix, i);
            executor.execute(productTask);
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Total execution time: " + elapsedTime/1000000 + " millis.");

        return product_matrix;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Matrix with " + rows + " rows and " + columns + " columns:\n");
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++)
                result.append(this.matrix[i][j]).append(" ");
            result.append("\n");
        }
        return result.toString();
    }
}
