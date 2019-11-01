package domain;

/**
 * @author joj on 11/1/2019
 **/
public class ProductTask implements Runnable {
    Matrix product_matrix, matrix1, matrix2;
    int cell;

    public ProductTask(Matrix product_matrix, Matrix matrix1, Matrix matrix2, int cell) {
        this.product_matrix = product_matrix;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.cell = cell;
    }


    @Override
    public void run() {
//        System.out.println("Multiplying " + cell);
        int product = 0;
        int row = cell / matrix2.columns;
        int column = cell % matrix2.columns;
        for (int i = 0; i < matrix1.columns; i++)
            product += matrix1.matrix[row][i] * matrix2.matrix[i][column];
        product_matrix.matrix[row][column] = product;
    }
}
