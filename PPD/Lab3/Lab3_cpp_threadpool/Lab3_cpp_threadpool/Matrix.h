#pragma once
#include <iostream>

class Matrix
{
public:
	int** matrix;
	unsigned rows, columns;

	Matrix();
	/*Matrix(const Matrix &another_matrix);*/
	Matrix(unsigned rows, unsigned columns, int matrix[100][100]);
	Matrix(unsigned rows, unsigned columns, int** matrix);

	friend std::ostream& operator<<(std::ostream& os, const Matrix& matrix);
	friend Matrix operator + (Matrix const & matrix1, Matrix const & matrix2);
	friend Matrix operator * (Matrix const & matrix1, Matrix const & matrix2);

	~Matrix();
};

