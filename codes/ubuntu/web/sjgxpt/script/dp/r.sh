#!/bin/bash
set -e
tmux kill-session -t server_logs
./lsvr_kill.sh
./lb.sh
./ldp.sh
./lsvr_start.sh
./lsvr_vl.sh
