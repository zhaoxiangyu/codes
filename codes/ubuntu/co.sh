#!/bin/bash

set -e
#sync="sudo cp -alf"
sync="rsync -av --delete"

mkdir -p ~/bak

test -f ~/.zshrc && cp ~/.zshrc ~/bak/zshrc
test -f ~/.oh-my-zsh/oh-my-zsh.sh || sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
$sync user-home/oh-my-zsh.zshrc ~/.zshrc

test -f ~/.vimrc && cp ~/.vimrc ~/bak/vimrc
test -e ~/.vim/bundle/Vundle.vim || git clone https://github.com/VundleVim/Vundle.vim.git ~/.vim/bundle/Vundle.vim
$sync user-home/vundle.vimrc ~/.vimrc
