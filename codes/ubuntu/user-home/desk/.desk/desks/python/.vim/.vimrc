" set nomodeline
set nocompatible

" set default 'runtimepath' (without ~/.vim folders)
"let &runtimepath = printf('%s/vimfiles,%s,%s/vimfiles/after', $VIM, $VIMRUNTIME, $VIM)

" what is the name of the directory containing this file?
let s:portable = expand('<sfile>:p:h')

" add the directory to 'runtimepath'
"let &runtimepath = printf('%s,%s,%s/after', s:portable, &runtimepath, s:portable)

"let python_config = s:portable . '/.vimrc.python'
execute 'source ' . s:portable . '/.vimrc.pyt'
