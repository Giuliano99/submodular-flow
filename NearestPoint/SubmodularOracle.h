#pragma once
#include "LPOracle.h"
#include <vector>
#include <tuple>
#include <algorithm>

template<typename VectorOperation>
class SubmodularOracle : public LPOracle {
public:
	SubmodularOracle(VectorOperation &fn) : fun(fn), has_mask(false) {}

	Eigen::VectorXd operator()(const Eigen::VectorXd &coefficients)  {
		std::vector<std::pair<double,int>> coeffs_inds(coefficients.size());
		for (size_t i = 0; i < coeffs_inds.size(); ++i) {
			coeffs_inds[i].first = coefficients[i];
			coeffs_inds[i].second = i;
		}
		std::sort(coeffs_inds.begin(), coeffs_inds.end());
		Eigen::VectorXd argument(Eigen::VectorXd::Zero(coefficients.size()));
		Eigen::VectorXd q(Eigen::VectorXd::Zero(coefficients.size()));
		for (auto it = coeffs_inds.begin(); it != coeffs_inds.end(); ++it) {
			if (has_mask) {
				q[it->second] = -fun(argument);
				argument[it->second] = 1 * mask(it->second);
				q[it->second] += fun(argument);
			}
			else {
				q[it->second] = -fun(argument);
				argument[it->second] = 1;
				q[it->second] += fun(argument);
			}
		}
		return q;
	}

	void set_mask(Eigen::VectorXd m) {
		mask = m;
		has_mask = true;
	}

	void remove_mask() {
		has_mask = false;
	}
private:
	VectorOperation &fun;
	Eigen::VectorXd mask;
	bool has_mask;
};