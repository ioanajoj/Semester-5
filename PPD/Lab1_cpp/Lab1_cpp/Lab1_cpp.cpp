#include "pch.h"
#include <iostream>
#include <vector>
#include "Account.h"
#include "Transfer.h"
#include <cstdlib>

void executeTransfers(std::mutex* mutex, unsigned start, unsigned end, std::vector<Transfer*> transfers) {
	for (unsigned i = start; i < end; i++) {
		(*mutex).lock();
		transfers[i]->transfer();
		(*mutex).unlock();
	}
}

int main()
{
	// parameters
	unsigned noOfBankAccounts = 100;
	unsigned noOfTransfers = 1000000;
	unsigned noOfThreads = 16;
	std::mutex mutex;

	// initialize random seed
	srand(time(NULL));

    std::cout << "Initializing data in bank.." << std::endl; 
	// initialize bank accounts
	std::vector<Account*> accounts;
	for (unsigned i = 0; i < noOfBankAccounts; i++) {
		accounts.push_back(new Account(i, rand() % 1000));
	}

	// create random transfers
	std::vector<Transfer*> transfers;
	for (unsigned i = 0; i < noOfTransfers; i++) {
		unsigned sourceId = rand() % 10;
		unsigned destinationId = rand() % 10;
		while (sourceId == destinationId) {
			destinationId = rand() % 10;
		}
		transfers.push_back(new Transfer(accounts[sourceId], accounts[destinationId], rand() % 50));
		
	}
	
	// distribute and assign transfers to threads
	std::vector<std::thread> threads;
	unsigned transfersPerThread = transfers.size() / noOfThreads;
	for (unsigned i = 0; i < noOfThreads; i++) {
		unsigned start = i * transfersPerThread;
		unsigned end = (i + 1)*transfersPerThread;
		threads.push_back(std::thread(executeTransfers, &mutex, start, end, transfers));
	}
	
	// start worker threads
	std::cout << "Starting transfers.." << std::endl;
	auto start = std::chrono::steady_clock::now();
	for (auto& thread : threads) 
		thread.join();
	auto end = std::chrono::steady_clock::now();
	auto diff = end - start;
	std::cout << "Execution time: " << std::chrono::duration <double, std::milli>(diff).count() << " ms" << std::endl;
	
	// run consistency check on all accounts
	bool consistencyCheck = true;
	std::cout << "Validation of data in bank: ";
	for (unsigned i = 0; i < accounts.size(); i++) {
		consistencyCheck = consistencyCheck && accounts[i]->consistencyCheck();
	}
	std::cout << consistencyCheck << std::endl;

	// clean up
	for (Transfer* transfer : transfers)
		delete transfer;
	transfers.clear();
	for (Account* account : accounts)
		delete account;
	accounts.clear();

	std::cout << "Bye world!" << std::endl;

}
