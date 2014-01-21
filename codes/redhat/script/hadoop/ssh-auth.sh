test $# -eq 0 && { echo "usage: ssh-deploy.sh hostname(or ip)"; exit 1; }
host=$1

cat ~/.ssh/id_rsa.pub.$host >>~/.ssh/authorized_keys
scp ~/.ssh/authorized_keys $host:~/.ssh/authorized_keys
