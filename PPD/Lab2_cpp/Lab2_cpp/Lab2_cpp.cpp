#include "pch.h"
#include <iostream>
#include "Matrix.h"
#include <cstdlib>
#include <ctime>

void deleteMatrix(Matrix matrix) {
	for (int i = 0; i < matrix.rows; ++i)
		delete[] matrix.matrix[i];
	delete[] matrix.matrix;
}

int main()
{
    std::cout << "Hello World!\n"; 
	
	int m1[100][100] = { {2, 1, 4}, {0, 1, 1} };
	int m2[100][100] = { {6, 3, -1, 0}, {1, 1, 0, 4}, {-2, 5, 0, 2} };
	Matrix matrix1 = Matrix(2, 3, m1);
	Matrix matrix2 = Matrix(3, 4, m2);
	std::cout << matrix1 << std::endl << matrix2 << std::endl;

	/*
	// initialize random seed
	srand(time(NULL));
	unsigned rows1 = rand() % 1000;
	unsigned rows2 = rand() % 1000;
	unsigned columns = rand() % 1000;

	int** m1 = new int*[rows1];
	for (unsigned i = 0; i < rows1; ++i)
		m1[i] = new int[columns];
	
	int** m2 = new int*[columns];
	for (unsigned i = 0; i < columns; ++i)
		m2[i] = new int[rows2];

	for (unsigned int i = 0; i < rows1; i++)
		for (unsigned int j = 0; j < columns; j++)
			m1[i][j] = rand() % 10;

	for (unsigned int i = 0; i < columns; i++)
		for (unsigned int j = 0; j < rows2; j++)
			m2[i][j] = rand() % 10;

	Matrix matrix1 = Matrix(rows1, columns, m1);
	Matrix matrix2 = Matrix(columns, rows2, m2);
	*/


	std::cout << "Compute sum matrix.." << std::endl;
	Matrix sum_matrix = matrix1 + matrix2;

	std::cout << "Computed sum matrix: " << sum_matrix << std::endl;
	
	std::cout << "Compute product matrix.." << std::endl;
	Matrix product_matrix = matrix1 * matrix2;

	std::cout << "Computed product: " << product_matrix << std::endl;
	
	std::cout << "Cleaning up.." << std::endl;
	deleteMatrix(matrix1);
	deleteMatrix(matrix2);
	deleteMatrix(sum_matrix);
	deleteMatrix(product_matrix);

	std::cout << "Bye world!" << std::endl;
}
