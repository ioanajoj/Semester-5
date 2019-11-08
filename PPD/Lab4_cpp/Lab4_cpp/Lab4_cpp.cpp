#include "pch.h"
#include <iostream>
#include "Matrix.h"
#include <cstdlib>
#include <ctime>
#include <string>
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
	
	
	int m1[100][100] = { {1,2,3},{4,0,1} };
	int m2[100][100] = { {0,3}, {2,5}, {8,7} };
	int m3[100][100] = { {4,6}, {9,0} };
	
	unsigned m1_r = 300, m1_c = 300;
	unsigned m2_r = m1_c, m2_c = 300;
	unsigned m3_r = m1_r, m3_c = m2_c;
	Matrix matrix1 = Matrix("a", 2, 3, m1);
	Matrix matrix2 = Matrix("b", 3, 2, m2);
	Matrix matrix3 = Matrix("c", 2, 2, m3);

	std::cout << matrix1 << std::endl;
	std::cout << matrix2 << std::endl;
	std::cout << matrix3 << std::endl;

	MatrixMultiplier matrixMultiplier(8, 8, matrix1, matrix2, matrix3);
	
	auto startTime = std::chrono::high_resolution_clock::now();
	Matrix *result = matrixMultiplier.multiply();
	auto endTime = std::chrono::high_resolution_clock::now();
	std::cout << "Time needed to multiply 3 matrices using Producer Consumer pattern: " << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;

	/*std::cout << std::endl << (*result).name << "=" << std::endl;
	for (unsigned i = 0; i < (*result).rows; i++) {
		for (unsigned j = 0; j < (*result).columns; j++)
			std::cout << (*result).matrix[i][j] << " ";
		std::cout << std::endl;
	}*/

	auto startTime2 = std::chrono::high_resolution_clock::now();
	Matrix product_matrix = matrix1 * matrix2 * matrix3;
	auto endTime2 = std::chrono::high_resolution_clock::now();
	std::cout << "Time needed to multiply 3 matrices serially: " << std::chrono::duration <double, std::milli>(endTime2 - startTime2).count() << std::endl;

	std::cout << std::endl << "Bye World!";
}