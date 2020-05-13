#pragma once
#include <vector>
#include <Eigen/Dense>
#include <algorithm>

typedef std::vector<std::vector<Eigen::VectorXd>> bundles_t;

class BundleDescent {
public:
	BundleDescent(const bundles_t &bdls, const Eigen::VectorXd &sply): bundles(bdls), supply(sply) {}
	double operator()(const Eigen::VectorXd &price_inc) {
		double val = price_inc.dot(supply);
		for (auto oit = bundles.begin(); oit != bundles.end(); ++oit) {
			double val_for_bidder = 1000;
			for (auto bit = oit->begin(); bit != oit->end(); ++bit) {
				val_for_bidder = std::min(val_for_bidder, bit->dot(price_inc));
			}
			val -= val_for_bidder;
		}
		return val;
	}
private:
	const bundles_t &bundles;
	const Eigen::VectorXd &supply;
};