#include "pch.h"
#include "Matrix.h"
#include <string>
#include <algorithm>
#include <vector>
#include <thread>

void threadWork(int **sum_matrix, unsigned start, unsigned end, unsigned rows, unsigned columns, Matrix matrix1, Matrix matrix2)
{
	unsigned cells = rows * columns;
	for (unsigned i = start; i < end; i++) {
		unsigned row = i / columns;
		unsigned column = i % columns;
		unsigned sum = 0;
		if (row < matrix1.rows) sum += matrix1.matrix[row][column];
		if (column < matrix2.rows) sum += matrix2.matrix[row][column];
		sum_matrix[row][column] = sum;
	}
}

Matrix::Matrix()
{
	this->rows = 0;
	this->columns = 0;
}

Matrix::Matrix(unsigned rows, unsigned columns, int matrix[100][100])
{
	this->rows = rows;
	this->columns = columns;
	this->matrix = new int*[rows];
	for (int i = 0; i < rows; ++i)
		this->matrix[i] = new int[columns];
	for (unsigned i = 0; i < rows; i++)
		for (unsigned j = 0; j < columns; j++)
			this->matrix[i][j] = matrix[i][j];
}

Matrix::Matrix(unsigned rows, unsigned columns, int** matrix)
{
	this->rows = rows;
	this->columns = columns;
	this->matrix = new int*[rows];
	for (int i = 0; i < rows; ++i)
		this->matrix[i] = new int[columns];
	for (unsigned i = 0; i < rows; i++)
		for (unsigned j = 0; j < columns; j++)
			this->matrix[i][j] = matrix[i][j];
}

std::ostream& operator<<(std::ostream &strm, const Matrix &matrix) {
	std::string m = "";
	for (unsigned i = 0; i < matrix.rows; i++) {
		for (unsigned j = 0; j < matrix.columns; j++) {
			m += std::to_string(matrix.matrix[i][j]);
			m += ", ";
		}
		m += "\n";
	}
	return strm << "Matrix with " << matrix.rows << " rows and " << matrix.columns << " columns:" << std::endl << m << std::endl;
}

Matrix operator+(Matrix const & matrix1, Matrix const & matrix2)
{
	unsigned rows = std::max(matrix1.rows, matrix2.rows);
	unsigned columns = std::max(matrix1.columns, matrix2.columns);
	int** sum_matrix = new int*[rows];
	for (int i = 0; i < rows; ++i)
		sum_matrix[i] = new int[columns];

	// divide work
	unsigned cells = rows * columns;
	unsigned noOfThreads = 16;
	std::vector<std::thread> threads;
	unsigned computationsPerThread = cells / noOfThreads;
	for (unsigned i = 0; i < noOfThreads; i++) {
		unsigned start = i * computationsPerThread;
		unsigned end = (i + 1) * computationsPerThread;
		threads.push_back(std::thread(threadWork, sum_matrix, start, end, rows, columns, std::ref(matrix1), std::ref(matrix2)));
	}
	for (auto& thread : threads)
		thread.join();

	for (unsigned i = 0; i < rows; i++) {
		for (unsigned j = 0; j < columns; j++) {
			int sum = 0;
			if (i < matrix1.rows) sum += matrix1.matrix[i][j];
			if (j < matrix2.rows) sum += matrix2.matrix[i][j];
			sum_matrix[i][j] = sum;
		}
	}

	return Matrix(rows, columns, sum_matrix);
}

Matrix::~Matrix()
{
}
