#pragma once

#include <stdexcept>

class InvalidInputException : std::exception
{
	char* what_message;

public:
	InvalidInputException();
	explicit InvalidInputException(const char* message) : std::exception(message) {};
};
