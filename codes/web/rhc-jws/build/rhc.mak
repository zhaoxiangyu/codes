#rhc related commands
OPENSHIFT_USERNAME:=blueocci@hotmail.com
OPENSHIFT_PW:=123qwe
RHC_OPTION_L:=-l$(OPENSHIFT_USERNAME) -p$(OPENSHIFT_PW)
RHC:=rhc

install:
	sudo apt-get install ruby
	sudo apt-get install rubygems 
	sudo gem install rhc
	
setup:
	$(RHC) domain create -n blueocci $(RHC_OPTION_L)
	
create-phpapp:
	$(RHC) app create -a wd4web -t php-5.3 $(RHC_OPTION_L)
	
setup-info:

create-jws:
	cd .. \
	;$(RHC) app create -a jws -t jbossas-7.0 $(RHC_OPTION_L)
	
push-jws:
	cd ../jws \
	;git add . \
	;git commit -m "committing @ `date`" \
	;git push

destroy-jws:
	cd ..\
	;$(RHC) app destroy -a jws
	