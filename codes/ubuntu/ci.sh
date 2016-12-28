#!/bin/bash

set -e

fullpath=$(readlink -f $0)
basedir=$(dirname $fullpath)

#sync="sudo cp -alf"
sync="rsync -av --delete"

$sync ~/.zshrc $basedir/user-home/oh-my-zsh.zshrc
$sync ~/.vimrc $basedir/user-home/vundle.vimrc
$sync ~/.emacs.d $basedir/user-home/emacs.d
$sync ~/.sbt/0.13/plugins/npt.sbt $basedir/user-home/sbt/0.13/plugins
$sync ~/.sbt/0.13/plugins/plugins.sbt $basedir/user-home/sbt/0.13/plugins
