#include "UpdateOperation.h"

UpdateOperation::~UpdateOperation()
{
}

int UpdateOperation::getIndex()
{
	if (this->variable == 'a') return 1;
	else if (this->variable == 'b') return 2;
	else if (this->variable == 'c') return 3;
	return 0;
}
