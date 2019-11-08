#include "pch.h"
#include "MultiplicationResult.h"


MultiplicationResult::MultiplicationResult(unsigned row, unsigned column, unsigned result)
{
	this->row = row;
	this->column = column;
	this->result = result;
}

MultiplicationResult::~MultiplicationResult()
{
}
