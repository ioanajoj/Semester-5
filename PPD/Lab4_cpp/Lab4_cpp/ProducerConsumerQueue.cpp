#include "pch.h"
#include "ProducerConsumerQueue.h"
#include <assert.h>
#include <optional>



template<typename T>
ProducerConsumerQueue<T>::ProducerConsumerQueue()
{
	this->closed = false;
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
			this->enqueue(value);
			this->consumerCV.notify_one();
			return;
		}
		else {
			this->producerCV.wait();
		}
	}
}

template<typename T>
T ProducerConsumerQueue<T>::dequeue()
{	
	std::unique_lock<std::mutex> l(this->mtx);
	while (true) {
		if (!this->queue.empty()) {
			T temp = queue.front();
			queue.pop();
			this->producerCV.notify_one();
			return std::optional<T>(temp);
		}
		if (closed)
			return std::nullopt;
	}
}

template<typename T>
void ProducerConsumerQueue<T>::close()
{
	std::unique_lock<std::mutex> l(this->mtx);
	closed = true;
	this->consumer.notify_one();
}
