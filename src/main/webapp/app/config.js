ciIntegratorApp.config([
		'$stateProvider',
		'$urlRouterProvider',
		'$httpProvider',
		'$logProvider',
		function($stateProvider, $urlRouterProvider, $httpProvider,
				$logProvider) {

			$urlRouterProvider.otherwise('/createTemplate');
			// register interceptors
			$httpProvider.interceptors.push('ciIntegratorInterceptor');

			$stateProvider

			.state('createTemplate', {
				url : '/createTemplate',
				templateUrl : 'partials/createTemplate.html',
				controller : 'templateController'

			})

		} ]);
