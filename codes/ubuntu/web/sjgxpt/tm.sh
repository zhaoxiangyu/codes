#!/bin/bash 

tmux new-session -s sjgxpt -d
#tmux set-window-option -g automatic-rename off
#tmux split-window -v 'ipython'
#tmux split-window -h
tmux new-window -t sjgxpt -c /home/helong/he/lky/share/sjgxpt/script/dp
tmux new-window -t sjgxpt -c /home/helong/he/lky/share/sjgxpt/script/test
tmux attach-session -t sjgxpt
#tmux -2 attach-session -d
