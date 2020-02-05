#include "pch.h"
#include <iostream>
#include <mutex>
#include <vector>
#include <future>
#include <thread>

using namespace std;

struct Account {
	unsigned id;
	unsigned balance;
	mutex mtx;

	Account(unsigned i, unsigned b)
	{
		id = i;
		balance = b;
	}
};

bool transfer(Account& from, Account& to, unsigned amount)
{
	cout << "Transfer: " << from.id << " to " << to.id << endl;
	if (from.id < to.id)
	{
		from.mtx.lock();
		to.mtx.lock();
	}
	else
	{
		to.mtx.lock();
		from.mtx.lock();
	}
	if (from.balance < amount)
	{
		if (from.id < to.id)
		{
			from.mtx.unlock();
			to.mtx.unlock();
		}
		else
		{
			to.mtx.unlock();
			from.mtx.unlock();
		}
		return false;
	}
	from.balance -= amount;
	to.balance += amount;
	if (from.id < to.id)
	{
		from.mtx.unlock();
		to.mtx.unlock();
	}
	else
	{
		to.mtx.unlock();
		from.mtx.unlock();
	}
	return true;
}


atomic<int> counter;

bool transfer_unique(Account& from, Account& to, unsigned amount)
{
	cout << "Transfer: " << from.id << " to " << to.id << "with " << amount << endl;
	lock(from.mtx, to.mtx); // simultaneous lock (prevents deadlock)
	unique_lock<mutex> l1(from.mtx, adopt_lock);
	unique_lock<mutex> l2(to.mtx, adopt_lock);
	/*if (from.id < to.id)
	{
		unique_lock<mutex> l1(from.mtx);
		unique_lock<mutex> l2(to.mtx);
	}
	else
	{
		unique_lock<mutex> l1(to.mtx);
		unique_lock<mutex> l2(from.mtx);
	}*/

	if (from.balance < amount)
	{
		counter++;
		return false;
	}
	from.balance -= amount;
	to.balance += amount;
	counter++;
	return true;

	// (mutexes unlocked automatically on destruction of lck1 and lck2)
}

int getSum(vector<Account*> accounts)
{
	int sum = 0;
	for (int i = 0; i < accounts.size(); i++)
	{
		accounts[i]->mtx.lock();
		sum += accounts[i]->balance;
	}
	for (int i = 0; i < accounts.size(); i++)
	{
		accounts[i]->mtx.unlock();
	}
	return sum;
}

int main()
{
    std::cout << "Hello World!\n";

	vector<Account*> accounts;
	for (int i = 0; i < 10; i++)
	{
		Account* account = new Account(i, 30);
		accounts.push_back(account);
	}

	int initialSum = getSum(accounts);

	vector<thread> threads;

	for (int i = 0; i < 10000; i++)
	{
		Account* from = accounts[rand() % 2];
		Account* to = accounts[rand() % 2];
		while (from->id == to->id)
		{
			to = accounts[rand() % 2];
		}
		threads.push_back(thread([from, to]() {transfer_unique(*from, *to, rand() % 10); }));
	}

	for (int i = 0; i < 10000; i++)
	{
		threads[i].join();
	}

	int finalSum = getSum(accounts);

	cout << initialSum << " : " << finalSum << endl;

	return finalSum;
}

