#pragma once
#include <vector>
#include "ProducerConsumerQueue.h"
#include "MultiplicationResult.h"
#include "Matrix.h"

class MatrixMultiplier
{
public:
	MatrixMultiplier(unsigned noOfProducerThreads, unsigned noOfConsumerThreads, Matrix & m1, Matrix & m2, Matrix & m3);
	~MatrixMultiplier();

	Matrix* multiply();
	ProducerConsumerQueue<MultiplicationResult>* pcQueue;

private:
	Matrix *m1, *m2, *m3;
	int** product_matrix;

	std::mutex mtx;
	std::condition_variable producerCV, consumerCV;
	unsigned noOfProducerThreads, noOfConsumerThreads;
};

