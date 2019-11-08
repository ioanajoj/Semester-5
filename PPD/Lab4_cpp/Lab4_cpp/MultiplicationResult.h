#pragma once
class MultiplicationResult
{
public:
	MultiplicationResult(unsigned row, unsigned column, unsigned result);
	~MultiplicationResult();

	unsigned row, column, result;
};

