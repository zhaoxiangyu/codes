#!/usr/bin/ruby -w
require 'net/http'

class MyHelper
  def initialize(server, port, username, password)
    @server = server
    @port = port
    @username = username
    @password = password

    puts "Initialised My Helper using #{@server}:#{@port} username=#{@username}"
  end



  def post_job(job_name)

    puts "Posting job #{job_name} to update order service"

    job_xml ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://test.com/Test/CreateUpdateOrders/1.0\">
    <soapenv:Header/>
    <soapenv:Body>
       <ns:CreateTestUpdateOrdersReq>
          <ContractGroup>ITE2</ContractGroup>
          <ProductID>topo</ProductID>
          <PublicationReference>#{job_name}</PublicationReference>
       </ns:CreateTestUpdateOrdersReq>
    </soapenv:Body>
 </soapenv:Envelope>"

    @http = Net::HTTP.new(@server, @port)
    puts "server: " + @server  + "port  : " + @port
    request = Net::HTTP::Post.new(('/XISOAPAdapter/MessageServlet?/Test/CreateUpdateOrders/1.0'), initheader = {'Content-Type' => 'text/xml'})
    request.basic_auth(@username, @password)
    request.body = job_xml
    response = @http.request(request)

    puts "request was made to server " + @server

    validate_response(response, "post_job_to_pega_updateorder job", '200')

  end



  private 

  def validate_response(response, operation, required_code)
    if response.code != required_code
      raise "#{operation} operation failed. Response was [#{response.inspect} #{response.to_hash.inspect} #{response.body}]"
    end
  end
end

/*
test = MyHelper.new("mysvr.test.test.com","8102","myusername","mypassword")
test.post_job("test_201601281419")
*/
