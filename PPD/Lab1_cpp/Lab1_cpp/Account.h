#pragma once
#include <mutex>
#include <vector>

class Account
{
public:
	unsigned id;
	std::mutex mutex;
	unsigned initialBalance;
	unsigned balance;

	Account();
	Account(unsigned id, unsigned balance);
	bool consistencyCheck();
	friend std::ostream& operator<<(std::ostream& os, const Account& account);
	~Account();
};

