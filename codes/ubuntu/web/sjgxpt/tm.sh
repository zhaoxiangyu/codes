#!/bin/sh 
tmux new-session -d 'sjgxpt'
tmux split-window -v 'ipython'
tmux split-window -h
tmux new-window 'mutt'
tmux -2 attach-session -d
