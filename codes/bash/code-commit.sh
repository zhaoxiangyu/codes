#!/bin/bash

echo "commiting codes to github"
pushd ..
git add -A .
git commit -m "by script"
git pull
git push  <<EOI
blueocci
123qwe123
EOI
popd
echo "codes committed"
