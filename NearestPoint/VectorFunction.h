#pragma once
#include <vector>

class VectorFunction {
public:
	virtual double operator()(const std::vector<double> &v) = 0;
};