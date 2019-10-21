#pragma once
#include <mutex>
#include <vector>
#include "Transfer.h"

class Account
{
public:
	unsigned id;
	std::mutex mutex;
	unsigned initialBalance;
	unsigned balance;
	std::vector<Transfer*> transfers;

	Account();
	Account(unsigned id, unsigned balance);
	bool consistencyCheck();
	friend std::ostream& operator<<(std::ostream& os, const Account& account);
	~Account();
};

