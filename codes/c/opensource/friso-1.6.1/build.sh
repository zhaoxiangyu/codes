#!/bin/bash

pushd src
make
chmod 755 friso
./friso -init ../friso.ini
popd
