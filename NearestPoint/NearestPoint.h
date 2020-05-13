#pragma once

#include <vector>
#include <list>
#include <Eigen/Dense>
#include <Eigen/Core>
#include <limits>
#include <algorithm>
#include "LPOracle.h"
#include <iostream>


class NearestPoint {
public:
	NearestPoint() {}
	~NearestPoint() {}

	Eigen::VectorXd affine_minimizer(const Eigen::MatrixXd &A, int col_ptr) {
		Eigen::VectorXd b(Eigen::VectorXd::Zero(A.rows()));
		b(0) = 1;
		//std::cout << "A block:\n" << A.block(0, 0, A.rows(), col_ptr) << std::endl;
		//std::cout << "b:\n" << b << std::endl;
		Eigen::VectorXd u = A.block(0, 0, A.rows(), col_ptr).householderQr().solve(b);
		//std::cout << "u:\n" << u << std::endl;
		return (1 / u.sum())*u;
	}

	void delete_columns(Eigen::VectorXd &lambda, Eigen::Block<Eigen::MatrixXd> &B, int &col_ptr) {
		std::vector<int> cols_to_delete;
		std::vector<int> not_deleted_cols;
		for (int i = 0; i < col_ptr; ++i) {
			if (lambda(i) <= 1e-10) cols_to_delete.push_back(i);
			else not_deleted_cols.push_back(i);
		}
		auto del_it = cols_to_delete.begin();
		auto not_del_it = not_deleted_cols.rbegin();
		for (; del_it != cols_to_delete.end() && not_del_it != not_deleted_cols.rend(); ++del_it, ++not_del_it) {
			if (*del_it >= *not_del_it) break;
			B.col(*del_it) = B.col(*not_del_it);
			lambda(*del_it) = lambda(*not_del_it);
		}
		col_ptr -= cols_to_delete.size();
	}

	Eigen::VectorXd get_nearest_point(Eigen::VectorXd start_vertex, LPOracle *lp_oracle, bool verbose = false) {
		double epsilon = 1e-5;
		double eps_squared = epsilon * epsilon;

		auto x = start_vertex;
		int dim = start_vertex.size();
		Eigen::VectorXd lambda(Eigen::VectorXd::Zero(dim + 1)); //There are at most dim+1 affinely independent vectors
		Eigen::MatrixXd A(Eigen::MatrixXd::Zero(dim + 1, dim + 1)); // -...- We add an additional row of 1s at the top for solving the least squares problem
		A.row(0) = Eigen::VectorXd::Ones(dim + 1);
		auto B = A.block(1, 0, dim, dim + 1); //The matrix B containing the vectors is just A without the top row
		int col_ptr = 0;
		B.col(col_ptr) = x; lambda(col_ptr) = 1; ++col_ptr;
		if (verbose) {
			std::cout << "A:\n" << A << std::endl;
			std::cout << "B:\n" << B << std::endl;
		}
		while (true) { //Major cycle
			if (verbose) std::cout << "x:\n" << x << std::endl;
			auto q = (*lp_oracle)(x);
			if (verbose) std::cout << "q:\n" << q << std::endl;
			if (verbose) std::cout << "x²-xq= " << x.squaredNorm() - x.dot(q) << std::endl;
			if (x.squaredNorm() <= x.dot(q) + epsilon) break;
			B.col(col_ptr) = q; lambda(col_ptr) = 0; ++col_ptr;
			Eigen::VectorXd alpha;
			while (true) {
				alpha = affine_minimizer(A, col_ptr);
				if (verbose) std::cout << "alpha:\n" << alpha << std::endl;
				if (alpha.minCoeff() >= 1e-10) break;
				double theta = std::numeric_limits<double>::infinity();
				if (verbose) std::cout << "lambda:\n" << lambda << std::endl;
				for (int i = 0; i < col_ptr; ++i) { if (lambda(i) - alpha(i) >= 1e-10) theta = std::min(lambda(i) / (lambda(i) - alpha(i)), theta); }
				theta = std::min(1.0, theta);
				lambda.topRows(col_ptr) = theta * alpha + (1 - theta)*lambda.topRows(col_ptr);
				if (verbose) std::cout << "B before deletion:\n" << B << std::endl;
				if (verbose) std::cout << "lambda before deletion:\n" << lambda << std::endl;
				delete_columns(lambda, B, col_ptr);
				if (verbose) std::cout << "lambda after deletion:\n" << lambda << std::endl;
				if (verbose) std::cout << "B after deletion:\n" << B << std::endl;
			}
			lambda.topRows(col_ptr) = alpha;
			x = B.leftCols(col_ptr)*lambda.topRows(col_ptr);
		}
		return x;
	}
};
