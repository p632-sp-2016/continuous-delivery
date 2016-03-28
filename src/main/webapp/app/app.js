var ciIntegratorApp = angular.module('ciIntegratorApp', [ 'ui.router' ])

ciIntegratorApp.service('globalData', function() {
	return {
		dataLoaded : false,
		message : null
	};
});

ciIntegratorApp.controller('progressIndicator', [ '$scope', '$state',
		'globalData', function($scope, $state, globalData) {
			$scope.isDataLoaded = globalData;

		} ]);

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
					if (status != 200) {
						$rootScope.displayingMsgType = "failure";
						$rootScope.displayingMsgContent = rejection.statusText;
					}

					return rejection;
				}
			};
			return sessionInjector;

		} ]);
