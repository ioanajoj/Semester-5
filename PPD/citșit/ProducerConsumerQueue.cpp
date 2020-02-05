#include <iostream>
#include <mutex>
#include <condition_variable>
#include <queue>
#include <assert.h>
#include <optional>

template<typename T>
class ProducerConsumerQueue {
private:
	std::queue<T*> queue;
	std::mutex mtx;
	std::condition_variable consumers_cv, producers_cv;
	bool closed;
	const size_t maxSize = 5;

public:
	ProducerConsumerQueue()
	{
		this->closed = false;
	};

	void enqueue(T* v)
	{
		std::unique_lock<std::mutex> l(this->mtx);
		assert(!this->closed);
		while (true)
		{
			if (this->queue.size() < maxSize)
			{
				this->queue.push(v);
				consumers_cv.notify_one();
				return;
			}
			else
			{
				std::cout << "Producer waits" << std::endl;
				producers_cv.wait(l);
			}
		}
	}

	void close()
	{
		std::unique_lock<std::mutex> l(this->mtx);
		this->closed = true;
		consumers_cv.notify_all();
	}

	T* dequeue()
	{
		std::unique_lock<std::mutex> l(this->mtx);
		while (true)
		{
			if (!this->queue.empty())
			{
				T* temp = this->queue.front();
				queue.pop();
				producers_cv.notify_one();
				return temp;
			}
			if (closed)
			{
				std::cout << "Consumer exits" << std::endl;
				return nullptr;
			}
			std::cout << "Consumer waits" << std::endl;
			consumers_cv.wait(l);
		}
	}
};
