test $# -eq 0 && { echo "usage: pre-deploy.sh hostname(or ip)"; exit 1; }
host=$1
scp ssh-setup.sh $host:~
scp user-setup.sh $host:~
scp /etc/hosts $host:/etc/hosts

ssh $host "grep 'rdate -s timeserver' /etc/crontab || sed -i -e '\$a* * * * * rdate -s timeserver' /etc/crontab"
#ssh $host service crond status
ssh $host service crond restart
