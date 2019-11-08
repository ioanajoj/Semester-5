#include "pch.h"
#include "Matrix.h"
#include <string>
#include <algorithm>
#include <vector>
#include <thread>
#include <mutex>
#include <math.h>
#include <assert.h>

std::mutex mtx;
int multiply_lc(unsigned i, unsigned j, Matrix matrix1, Matrix matrix2)
{
	int product = 0;
	for (unsigned cell = 0; cell < matrix1.columns; cell++)
		product += matrix1.matrix[i][cell] * matrix2.matrix[cell][j];
	mtx.lock();
	std::cout << matrix1.name << "*" << matrix2.name << " line " << i << " column " << j << std::endl;
	mtx.unlock();
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
	this->name = "default";
	this->rows = 0;
	this->columns = 0;
	this->matrix = new int*[0];
}

Matrix::Matrix(std::string name, unsigned rows, unsigned columns, int matrix[100][100])
{
	this->name = name;
	this->rows = rows;
	this->columns = columns;
	this->matrix = new int*[rows];
	for (unsigned i = 0; i < rows; ++i)
		this->matrix[i] = new int[columns];
	for (unsigned i = 0; i < rows; i++)
		for (unsigned j = 0; j < columns; j++)
			this->matrix[i][j] = matrix[i][j];
}

Matrix::Matrix(std::string name, unsigned rows, unsigned columns, int** matrix)
{
	this->name = name;
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

Matrix operator*(Matrix const & matrix1, Matrix const & matrix2)
{
	bool res = matrix1.columns == matrix2.rows;
	assert(matrix1.columns == matrix2.rows);

	// define parameters for product matrix
	unsigned rows = matrix1.rows;
	unsigned columns = matrix2.columns;
	int** product_matrix = new int*[rows];
	for (unsigned i = 0; i < rows; ++i)
		product_matrix[i] = new int[columns];

	// start async work
	unsigned cells = rows * columns;
	bool* completed = new bool[cells];
	for (int i = 0; i < cells; i++) completed[i] = false;


	// decide how to divide work
	unsigned noOfThreads = 8;
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
		threads.push_back(std::thread(multiply_matrices, product_matrix, start, end, std::ref(matrix1), std::ref(matrix2)));
	}

	// wait for threads to finish
	for (auto& thread : threads)
		thread.join();

	// stop timing
	auto endTime = std::chrono::high_resolution_clock::now();
	std::cout << "Time needed to do multiplication: " << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;

	// create new matrix
	return Matrix(matrix1.name + " * " + matrix2.name, rows, columns, product_matrix);
}

Matrix::~Matrix()
{
}
