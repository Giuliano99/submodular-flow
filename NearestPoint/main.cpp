#include "NearestPoint.h"
#include "VertexOracle.h"
#include <iostream>
#include <Eigen/Dense>
#include <vector>
#include "BundleDescent.h"
#include "SubmodularOracle.h"

using namespace std;
using namespace Eigen;

int main() {
	//Construct triangle
	VectorXd b11(3);
	VectorXd b12(3);
	VectorXd b13(3);
	VectorXd b14(3);
	VectorXd b15(3);
	VectorXd b16(3);
	VectorXd b17(3);
	b11 << 1, 3, 0;
	b12 << 1, 2, 1;
	b13 << 1, 0, 1;
	b14 << 1, 2, 0;
	b15 << 1, 1, 1;
	b16 << 1, 3, 0;
	b17 << 1, 2, 1;

	VectorXd b21(3);
	VectorXd b22(3);
	VectorXd b23(3);
	VectorXd b24(3);
	VectorXd b25(3);
	VectorXd b26(3);
	VectorXd b27(3);
	b21 << 1, 4, 0;
	b22 << 1, 3, 1;
	b23 << 1, 2, 2;
	b24 << 1, 1, 3;
	b25 << 0, 4, 1;
	b26 << 0, 3, 2;
	b27 << 0, 2, 3;

	VectorXd b31(3);
	VectorXd b32(3);
	VectorXd b33(3);
	b31 << 2, 0, 0;
	b32 << 2, 0, 1;
	b33 << 2, 0, 0;
	
	VectorXd supply(3);
	supply << 2, 4, 3;

	
	bundles_t bundles = { {b11,b12}, {b21,b22,b23,b24,b25,b26,b27}, {b31,b32} };
	BundleDescent b_descent(bundles, supply);
	SubmodularOracle<BundleDescent> oracle(b_descent);

	Eigen::VectorXd help(Eigen::VectorXd::Zero(3));
	help << 3, 2, 1;
	VectorXd start_vertex = oracle(help);
	cout << "Start vertex:\n" << start_vertex << endl;

	auto nearest_point = get_nearest_point(start_vertex, &oracle);
	cout << "Result:\n" << nearest_point;
	cout << "\nSteepest descent direction: ";
	for (int i = 0; i < nearest_point.size(); ++i) cout << ((nearest_point(i) < -1e-7) ? 1 : 0) << " ";
	cin.ignore();
}