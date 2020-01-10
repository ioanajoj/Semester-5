#pragma once
class SubscribeOperation
{
public:
	SubscribeOperation(char variable, int rank) : variable(variable), rank(rank) {};
	~SubscribeOperation();

private:
	char variable;
	int rank;
};

