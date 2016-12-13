var soap = require('soap');
var url = 'http://localhost:11080/sofn-dgap-pre/ws/welcome1?wsdl';
//var args = {name: 'value'};
var args = {};
soap.createClient(url, function(err, client) {
	client.getMessage(args, function(err, result) {
			  console.log(result);
	});
});
