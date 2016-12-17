#!/bin/bash 

tmux new-session -s sjgxpt -n editor -d
#tmux set-window-option -g automatic-rename off
#tmux split-window -v 'ipython'
#tmux split-window -h
#tmux new-window -t sjgxpt -c /home/helong/he/lky/share/sjgxpt/script/dp
#tmux new-window -t sjgxpt -c /home/helong/he/lky/share/sjgxpt/script/test
tmux new-window -t sjgxpt -n deploy
tmux new-window -t sjgxpt -n test
tmux split-window -t sjgxpt -v -p 10
tmux select-pane -t:.1
tmux select-window -t sjgxpt:1
tmux attach-session -t sjgxpt
#tmux -2 attach-session -d
