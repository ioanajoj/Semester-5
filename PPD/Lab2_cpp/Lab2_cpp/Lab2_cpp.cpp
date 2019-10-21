#include "pch.h"
#include <iostream>
#include "Matrix.h"

void deleteMatrix(Matrix matrix) {
	for (int i = 0; i < matrix.rows; ++i)
		delete[] matrix.matrix[i];
	delete[] matrix.matrix;
}

int main()
{
    std::cout << "Hello World!\n"; 
	int m1[100][100] = { {10, 9, 6, 99}, {3, 6, 11, 5}, {14, 2, 7, 1} };
	int m2[100][100] = { {4, 8, 12}, {17, 4, 6}, {7, 2, 10} };

	Matrix matrix1 = Matrix(3, 4, m1);
	Matrix matrix2 = Matrix(3, 3, m2);

	std::cout << matrix1 << std::endl << matrix2 << std::endl;

	std::cout << "Compute sum.." << std::endl;
	Matrix sum_matrix = matrix1 + matrix2;

	std::cout << "Computed sum matrix: " << sum_matrix << std::endl;

	std::cout << "Cleaning up.." << std::endl;
	deleteMatrix(matrix1);
	deleteMatrix(matrix2);
	deleteMatrix(sum_matrix);

	std::cout << "Bye world!" << std::endl;
}
