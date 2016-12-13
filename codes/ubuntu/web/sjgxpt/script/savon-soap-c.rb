#!/usr/bin/ruby -w
require 'savon'

# create a client for the service
client = Savon.client(wsdl: 'http://localhost:11080/sofn-dgap-pre/ws/welcome1?wsdl')

client.operations
# => [:find_user, :list_users]

# call the 'findUser' operation
#response = client.call(:find_user, message: { id: 42 })
response = client.call(:get_message, message: {})

response.body
# => { find_user_response: { id: 42, name: 'Hoff' } }
