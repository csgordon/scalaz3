#!/bin/bash

pushd external/

# Fetch version 4.4.1 of Z3, to build the Java bindings
git clone --branch z3-4.4.1 https://github.com/Z3Prover/z3

pushd z3/
python scripts/mk_make.py --prefix=`pwd`/../z3bin --java
pushd build/
make
make install
