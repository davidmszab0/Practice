(function() {
  'use strict';

  angular
    .module('client')
    .directive('refView', angular_directive);

	function angular_directive() {
		var directive = {
			restrict : 'EM', // M = only the comment directive will be invoked
			replace : true,
			template : "<h1>Made by a directive!</h1>"
		};
		return directive;
	}
})();