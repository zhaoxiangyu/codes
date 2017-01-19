#!/bin/bash

set -e

fullpath=$(readlink -f $0)
basedir=$(dirname $fullpath)
target_dir="$basedir/user-home"

#sync="sudo cp -alf"
sync="rsync -av --delete"
#sync="install -D"

zsh(){
	$sync ~/.zshrc $target_dir/oh-my-zsh.zshrc
}

vim(){
	$sync ~/.vimrc $target_dir/vundle.vimrc
}

emacs(){
	$sync ~/.emacs.d/ $target_dir/emacs.d
}

sbt(){
	mkdir -p $target_dir/sbt/0.13/plugins
	$sync ~/.sbt/0.13/plugins/npt.sbt $target_dir/sbt/0.13/plugins/npt.sbt
	$sync ~/.sbt/0.13/plugins/plugins.sbt $target_dir/sbt/0.13/plugins/plugins.sbt
	$sync ~/.sbt/repositories $target_dir/sbt/
}

colorit(){
	$sync ~/.coloritrc $target_dir/coloritrc
}

desk(){
	mkdir -p $target_dir/desk
	rsync -av --delete --exclude=".vim/plugged" --exclude=".vim/dirs" --delete-excluded ~/.desk/ $target_dir/desk
}

$*
