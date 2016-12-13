#!/bin/bash

set -e
#sync="sudo cp -alf"
sync="rsync -av --delete"

$sync build vc/github/ubuntu/web/sjgxpt/
$sync udev/conf vc/github/ubuntu/web/sjgxpt/
$sync infra/sql vc/github/ubuntu/web/sjgxpt/
$sync script vc/github/ubuntu/web/sjgxpt/
$sync tm.sh vc/github/ubuntu/web/sjgxpt/
$sync ~/.zshrc vc/github/ubuntu/user-home/my.zshrc
$sync ~/.vimrc vc/github/ubuntu/user-home/x.vimrc
$sync ~/.vimrc.bak vc/github/ubuntu/user-home/vundle.vimrc
