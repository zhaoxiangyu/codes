#!/bin/bash

set -e
#sync="sudo cp -alf"
sync="rsync -av --delete"

$sync ~/.zshrc user-home/oh-my-zsh.zshrc
$sync ~/.vimrc user-home/vundle.vimrc
$sync ~/.emacs.d user-home/emacs.d
$sync ~/.sbt/0.13/plugins/npt.sbt user-home/sbt/0.13/plugins
$sync ~/.sbt/0.13/plugins/plugins.sbt user-home/sbt/0.13/plugins
