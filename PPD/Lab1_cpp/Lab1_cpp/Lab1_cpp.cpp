#include "pch.h"
#include <iostream>
#include <vector>
#include "Account.h"
#include <cstdlib>
/*
void executeTransfers(unsigned start, unsigned end, std::vector<Transfer*> transfers) {
	for (unsigned i = start; i < end; i++) {
		transfers[i]->transfer();
	}
	std::cout << "My name is thread\n";
}
*/

std::mutex mtx;
void print_thread_id(int id) {
	// critical section (exclusive access to std::cout signaled by locking mtx):
	mtx.lock();
	std::cout << "thread #" << id << '\n';
	mtx.unlock();
}

int main()
{
    std::cout << "Hello World!\n"; 
	
	std::vector<std::unique_ptr<Account>> accounts;
	for (int i = 0; i < 12; i++) {
		accounts.push_back(std::make_unique<Account>(i, rand() % 10));
	}
	/*
	std::vector<Transfer*> transfers;
	for (int i = 0; i < 10; i++) {
		unsigned sourceId = rand() % 10;
		unsigned destinationId = rand() % 10;
		while (sourceId == destinationId) {
			destinationId = rand() % 10;
		}
		transfers.push_back(new Transfer(accounts[sourceId], accounts[destinationId], rand() % 50));
		std::cout << (*transfers[i]);
	}

	std::thread threads[10];
	for (int i = 0; i < 10; ++i)
		threads[i] = std::thread(print_thread_id, i + 1);

	for (auto& th : threads) th.join();
	*/
	/*
	std::thread threads[10];
	unsigned noOfThreads = 1;
	unsigned transfersPerThread = transfers.size() / noOfThreads;
	for (unsigned i = 0; i < noOfThreads; i++) {
		unsigned start = i * transfersPerThread;
		unsigned end = (i + 1)*transfersPerThread;
		threads[i] = std::thread(print_thread_id, i+1);
	}

	for (auto& thread : threads) 
		thread.join();
	*/
	for (int i = 0; i < accounts.size(); i++) {
		std::cout << (*accounts[i].get());
	}


	/*std::cout << "Size of transfers: " << transfers.size() << "\nSize of accounts: " << accounts.size();*/
	

	/*
	std::thread threads[10];
	// spawn 10 threads:
	for (int i = 0; i < 10; ++i)
		threads[i] = std::thread(print_thread_id, i + 1);

	for (auto& th : threads) th.join();
	*/
}
