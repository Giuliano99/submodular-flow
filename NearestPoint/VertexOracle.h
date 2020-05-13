#pragma once
#include <Eigen/Dense>
#include <vector>
#include <limits>
#include "LPOracle.h"

class VertexOracle : public LPOracle
{
public:
	VertexOracle(const std::vector<Eigen::VectorXd> &verts): vertices(verts) {}
	Eigen::VectorXd operator()(const Eigen::VectorXd &coefficients) const {
		Eigen::VectorXd min_vec;
		double minimum = std::numeric_limits<double>::infinity();
		for (auto it = vertices.begin(); it != vertices.end(); ++it) {
			double val = (*it).dot(coefficients);
			if (val < minimum) {
				minimum = val;
				min_vec = *it;
			}
		}
		return min_vec;
	}

private:
	std::vector<Eigen::VectorXd> vertices;
};

