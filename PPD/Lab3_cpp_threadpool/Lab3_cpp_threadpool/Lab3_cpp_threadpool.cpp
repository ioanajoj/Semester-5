#include "pch.h"
#include <iostream>
#include "Matrix.h"
#include <cstdlib>
#include <ctime>

#include "ThreadPool.h"

int main()
{
	std::cout << "Hello World!\n";

	int m1[100][100] = { {2, 1, 4}, {0, 1, 1}, {2, -3, 8 } };
	int m2[100][100] = { {6, 3}, {1, 1}, {-2, 5} };
	Matrix matrix1 = Matrix(3, 3, m1);
	Matrix matrix2 = Matrix(3, 2, m2);

	std::cout << matrix1 << std::endl;
	std::cout << matrix2 << std::endl;

	std::cout << "Compute sum matrix.." << std::endl;
	Matrix sum_matrix = matrix1 + matrix2;
	std::cout << "Computed sum matrix: " << sum_matrix << std::endl;

	std::cout << "Compute product matrix.." << std::endl;
	Matrix product_matrix = matrix1 * matrix2;
	std::cout << "Computed product matrix: " << product_matrix << std::endl;


	std::cout << "Bye World!";
}
