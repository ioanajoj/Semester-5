#include <iostream>

using namespace std;

class Task
{
public:
	int id;

	Task(int i) 
	{
		this->id = i;
	};

	void execute()
	{
		cout << "I am task " << this->id << endl;
	}
};