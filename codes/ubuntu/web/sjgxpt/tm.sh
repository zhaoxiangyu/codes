#!/bin/bash 

tmux new-session -s 'sjgxpt' -n 'deploy' -d
#tmux split-window -v 'ipython'
#tmux split-window -h
tmux new-window 'test'
tmux attach-session -t sjgxpt
#tmux -2 attach-session -d
