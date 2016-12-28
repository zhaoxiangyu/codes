#!/bin/bash

tmux new-session -s sjgxpt -d
#tmux set-window-option -g automatic-rename off
#tmux split-window -h
tmux new-window -t sjgxpt -c /home/helong/he/lky/share/sjgxpt/script/dp
tmux split-window -v -p 20 'top'
tmux select-pane -t :.1
tmux new-window -t sjgxpt -c /home/helong/he/lky/share/sjgxpt/script/test
tmux attach-session -t sjgxpt
tmux select-window -t :1
#tmux -2 attach-session -d
