#include "DSM.h"
#include "mpi.h"
#include <iostream>

DSM::DSM(int world_size, int process_id) : world_size(world_size), process_id(process_id)
{
	this->a = 1;
	this->subscribers['a'] = std::vector<int>();
	this->b = 2;
	this->subscribers['b'] = std::vector<int>();
	this->c = 3;
	this->subscribers['c'] = std::vector<int>();
}


DSM::~DSM()
{
}

void DSM::updateVariable(char variable, int value)
{
	this->setValue(variable, value);
	// Update subscribers
	UpdateOperation updateOperation = UpdateOperation(variable, value);
	this->updateSubscribers(variable, updateOperation);
}

void DSM::setValue(char variable, int value)
{
	if (isSubscribedTo(this->process_id, variable))
	{
		// Change value
		if (variable == 'a') this->a = value;
		else if (variable == 'b') this->b = value;
		else if (variable == 'c') this->c = value;
	}
}

void DSM::updateSubscribers(char variable, UpdateOperation updateOperation)
{
	std::cout << this->process_id << ": updating subscribers" << std::endl;
	for (int i = 0; i < this->world_size; i++)
	{
		int process_rank;
		MPI_Comm_rank(MPI_COMM_WORLD, &process_rank);
		if (process_rank == i) continue;
		if (this->isSubscribedTo(i, variable)) {
			std::cout << this->process_id << ": sending update message to " << i << std::endl;
			// Send index of variable and new value 
			char metadata[2];
			metadata[0] = updateOperation.getIndex();
			metadata[1] = updateOperation.new_value;
			// SEND UPDATE DATA WITH TAG 1
			MPI_Ssend(metadata, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
		}
	}
}

void DSM::xChangeValue(char variable, int old_value, int new_value)
{
	if (variable == 'a' and a == old_value)
		updateVariable('a', new_value);
	else if (variable == 'b' and a == old_value)
		updateVariable('b', new_value);
	else if (variable == 'c' and a == old_value)
		updateVariable('c', new_value);
}

void DSM::subscribeMe(char variable)
{
	if (this->isSubscribedTo(this->process_id, variable)) return;
	// add locally
	this->subscribers[variable].push_back(this->process_id);
	std::cout << this->process_id << ": subcribed to " << variable << std::endl;
	// send SubscribeOperation message to all the other processes SUBSCRIBE TAG 2
	int metadata[2];
	metadata[0] = this->process_id;
	metadata[1] = this->getVariableIndex(variable);
	for (int i = 0; i < this->world_size; i++)
	{
		if (i == this->process_id) continue;
		std::cout << this->process_id << ": sending subscribe message to " << i << std::endl;
		MPI_Ssend(metadata, 2, MPI_INT, i, 2, MPI_COMM_WORLD);
	}
}

void DSM::subscribe(int other_process_id, char variable)
{
	// add locally
	this->subscribers[variable].push_back(other_process_id);
	std::cout << this->process_id << ": subscribed " << other_process_id << " to " << variable << std::endl;
}

bool DSM::isSubscribedTo(int process_id, char variable)
{
	std::cout << std::endl;
	return std::find(this->subscribers[variable].begin(), this->subscribers[variable].end(), process_id) != this->subscribers[variable].end();
}

void DSM::close()
{
	// Send tag 13 to tell all threads to finish
	int metadata[2];
	for (int i = 0; i < this->world_size; i++) {
		std::cout << "Send to " << i << std::endl;
		MPI_Ssend(metadata, 2, MPI_INT, i, 13, MPI_COMM_WORLD);
	}
}

int DSM::getVariableIndex(char variable)
{
	if (variable == 'a') return 1;
	else if (variable == 'b') return 2;
	else if (variable == 'c') return 3;
	return 0;
}

char DSM::getVariableChar(int index)
{
	if (index == 1) return 'a';
	else if (index == 2) return 'b';
	else if (index == 3) return 'c';
}

