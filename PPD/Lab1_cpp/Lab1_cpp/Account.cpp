#include "pch.h"
#include "Account.h"
#include <iostream>


Account::Account()
{
}

Account::Account(unsigned id, unsigned balance)
{
	this->id = id;
	this->initialBalance = balance;
	this->balance = balance;
}

bool Account::consistencyCheck()
{
	unsigned transferedAmount = 0;
	for (Transfer* transfer : this->transfers) {
		if (transfer->sourceAccount->id == this->id)
			transferedAmount -= transfer->amount;
		else if (transfer->destinationAccount->id == this->id)
			transferedAmount += transfer->amount;
	}
	if (this->initialBalance + transferedAmount == this->balance)
		return true;
	return false;
}

std::ostream& operator<<(std::ostream &strm, const Account &account) {
	return strm << "Account: " << account.id << " with balance " << account.initialBalance << "\n";
}

Account::~Account()
{
}
