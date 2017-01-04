echo running install ...
git config --global push.default simple
git config --global user.name "$USERNAME_$(lsb_release -si|tr A-Z a-z)"
git config --global user.email "blueocci@hotmail.com"
dirname="github-`date --rfc-3339=date`"
mkdir $dirname
git clone https://github.com/blueocci/codes.git $dirname
