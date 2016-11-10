syntax on
set fileencodings=utf-8,ucs-bom,gbk,cp936
set tabstop=4
set softtabstop=4
set shiftwidth=4
"set expandtab
set ruler
set showcmd
set modeline

set number

"python setting
"filetype plugin indent on
autocmd FileType python setlocal et sta sw=4 sts=4
autocmd FileType python setlocal foldmethod=indent
set foldlevel=99
"map <F3> za

"install vundle.vim
"git clone https://github.com/gmarik/Vundle.vim.git ~/.vim/bundle/Vundle.vim
set nocompatible	" be iMproved, required
filetype off		" required
"set the runtime path to include Vundle and initiallize
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()
Plugin 'gmarik/Vundle.vim'
Plugin 'scrooloose/nerdcommenter'
Plugin 'scrooloose/syntastic'
Plugin 'nvie/vim-flake8'
Plugin 'cscope_macros.vim'
Plugin 'vim-ruby/vim-ruby'
call vundle#end()
filetype plugin indent on

"vim ruby
set nocompatible      " We're running Vim, not Vi!
syntax on             " Enable syntax highlighting
filetype on           " Enable filetype detection
filetype indent on    " Enable filetype-specific indenting
filetype plugin on    " Enable filetype-specific plugins
