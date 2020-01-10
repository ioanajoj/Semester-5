#pragma once
#include "Operation.h"
#include <string>

class WriteOperation : public Operation
{
public:
	WriteOperation(std::string variable, int value) : variable(variable), value(value) {};
	~WriteOperation();

private:
	std::string variable;
	int value;
};

