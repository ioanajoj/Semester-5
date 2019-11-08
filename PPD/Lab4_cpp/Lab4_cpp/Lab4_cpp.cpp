#include "pch.h"
#include <iostream>
#include "Matrix.h"
#include <cstdlib>
#include <ctime>
#include <string>
#include "RCMultiplier.h"
#include "MatrixMultiplier.h"

int** createMatrix(unsigned rows, unsigned columns) {
	int** matrix = new int*[rows];
	for (unsigned i = 0; i < rows; ++i)
		matrix[i] = new int[columns];

	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < columns; j++)
			matrix[i][j] = rand() % 10;
	}

	return matrix;
}

int main()
{
	std::cout << "Hello World!\n";

	srand(time(NULL));
	/*
	int m1[100][100] = { {1,2,3},{4,0,1} };
	int m2[100][100] = { {0,3}, {2,5}, {8,7} };
	int m3[100][100] = { {4,6}, {9,0} };
	*/
	Matrix matrix1 = Matrix("a", 50, 50, createMatrix(50, 50));
	Matrix matrix2 = Matrix("b", 50, 50, createMatrix(50, 50));
	Matrix matrix3 = Matrix("c", 50, 50, createMatrix(50, 50));

	std::cout << matrix1 << std::endl;
	std::cout << matrix2 << std::endl;
	std::cout << matrix3 << std::endl;

	MatrixMultiplier matrixMultiplier(4, 4, matrix1, matrix2, matrix3);
	Matrix *result = matrixMultiplier.multiply();

	std::cout << std::endl << (*result).name << "=" << std::endl;
	for (unsigned i = 0; i < (*result).rows; i++) {
		for (unsigned j = 0; j < (*result).columns; j++)
			std::cout << (*result).matrix[i][j] << " ";
		std::cout << std::endl;
	}

	std::cout << std::endl << "Bye World!";
}