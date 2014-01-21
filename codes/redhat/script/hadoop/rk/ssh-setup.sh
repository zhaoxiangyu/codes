server=namenode
hostname=`hostname`

cd ~
if test ! -f ~/.ssh/id_rsa.pub; then
  ssh-keygen -t rsa
  #cat id_rsa.pub >>authorized_keys
fi
cd ~/.ssh
scp id_rsa.pub $server:~/.ssh/id_rsa.pub.$hostname
