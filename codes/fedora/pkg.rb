#!/usr/bin/env ruby
def history
	system "sudo dnf history list"
end

history
