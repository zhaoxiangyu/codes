#!/bin/bash 

#tmux set-window-option -g automatic-rename off
tmux new-session -s sjgxpt -n deploy -d
#tmux split-window -v 'ipython'
#tmux split-window -h
tmux new-window -n test
#tmux attach-session -t sjgxpt
#tmux -2 attach-session -d
