package domain;

/**
 * @author joj on 11/1/2019
 **/
public class SumTask implements Runnable {
    Matrix sum_matrix, matrix1, matrix2;
    int cell;

    public SumTask(Matrix sum_matrix, Matrix matrix1, Matrix matrix2, int cell) {
        this.sum_matrix = sum_matrix;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.cell = cell;
    }


    @Override
    public void run() {
        System.out.println("Adding cell " + cell);
        int row = cell / matrix1.columns;
        int column = cell % matrix1.columns;
        int sum = 0;
        if (row < matrix1.rows && column < matrix1.columns) sum += matrix1.matrix[row][column];
        if (row < matrix2.rows && column < matrix2.columns) sum += matrix2.matrix[row][column];
        sum_matrix.matrix[row][column] = sum;
    }
}
