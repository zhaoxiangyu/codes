# vim.sh
#
# Description: desk for customize vim enviroments
#

# Setup vim enviroments
setup_vim() {

	local LANG=$1
	INSTALL_PATH="$(cd $(dirname $0)/../ ; pwd -P )"

	cat > "$HOME/.vimrc.$LANG" <<-EOF
	EOF

	echo "Hint: vim -u ~/.vimrc.$LANG"
}

# alias for vim
# alias vim='vim -u ~/.vimrc.go'
