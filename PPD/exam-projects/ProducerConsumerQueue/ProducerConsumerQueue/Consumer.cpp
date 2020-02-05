#include "ProducerConsumerQueue.cpp"
#include "Task.cpp"
#include "iostream"

class Consumer {
public:
	int id;
	ProducerConsumerQueue<Task>* pcQ;

	Consumer(int i, ProducerConsumerQueue<Task>* q) : id{ i }, pcQ{ q } {};

	void consume()
	{
		while (true) 
		{
			Task* task = pcQ->dequeue();
			if (task == nullptr)
				return;
			task->execute();
		}
	}
};