#include "pch.h"
#include "Matrix.h"
#include <string>
#include <algorithm>
#include <vector>
#include <thread>
#include <mutex>
#include <math.h>
#include <future>
#include "ThreadPool.h"

void add_matrix(int **sum_matrix, int cell, unsigned rows, unsigned columns, Matrix matrix1, Matrix matrix2)
{
	unsigned row = cell / columns;
	unsigned column = cell % columns;
	int sum = 0;
	if (row < matrix1.rows && column < matrix1.columns)
		sum += matrix1.matrix[row][column];
	if (row < matrix2.rows && column < matrix2.columns)
		sum += matrix2.matrix[row][column];
	sum_matrix[row][column] = sum;
	//std::cout << "(" << row << "," << column << ")=" << sum_matrix[row][column] << std::endl;
}

void multiply_lc(int **product_matrix, unsigned cellNo, Matrix matrix1, Matrix matrix2)
{
	int product = 0;
	unsigned row = cellNo / matrix2.columns;
	unsigned column = cellNo % matrix2.columns;
	for (unsigned cell = 0; cell < matrix1.columns; cell++)
		product += matrix1.matrix[row][cell] * matrix2.matrix[cell][column];
	product_matrix[row][column] = product;
	//std::cout << "(" << row << "," << column << ")=" << product << std::endl;
}


Matrix::Matrix()
{
	this->rows = 0;
	this->columns = 0;
	this->matrix = new int*[0];
}

Matrix::Matrix(unsigned rows, unsigned columns, int matrix[100][100])
{
	this->rows = rows;
	this->columns = columns;
	this->matrix = new int*[rows];
	for (unsigned i = 0; i < rows; ++i)
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
	for (unsigned i = 0; i < rows; ++i)
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
	// define parameters for sum matrix
	unsigned rows = std::max(matrix1.rows, matrix2.rows);
	unsigned columns = std::max(matrix1.columns, matrix2.columns);
	int** sum_matrix = new int*[rows];
	for (unsigned i = 0; i < rows; ++i)
		sum_matrix[i] = new int[columns];

	// decide how to divide work
	unsigned cells = rows * columns;

	auto startTime = std::chrono::high_resolution_clock::now();

	ThreadPool pool(4);
	std::vector< std::future<void> > results;

	for (int i = 0; i < cells; ++i) {
		results.emplace_back(
			pool.enqueue(add_matrix, sum_matrix, i, rows, columns, matrix1, matrix2)
		);
	}

	// wait for async work
	bool result = false;
	while (!result) {
		result = true;
		for (auto &future : results) {
			if (future.wait_for(std::chrono::seconds(0)) != std::future_status::ready)
				result = false;
		}
	}

	// stop timing
	auto endTime = std::chrono::high_resolution_clock::now();
	std::cout << "Time needed to add: " << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;

	// create new matrix
	return Matrix(rows, columns, sum_matrix);
}

Matrix operator*(Matrix const & matrix1, Matrix const & matrix2)
{
	if (matrix1.columns != matrix2.rows)
		return Matrix();

	// define parameters for product matrix
	unsigned rows = matrix1.rows;
	unsigned columns = matrix2.columns;
	int** product_matrix = new int*[rows];
	for (unsigned i = 0; i < rows; ++i)
		product_matrix[i] = new int[columns];

	// assign work to threads
	auto startTime = std::chrono::high_resolution_clock::now();

	ThreadPool pool(4);
	std::vector< std::future<void> > results;

	// start async work
	unsigned cells = rows * columns;
	for (int i = 0; i < cells; ++i) {
		results.emplace_back(
			pool.enqueue(multiply_lc, product_matrix, i, matrix1, matrix2)
		);
	}

	// stop timing
	auto endTime = std::chrono::high_resolution_clock::now();
	std::cout << "Time needed to do multiplication: " << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;

	// create new matrix
	return Matrix(rows, columns, product_matrix);
}

Matrix::~Matrix()
{
}
