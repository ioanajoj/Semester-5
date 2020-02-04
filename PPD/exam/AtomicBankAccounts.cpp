#include "pch.h"
#include <iostream>
#include <atomic>
#include <vector>
#include <thread>

using namespace std;

struct AtomicAccount {
	atomic<int> balance;
};

bool atomic_transfer(AtomicAccount& from, AtomicAccount& to, unsigned amount)
{
	if (from.balance < amount)
	{
		return false;
	}
	from.balance -= amount;
	to.balance += amount;
	return true;
}

int getSum(vector<AtomicAccount*> accounts)
{
	int sum = 0;
	for (int i = 0; i < accounts.size(); i++)
	{
		sum += accounts[i]->balance;
	}
	return sum;
}

int main2()
{
	cout << "Hello world!" << endl;

	int n = 100;
	vector<AtomicAccount*> accounts;
	for (int i = 0; i < 100; i++)
	{
		AtomicAccount* account = new AtomicAccount();
		account->balance = 30;
		accounts.push_back(account);
	}

	int initialSum = getSum(accounts);
	cout << "Initial sum: " << initialSum << endl;

	vector<thread> threads;

	for (int i = 0; i < 1000; i++)
	{
		int pos1 = rand() % 100;
		int pos2 = rand() % 100;
		while (pos1 == pos2)
		{
			pos2 = rand() % 100;
		}
		AtomicAccount* from = accounts[pos1];
		AtomicAccount* to = accounts[pos2];
		
		threads.push_back(thread([from, to]() {atomic_transfer(*from, *to, 10); }));
	}

	for (int i = 0; i < 1000; i++)
	{
		threads[i].join();
	}

	int finalSum = getSum(accounts);

	cout << "Final Sum: " << finalSum << endl;
	return 0;
}