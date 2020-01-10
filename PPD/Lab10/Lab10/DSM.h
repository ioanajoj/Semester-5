#pragma once
#include <map>
#include <vector>
#include "UpdateOperation.h"

class DSM
{
public:
	DSM(int world_size, int process_id);
	~DSM();

	bool available = true;

	void updateVariable(char variable, int value);
	void setValue(char variable, int value);
	void updateSubscribers(char variable, UpdateOperation updateOperation);
	void xChangeValue(char variable, int old_value, int new_value);
	void subscribeMe(char variable);
	void subscribe(int other_process_id, char variable);
	bool isSubscribedTo(int process_id, char variable);
	void close();

	int getVariableIndex(char variable);
	char getVariableChar(int index);

private:
	// Number of processes / nodes running
	int world_size;
	// Process ID
	int process_id;
	// Variables in the systems mapped to their values
	int a, b, c;
	// Subsribers to the variables
	//		• std::string is the string name of the variable
	//		• int in vector are ids of the nodes
	std::map<char, std::vector<int>> subscribers;
};

