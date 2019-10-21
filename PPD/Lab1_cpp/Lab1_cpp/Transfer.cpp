#include "pch.h"
#include "Transfer.h"
#include "Account.h"
#include <iostream>

Transfer::Transfer(Account * sourceAccount, Account * destinationAccount, unsigned amount)
{
	this->sourceAccount = sourceAccount;
	this->destinationAccount = destinationAccount;
	this->amount = amount;
}

void Transfer::transfer()
{
	//std::cout << "Transfer from " << sourceAccount->id << " to " << destinationAccount->id;
	if (sourceAccount->id < destinationAccount->id) {
		sourceAccount->mutex.lock();
		destinationAccount->mutex.lock();
	}
	else {
		destinationAccount->mutex.lock();
		sourceAccount->mutex.lock();
	}

	sourceAccount->balance -= amount;
	sourceAccount->transfers.push_back(this);
	destinationAccount->balance += amount;
	destinationAccount->transfers.push_back(this);
	
	sourceAccount->mutex.unlock();
	destinationAccount->mutex.unlock();
}

std::ostream& operator<<(std::ostream &strm, const Transfer &transfer) {
	return strm << "Transfer from: " << transfer.sourceAccount->id << " to " << transfer.destinationAccount->id << " amount " << transfer.amount << "\n";
}

Transfer::~Transfer()
{
}
