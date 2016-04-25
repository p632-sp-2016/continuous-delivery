var ciIntegratorApp = angular.module('ciIntegratorApp', [ 'ui.router' ])



ciIntegratorApp.factory('ciIntegratorInterceptor', [ '$rootScope', '$location',
		function($rootScope, $location) {

			var sessionInjector = {
				request : function(config) {
					return config;
				},
				response : function(response) {
					return response;
				},
				responseError : function(rejection) {
					var status = rejection.status;
					if (status !== 200) {
						$rootScope.displayingMsgContent = "Server Error :" + rejection.statusText;
					}

					return rejection;
				}
			};
			return sessionInjector;

		} ]);
