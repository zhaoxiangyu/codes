# python.sh
#
# Description: desk for doing python programming
#

cd ~/data/github/codes/codes/python

DESK_NAME="python"
#DESK_ROOT="~/.desk/desks/$DESK_NAME"
DESK_ROOT="/home/he/.desk/desks/$DESK_NAME"

# Setup packages for ubuntu
setup_ubuntu() {
	sudo apt-get install curl vim exuberant-ctags git ack-grep
	sudo pip install pep8 flake8 pyflakes isort yapf
}

# Setup vim enviroments
setup_vim() {
	wget https://raw.githubusercontent.com/fisadev/fisa-vim-config/master/.vimrc -O $DESK_ROOT/.vimrc.$DESK_NAME
}

# alias for vim
# alias vim="vim -u $DESK_ROOT/.vim/.vimrc.python"
alias vim="vim -u $DESK_ROOT/.vim/.vimrc"
# export MYVIMRC="$DESK_ROOT/.vim/.vimrc"  #or any other location you want
