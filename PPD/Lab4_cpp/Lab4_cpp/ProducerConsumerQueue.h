#pragma once
#include <queue>
#include <mutex>
#include <assert.h>
#include <optional>
#include "Matrix.h"
#include "MultiplicationResult.h"

template<typename T>
class ProducerConsumerQueue
{
public:
	ProducerConsumerQueue(Matrix & matrix1, Matrix & matrix2, Matrix & matrix3);
	~ProducerConsumerQueue();

	void enqueue(T v);
	std::optional<T> dequeue();
	void close();

	void producer(unsigned start, unsigned end);
	void consumer(unsigned id);
	bool finished();
	Matrix* get_result_matrix();

private:
	std::queue<T> queue;
	std::mutex mtx, outmtx, result_matrix_mtx;
	std::condition_variable producerCV, consumerCV;
	bool closed;
	const size_t max_size = 100000;
	Matrix *matrix1, *matrix2, *matrix3;
	int** product_matrix;
	unsigned finished_tasks;

	int rc_multiply(unsigned row, unsigned column);
};

template<typename T>
ProducerConsumerQueue<T>::ProducerConsumerQueue(Matrix & matrix1, Matrix & matrix2, Matrix & matrix3)
{
	this->matrix1 = &matrix1;
	this->matrix2 = &matrix2;
	this->matrix3 = &matrix3;
	this->finished_tasks = 0;
	this->closed = false;

	unsigned rows = this->matrix1->rows;
	unsigned columns = this->matrix2->columns;
	this->product_matrix = new int*[rows];
	for (unsigned i = 0; i < rows; ++i)
		this->product_matrix[i] = new int[columns];
	for (unsigned i = 0; i < rows; i++)
		for (unsigned j = 0; j < columns; j++)
			this->product_matrix[i][j] = 0;
	std::cout << "Product matrix done" << std::endl;
}

template<typename T>
ProducerConsumerQueue<T>::~ProducerConsumerQueue()
{
}

template<typename T>
void ProducerConsumerQueue<T>::enqueue(T value)
{
	std::unique_lock<std::mutex> l(this->mtx);
	assert(!closed);
	while (true) {
		if (this->queue.size() <= max_size) {
			this->queue.push(value);
			/*outmtx.lock();
			std::cout << "Enqueued new value. " << " Size = " << this->queue.size() << std::endl;
			outmtx.unlock();*/
			this->consumerCV.notify_one(); 
			return;
		}
		else {
			//std::cout << "Exceeded maximum size of queue." << std::endl;
		}
	}
}

template<typename T>
std::optional<T> ProducerConsumerQueue<T>::dequeue()
{
	std::unique_lock<std::mutex> lock(this->mtx);
	while (true) {
		if (!this->queue.empty()) {
			T temp = queue.front();
			queue.pop();
			return std::optional<T>(temp);
		}
		if (closed)
			return std::nullopt;
		this->consumerCV.wait(lock);
	}
}

template<typename T>
void ProducerConsumerQueue<T>::close()
{
	std::unique_lock<std::mutex> l(this->mtx);
	closed = true;
	this->consumerCV.notify_all();
}

template<typename T>
int ProducerConsumerQueue<T>::rc_multiply(unsigned row, unsigned column)
{
	int product = 0;
	for (unsigned cell = 0; cell < this->matrix1->columns; cell++)
		product += this->matrix1->matrix[row][cell] * this->matrix2->matrix[cell][column];
	return product;
}

template<typename T>
Matrix* ProducerConsumerQueue<T>::get_result_matrix()
{
	return new Matrix(this->matrix1->name + "*" + this->matrix2->name + "*" + this->matrix3->name, this->matrix1->rows, this->matrix2->columns, this->product_matrix);
}

template<typename T>
void ProducerConsumerQueue<T>::producer(unsigned start, unsigned end)
{
	for (unsigned i = start; i < end; i++) {
		unsigned row = i / matrix2->columns;
		unsigned column = i % matrix2->columns;
		int product = this->rc_multiply(row, column);
		this->enqueue(MultiplicationResult(row, column, product));
	}
}

template<typename T>
void ProducerConsumerQueue<T>::consumer(unsigned id)
{
	while (true) {
		std::optional<MultiplicationResult> optResult = this->dequeue();
		if (optResult.has_value()) {
			MultiplicationResult result = optResult.value();
			for (unsigned i = 0; i < this->matrix3->columns; i++) {
				result_matrix_mtx.lock();
				this->product_matrix[result.row][i] += result.result * this->matrix3->matrix[result.column][i];
				this->finished_tasks++;
				result_matrix_mtx.unlock();
				/*outmtx.lock();
				std::cout << "Consumer " << id << " computed [" << result.row << "][" << i << "]" << std::endl;
				outmtx.unlock();*/
			}
		}
		else {
			/*outmtx.lock();
			std::cout << "Consumer " << id << " exiting.." << std::endl;
			outmtx.unlock();*/
			return;
		}
	}
}

template<typename T>
inline bool ProducerConsumerQueue<T>::finished()
{
	if (this->finished_tasks != 4)
		return false;
	return true;
}

