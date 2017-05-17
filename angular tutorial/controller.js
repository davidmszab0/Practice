(function() {
  'use strict';

angular
	.module("client", [])
	.controller('ChildModalController', ChildModalController1); 

	function ChildModalController1(refService) {
		var vm = this;
		var printFunc = {};

		console.log("haho!");

		vm.name = "Jozsi";
		console.log(vm.name);

		refService.printServiceFunc();
		
		vm.printFunc = function() {
			console.log("controller is pringint");
		}
	}
})();

		 

	

	