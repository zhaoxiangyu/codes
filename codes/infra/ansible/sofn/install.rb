#!/usr/bin/env ruby

def ansi_install
	puts `ansible-playbook -i env/local -t install site.yml`
end
