#include <iostream>
#include <vector>
#include <thread>
#include <list>
#include <mutex>
#include <condition_variable>
#include <algorithm>

using namespace std;

class ThreadPool
{
private:
	vector<thread> threads;
	list<function<void()>> workItems;
	mutex mtx;
	condition_variable cv;
	bool closed = false;

	void threadFunc() 
	{
		while (true)
		{
			unique_lock<mutex> l(mtx);
			if (workItems.empty() && !closed)
			{
				cv.wait(l);
			}
			else if (workItems.empty())
			{
				return;
			}
			else
			{
				function<void()> toExecute = workItems.front();
				workItems.pop_front();
				l.unlock();
				toExecute();
			}
		}
	}

public:
	bool enqueue(function<void()> f)
	{
		if (closed) 
			return false;
		unique_lock<mutex> l(mtx);
		workItems.push_back(f);
		cv.notify_one();
		return true;
	}

	void close()
	{
		unique_lock<mutex> l(mtx);
		if (closed)
			return;
		closed = true;
		cv.notify_all();
	}

	void awaitTermination()
	{
		for (int i = 0; i < threads.size(); i++)
			if (threads[i].joinable())
				threads[i].join();
	}

	ThreadPool(int noOfThreads)
	{
		for (int i = 0; i < noOfThreads; i++)
		{
			threads.emplace_back(thread([this]() { this->threadFunc(); }));
		}
	}

	~ThreadPool()
	{
		close();
		awaitTermination();
	}
};

int main()
{	
	ThreadPool threadPool(5);
	for (int i = 0; i < 10; i++)
	{
		threadPool.enqueue([i]() {cout << "Function " << i << endl; });
	}
	threadPool.close();
	threadPool.awaitTermination();

	cout << "Scope close";
	

	int x;
	cout << "Press any key.";
	//cin >> x;

}

