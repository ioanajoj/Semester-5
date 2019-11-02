#include "pch.h"
#include <iostream>
#include "Matrix.h"
#include <cstdlib>
#include <ctime>

#include "ThreadPool.h"

int** createMatrix(unsigned rows, unsigned columns) {
	int** matrix = new int*[rows];
	for (unsigned i = 0; i < rows; ++i)
		matrix[i] = new int[columns];

	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < columns; j++)
			matrix[i][j] = rand() % 100;
	}

	return matrix;
}

int main()
{
	std::cout << "Hello World!\n";

	srand(time(NULL));

	Matrix matrix1 = Matrix(500, 1000, createMatrix(500, 1000));
	Matrix matrix2 = Matrix(1000, 500, createMatrix(1000, 500));

	//std::cout << matrix1 << std::endl;
	//std::cout << matrix2 << std::endl;

	std::cout << "Compute sum matrix.." << std::endl;
	Matrix sum_matrix = matrix1 + matrix2;
	//std::cout << "Computed sum matrix: " << sum_matrix << std::endl;

	std::cout << "Compute product matrix.." << std::endl;
	Matrix product_matrix = matrix1 * matrix2;
	//std::cout << "Computed product matrix: " << product_matrix << std::endl;


	std::cout << "Bye World!";
}
