groupadd hadoop
useradd -g hadoop hadoop
passwd hadoop <<EOI
123456
123456
EOI
sed -i "\$ahadoop\t$HOSTNAME=(ALL)\tNOPASSWD: ALL" /etc/sudoers
