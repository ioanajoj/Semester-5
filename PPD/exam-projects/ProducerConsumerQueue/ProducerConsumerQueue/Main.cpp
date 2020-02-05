#include <iostream>
#include "Consumer.cpp"

using namespace std;

int main()
{
	cout << "Hello World!" << endl;

	ProducerConsumerQueue<Task>* pcQ = new ProducerConsumerQueue<Task>();

	vector<thread> consumers;
	for (int i = 0; i < 3; i++)
	{
		Consumer consumer = Consumer(i, pcQ);
		consumers.push_back(thread([&]() { consumer.consume(); }));
	}

	for (int i = 0; i < 10; i++)
	{
		Task* task = new Task(i);
		pcQ->enqueue(task);
	}

	pcQ->close();

	for (int i = 0; i < 3; i++)
		consumers[i].join();
}