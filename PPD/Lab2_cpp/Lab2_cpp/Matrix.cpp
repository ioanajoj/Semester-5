#include "pch.h"
#include "Matrix.h"
#include <string>
#include <algorithm>
#include <vector>
#include <thread>
#include <mutex>
#include <math.h>

void add_matrices(int **sum_matrix, unsigned start, unsigned end, unsigned rows, unsigned columns, Matrix matrix1, Matrix matrix2)
{
	unsigned cells = rows * columns;
	for (unsigned i = start; i < end; i++) {
		unsigned row = i / columns;
		unsigned column = i % columns;
		int sum = 0;
		if (row < matrix1.rows && column < matrix1.columns) sum += matrix1.matrix[row][column];
		if (row < matrix2.rows && column < matrix2.columns) sum += matrix2.matrix[row][column];
		sum_matrix[row][column] = sum;
	}
}

int multiply_lc(unsigned i, unsigned j, Matrix matrix1, Matrix matrix2)
{
	int product = 0;
	for (unsigned cell = 0; cell < matrix1.columns; cell++)
		product += matrix1.matrix[i][cell] * matrix2.matrix[cell][j];
	return product;
}

void multiply_matrices(int **product_matrix, unsigned start, unsigned end, Matrix matrix1, Matrix matrix2)
{
	for (unsigned i = start; i < end; i++) {
		unsigned row = i / matrix2.columns;
		unsigned column = i % matrix2.columns;
		product_matrix[row][column] = multiply_lc(row, column, std::ref(matrix1), std::ref(matrix2));
	}
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
	unsigned noOfThreads = 20;
	int computationsPerThread;
	if (cells > noOfThreads) 
		computationsPerThread = ceil(cells / (double)noOfThreads);
	else {
		noOfThreads = cells;
		computationsPerThread = 1;
	}

	// assign work to threads
	std::vector<std::thread> threads;
	unsigned start, end;
	auto startTime = std::chrono::high_resolution_clock::now();
	for (unsigned i = 0; i < noOfThreads; i++) {
		start = i * computationsPerThread;
		end = (i + 1) * computationsPerThread;
		if (end > cells) end = cells;
		threads.push_back(std::thread(add_matrices, sum_matrix, start, end, rows, columns, std::ref(matrix1), std::ref(matrix2)));
	}

	// wait for threads to finish
	for (auto& thread : threads)
		thread.join();

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
	
	// divide work
	unsigned noOfThreads = 4;
	int computationsPerThread;
	unsigned cells = rows * columns;
	if (cells > noOfThreads)
		computationsPerThread = ceil(cells / (double)noOfThreads);
	else {
		noOfThreads = cells;
		computationsPerThread = 1;
	}
	std::cout << "Computations per thread: " << computationsPerThread << std::endl;

	// assign work to threads
	std::vector<std::thread> threads;
	unsigned start, end;
	auto startTime = std::chrono::high_resolution_clock::now();
	for (unsigned i = 0; i < noOfThreads; i++) {
		start = i * computationsPerThread;
		end = (i + 1) * computationsPerThread;
		if (end > cells) end = cells;
		threads.push_back(std::thread(multiply_matrices, product_matrix, start, end, std::ref(matrix1), std::ref(matrix2)));
	}

	// wait for threads to finish
	for (auto& thread : threads)
		thread.join();
	
	// stop timing
	auto endTime = std::chrono::high_resolution_clock::now();
	std::cout << "Time needed to do multiplication: " << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;

	// create new matrix
	return Matrix(rows, columns, product_matrix);
}

Matrix::~Matrix()
{
}
