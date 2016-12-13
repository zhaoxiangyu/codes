#!/usr/bin/ruby -w

require 'soap/wsdlDriver'

client = SOAP::WSDLDriverFactory.new( 'http://example.com/service.wsdl' ).create_rpc_driver
result = client.doStuff();
