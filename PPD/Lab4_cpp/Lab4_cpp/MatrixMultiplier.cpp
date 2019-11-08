#include "pch.h"
#include "MatrixMultiplier.h"
#include <thread>
#include <vector>
#include <math.h>
#include <mutex>
#include <future>
#include <string>
#include <assert.h>

MatrixMultiplier::MatrixMultiplier(unsigned noOfProducerThreads, unsigned noOfConsumerThreads, Matrix & m1, Matrix & m2, Matrix & m3)
{
	this->m1 = &m1;
	this->m2 = &m2;
	this->m3 = &m3;
	this->pcQueue = new ProducerConsumerQueue<MultiplicationResult>(m1, m2, m3);
	this->noOfProducerThreads = noOfProducerThreads;
	this->noOfConsumerThreads = noOfConsumerThreads;
}

MatrixMultiplier::~MatrixMultiplier()
{
}

Matrix* MatrixMultiplier::multiply()
{
	assert(this->m1->columns == this->m2->rows);
	assert(this->m1->rows == this->m3->rows);
	assert(this->m2->columns == this->m3->columns);

	// define parameters for product matrix
	unsigned rows = m1->rows;
	unsigned columns = m2->columns;
	unsigned cells = rows * columns;

	// decide how to divide work
	int computationsPerThread;
	if (cells > this->noOfProducerThreads)
		computationsPerThread = ceil(cells / (double)noOfProducerThreads);
	else {
		noOfProducerThreads = cells;
		computationsPerThread = 1;
	}

	// Call consumers
	std::vector<std::thread> consumers;
	for (unsigned i = 0; i < this->noOfConsumerThreads; i++) {
		consumers.push_back(std::thread([this, i] {
			this->pcQueue->consumer(i);
		}));
	}

	// Call producers
	std::vector<std::thread> producers;
	unsigned start, end;
	for (unsigned i = 0; i < this->noOfProducerThreads; i++) {
		start = i * computationsPerThread;
		end = (i + 1) * computationsPerThread;
		if (end > cells) end = cells;
		producers.push_back(std::thread([this, start, end] {
			this->pcQueue->producer(start, end);
		}));
	}

	// wait for producers to finish
	for (auto& producer : producers)
		producer.join();

	this->pcQueue->close();

	// wait for consumers to finish
	for (auto& consumer : consumers)
		consumer.join();

	return this->pcQueue->get_result_matrix();
}

