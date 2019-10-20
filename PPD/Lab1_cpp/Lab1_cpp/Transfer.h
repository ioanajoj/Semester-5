#pragma once
#include <iostream>

class Account;
class Transfer
{
public:
	Account* sourceAccount;
	Account* destinationAccount;
	unsigned amount;

	Transfer(Account* sourceAccount, Account* destinationAccount, unsigned amount);
	void transfer();
	friend std::ostream& operator<<(std::ostream& os, const Transfer& transfer);
	~Transfer();
};

