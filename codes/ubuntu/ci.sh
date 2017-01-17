#!/bin/bash

set -e

fullpath=$(readlink -f $0)
basedir=$(dirname $fullpath)
target_dir="$basedir/user-home"

#sync="sudo cp -alf"
sync="rsync -av --delete"
#sync="install -D"

$sync ~/.zshrc $target_dir/oh-my-zsh.zshrc
$sync ~/.vimrc $target_dir/vundle.vimrc
$sync ~/.emacs.d/ $target_dir/emacs.d

mkdir -p $target_dir/sbt/0.13/plugins
$sync ~/.sbt/0.13/plugins/npt.sbt $target_dir/sbt/0.13/plugins/npt.sbt
$sync ~/.sbt/0.13/plugins/plugins.sbt $target_dir/sbt/0.13/plugins/plugins.sbt

$sync ~/.coloritrc $target_dir/coloritrc

mkdir -p $target_dir/desk
rsync -av --delete --exclude=".vim/plugged" --exclude=".vim/dirs" --delete-excluded ~/.desk $target_dir/desk
