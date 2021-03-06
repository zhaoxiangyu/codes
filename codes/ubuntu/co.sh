#!/bin/bash

set -e

fullpath=$(readlink -f $0)
basedir=$(dirname $fullpath)
target_dir="$basedir/user-home"

#sync="sudo cp -alf"
sync="rsync -av --delete"

mkdir -p ~/bak

test -f ~/.zshrc && cp ~/.zshrc ~/bak/zshrc.$(date +%F)
test -f ~/.oh-my-zsh/oh-my-zsh.sh || sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
$sync $basedir/user-home/oh-my-zsh.zshrc ~/.zshrc

test -f ~/.vimrc && cp ~/.vimrc ~/bak/vimrc.$(date +%F)
test -e ~/.vim/bundle/Vundle.vim || git clone https://github.com/VundleVim/Vundle.vim.git ~/.vim/bundle/Vundle.vim
$sync $basedir/user-home/vundle.vimrc ~/.vimrc

test -d ~/.emacs.d && cp -a ~/.emacs.d ~/bak/emacs.d.$(date +%F)

test -d ~/.sbt && mv -f ~/.sbt ~/bak/sbt.$(date +%F)
mkdir -p ~/.sbt
$sync $basedir/user-home/sbt/ ~/.sbt

test -d ~/.desk && mv -f ~/.desk ~/bak/desk.$(date +%F)
mkdir -p ~/.desk
rsync -av --delete $target_dir/desk/ ~/.desk
