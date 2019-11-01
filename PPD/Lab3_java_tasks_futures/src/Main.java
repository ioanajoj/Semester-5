import domain.Matrix;

import java.util.Random;

/**
 * @author joj on 11/1/2019
 **/
public class Main {

    private static int NO_OF_THREADS = 16;

    public static void main(String[] args) {
        System.out.println("Hello World");

        int[][] m1 = createMatrix(50, 70);
        int[][] m2 = createMatrix(70, 50);

        Matrix matrix1 = new Matrix(50, 50, m1, NO_OF_THREADS);
        Matrix matrix2 = new Matrix(50, 50, m2, NO_OF_THREADS);

        System.out.println(matrix1);
        System.out.println(matrix2);

        System.out.println("Computing sum matrix");
        Matrix sum_matrix = matrix1.add(matrix2);
//        System.out.println(sum_matrix);

        System.out.println("Computing product matrix");
        Matrix product_matrix = matrix1.multiply(matrix2);
//        System.out.println(product_matrix);

        System.out.println("Bye World!");
    }

    private static int[][] createMatrix( final int rows, final int columns ) {
        int[][] matrix = new int[rows][columns];

        Random random = new Random();
        for( int i = 0; i < rows; i++ ) {
            for( int j = 0; j < columns; j++ )
                matrix[i][j] = random.nextInt()/100;
        }

        return matrix;
    }
}
