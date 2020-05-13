#pragma once
#include <vector>
#include <Eigen/Dense>

class LPOracle {
public:
	virtual Eigen::VectorXd operator()(const Eigen::VectorXd &coefficients) = 0;
};