#pragma once
#include "Operation.h"

class UpdateOperation : public Operation
{
public:
	UpdateOperation(char var, int new_val) : variable(var), new_value(new_val) {};
	~UpdateOperation();

	int getIndex();

	char variable;
	int new_value;
};

