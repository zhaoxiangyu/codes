#!/bin/bash

setup-env() {
	export PATH=/opt/vagrant/embedded/bin:$PATH
	export GEM_HOME=~/.vagrant.d/gems
	export GEM_PATH=$GEM_HOME:/opt/vagrant/embedded/gems
}

uninstall-libvirt(){
	gem uninstall ruby-libvirt
	sudo mv /opt/vagrant/embedded/lib/libcurl.so{,.backup}
	sudo mv /opt/vagrant/embedded/lib/libcurl.so.4{,.backup}
	sudo mv /opt/vagrant/embedded/lib/libcurl.so.4.4.0{,.backup}
	sudo mv /opt/vagrant/embedded/lib/pkgconfig/libcurl.pc{,.backup}
}

install-libvirt(){
	#gem install ruby-libvirt
	gem install ruby-libvirt-0.6.0.gem
	sudo mv /opt/vagrant/embedded/lib/libcurl.so{.backup,}
	sudo mv /opt/vagrant/embedded/lib/libcurl.so.4{.backup,}
	sudo mv /opt/vagrant/embedded/lib/libcurl.so.4.4.0{.backup,}
	sudo mv /opt/vagrant/embedded/lib/pkgconfig/libcurl.pc{.backup,}
}
