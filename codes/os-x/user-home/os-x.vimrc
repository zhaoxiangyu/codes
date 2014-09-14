set fileencodings=utf-8,ucs-bom,gbk,cp936
set ruler
set showcmd
set modeline
syntax on

set number
nnoremap <F2> :set nonumber!<CR>:set foldcolumn=0<CR>

"python setting
filetype indent plugin on
autocmd FileType python setlocal et sta sw=4 sts=4
autocmd FileType python setlocal foldmethod=indent
set foldlevel=99
"map <F3> za
