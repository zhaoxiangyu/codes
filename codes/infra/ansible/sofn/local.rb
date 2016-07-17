#!/usr/bin/env ruby

def vagrant_up
	puts `ruby -r ./vgrnt.rb -e 'up'`
end
