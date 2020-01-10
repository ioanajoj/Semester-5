#include "DSM.h"
#include "mpi.h"
#include <iostream>
#include <algorithm>

DSM::DSM(int world_size, int process_id, std::map<char, int> variables) : world_size(world_size), process_id(process_id), variables(variables)
{
	std::transform(begin(variables), end(variables), back_inserter(this->vars),
		[](decltype(variables)::value_type const& pair) {
		return pair.first;
	});
	for (char var : this->vars)
		this->subscribers[var].push_back(this->process_id);
}


DSM::~DSM()
{
}

void DSM::assignUpdate(char variable, int value)
{
	int metadata[2];
	metadata[0] = this->getVariableIndex(variable);
	metadata[1] = value;
	for (int i = 0; i < this->world_size; i++)
		MPI_Ssend(metadata, 2, MPI_INT, i, 1, MPI_COMM_WORLD);
}

void DSM::updateVariable(char variable, int value)
{
	if (!this->contains(variable)) return;
	this->variables[variable] = value;
	// Update subscribers
	UpdateOperation updateOperation = UpdateOperation(variable, value);
	this->updateSubscribers(variable, updateOperation);
}

void DSM::setValue(char variable, int value)
{
	if (this->isSubscribedTo(this->process_id, variable))
	{
		this->variables[variable] = value;
	}
}

void DSM::updateSubscribers(char variable, UpdateOperation updateOperation)
{
	std::cout << this->process_id << ": updating subscribers" << std::endl;
	for (int i = 0; i < this->world_size; i++)
	{
		if (i == this->process_id) continue;
		if (this->isSubscribedTo(i, variable)) {
			std::cout << this->process_id << ": sending update message to " << i << std::endl;
			// Send index of variable and new value 
			char metadata[2];
			metadata[0] = updateOperation.getIndex();
			metadata[1] = updateOperation.new_value;
			// SEND UPDATE DATA WITH TAG 1
			MPI_Ssend(metadata, 1, MPI_INT, i, 11, MPI_COMM_WORLD);
		}
	}
}

void DSM::xChangeValue(char variable, int old_value, int new_value)
{
	if (this->variables[variable] == old_value)
		updateVariable(variable, new_value);
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

bool DSM::contains(char variable)
{
	return std::find(this->vars.begin(), this->vars.end(), variable) != this->vars.end();
}

int DSM::getValue(char variable)
{
	return this->variables[variable];
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
	return int(variable);
}

char DSM::getVariableChar(int index)
{
	return char(index);
}

