(function() {
  'use strict';

angular
	.module("client")
	.factory("refService", Service);

	function Service() {
		var service = {
			printServiceFunc: _printServiceFunc
		};
		return service;

		function _printServiceFunc() {
			var x = "service is serving";
				console.log("console: service is serving");
			return x;
		}
	}
})();