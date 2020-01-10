#pragma once
#include "Operation.h"
#include <string>

class XChangeOperation : public Operation
{
public:
	XChangeOperation(std::string var, int old_val, int new_val) : variable(var), old_value(old_val), new_value(new_val) {};
	~XChangeOperation();
private:
	std::string variable;
	int old_value, new_value;
};

