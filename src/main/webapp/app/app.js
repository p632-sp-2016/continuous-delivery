var ciIntegratorApp = angular.module('ciIntegratorApp', [ 'ui.router' ])

ciIntegratorApp.factory('ciIntegratorInterceptor', ['$q', '$rootScope', function ($q,$rootScope) {
    return {
        // On request success
        request: function (config) {
          // console.log(config); // Contains the data about the request before it is sent.

          // Return the config or wrap it in a promise if blank.
          return config || $q.when(config);
        },

        // On request failure
        requestError: function (rejection) {
          // console.log(rejection); // Contains the data about the error on the request.
          
          // Return the promise rejection.
          return $q.reject(rejection);
        },

        // On response success
        response: function (response) {
          // console.log(response); // Contains the data from the response.
          
          // Return the response or promise.
          return response || $q.when(response);
        },

        // On response failture
        responseError: function (rejection) {
            console.log(rejection); // Contains the data about the error.
        	var status = rejection.status;
			if (status != 200) {
				$rootScope.displayingMsgContent = rejection.statusText;
				
			}
          // Return the promise rejection.
          return $q.reject(rejection);
        }
      };
    }]);

