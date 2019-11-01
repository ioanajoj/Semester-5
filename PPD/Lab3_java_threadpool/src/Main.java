import domain.Matrix;

/**
 * @author joj on 11/1/2019
 **/
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        int[][] m1 = {{2,1,4}, {0,1,1}, {2,-3,8}};
        int[][] m2 = {{6,3}, {1,1}, {-2,5}};

        Matrix matrix1 = new Matrix(3, 3, m1);
        Matrix matrix2 = new Matrix(3, 2, m2);

        System.out.println(matrix1);
        System.out.println(matrix2);

        System.out.println("Computing sum matrix");
        Matrix sum_matrix = matrix1.add(matrix2);
        System.out.println(sum_matrix);

        System.out.println("Computing product matrix");
        Matrix product_matrix = matrix1.multiply(matrix2);
        System.out.println(product_matrix);

        System.out.println("Bye World!");
    }
}
